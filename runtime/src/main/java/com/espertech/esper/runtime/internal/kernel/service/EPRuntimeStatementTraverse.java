/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.runtime.internal.kernel.service;

import com.espertech.esper.common.client.EPException;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.EventType;
import com.espertech.esper.common.client.annotation.*;
import com.espertech.esper.common.client.meta.EventTypeApplicationType;
import com.espertech.esper.common.client.meta.EventTypeIdPair;
import com.espertech.esper.common.client.meta.EventTypeMetadata;
import com.espertech.esper.common.client.meta.EventTypeTypeClass;
import com.espertech.esper.common.client.util.*;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluator;
import com.espertech.esper.common.internal.epl.expression.core.ExprNode;
import com.espertech.esper.common.internal.epl.expression.core.ExprNodeUtilityPrint;
import com.espertech.esper.common.internal.event.bean.core.BeanEventBean;
import com.espertech.esper.common.internal.event.bean.core.BeanEventType;
import com.espertech.esper.common.internal.event.bean.core.BeanEventTypeStemService;
import com.espertech.esper.common.internal.event.bean.service.BeanEventTypeFactoryPrivate;
import com.espertech.esper.common.internal.event.core.EventBeanTypedEventFactoryRuntime;
import com.espertech.esper.common.internal.event.eventtypefactory.EventTypeFactoryImpl;
import com.espertech.esper.common.internal.util.JavaClassHelper;
import com.espertech.esper.common.internal.util.UuidGenerator;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EPRuntimeStatementTraverse {
    private static final Logger log = LoggerFactory.getLogger(EPRuntimeStatementTraverse.class);

    // Predefined properties available:
    // - name (string)
    // - description (string)
    // - epl (string)
    // - each tag individually (string)
    // - priority
    // - drop (boolean)
    // - hint (string)
    private final BeanEventType statementRowType;

    public EPRuntimeStatementTraverse() {
        BeanEventTypeStemService stemSvc = new BeanEventTypeStemService(Collections.emptyMap(), null, PropertyResolutionStyle.CASE_SENSITIVE, AccessorStyle.JAVABEAN);
        BeanEventTypeFactoryPrivate factoryPrivate = new BeanEventTypeFactoryPrivate(new EventBeanTypedEventFactoryRuntime(null), EventTypeFactoryImpl.INSTANCE, stemSvc);
        EventTypeMetadata metadata = new EventTypeMetadata(UuidGenerator.generate(), null, EventTypeTypeClass.STREAM, EventTypeApplicationType.CLASS, NameAccessModifier.TRANSIENT, EventTypeBusModifier.NONBUS, false, EventTypeIdPair.unassigned());
        statementRowType = new BeanEventType(stemSvc.getCreateStem(StatementRow.class, null), metadata, factoryPrivate, null, null, null, null);
    }

    public void traverseStatements(EPRuntimeImpl runtime, BiConsumer<EPDeployment, EPStatement> consumer, String filterExpression) throws EPException {
        ExprNode filterExpr;
        try {
            filterExpr = runtime.getReflectiveCompileSvc().compileExpression(filterExpression, new EventType[]{statementRowType}, new String[]{statementRowType.getName()});
        } catch (Throwable t) {
            throw new EPException("Failed to compiler filter: " + t.getMessage(), t);
        }

        for (String deploymentId : runtime.getDeploymentService().getDeployments()) {
            EPDeployment deployment = runtime.getDeploymentService().getDeployment(deploymentId);
            if (deployment == null) {
                continue;
            }
            for (EPStatement stmt : deployment.getStatements()) {
                if (!evaluateStatement(filterExpr, stmt)) {
                    continue;
                }
                consumer.accept(deployment, stmt);
            }
        }
    }

    private static StatementRow getRow(EPStatement statement) {
        String description = null;
        String hint = null;
        String hintDelimiter = "";
        int priority = 0;
        Map<String, String> tags = null;
        boolean drop = false;

        Annotation[] annotations = statement.getAnnotations();
        for (Annotation anno : annotations) {
            if (anno instanceof Hint) {
                if (hint == null) {
                    hint = "";
                }
                hint += hintDelimiter + ((Hint) anno).value();
                hintDelimiter = ",";
            } else if (anno instanceof Tag) {
                Tag tag = (Tag) anno;
                if (tags == null) {
                    tags = new HashMap<String, String>();
                }
                tags.put(tag.name(), tag.value());
            } else if (anno instanceof Priority) {
                Priority tag = (Priority) anno;
                priority = tag.value();
            } else if (anno instanceof Drop) {
                drop = true;
            } else if (anno instanceof Description) {
                description = ((Description) anno).value();
            }
        }

        String name = statement.getName();
        String text = (String) statement.getProperty(StatementProperty.EPL);

        return new StatementRow(
            name,
            text,
            statement.getUserObjectCompileTime(),
            statement.getUserObjectRuntime(),
            description,
            hint,
            priority,
            drop,
            tags
        );
    }

    private boolean evaluateStatement(ExprNode expression, EPStatement stmt) {
        if (expression == null) {
            return true;
        }

        Class returnType = expression.getForge().getEvaluationType();
        if (JavaClassHelper.getBoxedType(returnType) != Boolean.class) {
            throw new EPException("Invalid expression, expected a boolean return type for expression and received '" +
                JavaClassHelper.getClassNameFullyQualPretty(returnType) +
                "' for expression '" + ExprNodeUtilityPrint.toExpressionStringMinPrecedenceSafe(expression) + "'");
        }
        ExprEvaluator evaluator = expression.getForge().getExprEvaluator();

        try {
            StatementRow row = getRow(stmt);
            EventBean rowBean = new BeanEventBean(row, statementRowType);

            Boolean pass = (Boolean) evaluator.evaluate(new EventBean[]{rowBean}, true, null);
            return !((pass == null) || (!pass));
        } catch (Exception ex) {
            log.error("Unexpected exception filtering statements by expression, skipping statement: " + ex.getMessage(), ex);
        }
        return false;
    }

    public static class StatementRow {
        private String name;
        private String epl;
        private Object userObjectCompileTime;
        private Object userObjectRuntimeTime;
        private String description;
        private String hint;
        private int priority;
        private Boolean drop;
        private Map<String, String> tag;

        public StatementRow(String name, String epl, Object userObjectCompileTime, Object userObjectRuntimeTime, String description, String hint, int priority, Boolean drop, Map<String, String> tag) {
            this.name = name;
            this.epl = epl;
            this.userObjectCompileTime = userObjectCompileTime;
            this.userObjectRuntimeTime = userObjectRuntimeTime;
            this.description = description;
            this.hint = hint;
            this.priority = priority;
            this.drop = drop;
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEpl() {
            return epl;
        }

        public void setEpl(String epl) {
            this.epl = epl;
        }

        public Object getUserObjectCompileTime() {
            return userObjectCompileTime;
        }

        public void setUserObjectCompileTime(Object userObjectCompileTime) {
            this.userObjectCompileTime = userObjectCompileTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Boolean isDrop() {
            return drop;
        }

        public Boolean getDrop() {
            return drop;
        }

        public void setDrop(Boolean drop) {
            this.drop = drop;
        }

        public Map<String, String> getTag() {
            return tag;
        }

        public void setTag(Map<String, String> tag) {
            this.tag = tag;
        }
    }
}
