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
package com.espertech.esper.common.internal.epl.expression.ops;

import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.client.type.EPTypePremade;
import com.espertech.esper.common.client.util.ThreadingProfile;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenClassScope;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenMethodScope;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpression;
import com.espertech.esper.common.internal.epl.expression.codegen.ExprForgeCodegenSymbol;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluator;
import com.espertech.esper.common.internal.epl.expression.core.ExprForgeConstantType;
import com.espertech.esper.common.internal.epl.expression.core.ExprForgeInstrumentable;
import com.espertech.esper.common.internal.epl.expression.core.ExprNodeUtilityQuery;
import com.espertech.esper.common.internal.metrics.instrumentation.InstrumentationBuilderExpr;

public class ExprConcatNodeForge implements ExprForgeInstrumentable {
    private final ExprConcatNode parent;
    private final ThreadingProfile threadingProfile;

    public ExprConcatNodeForge(ExprConcatNode parent, ThreadingProfile threadingProfile) {
        this.parent = parent;
        this.threadingProfile = threadingProfile;
    }

    public ExprConcatNode getForgeRenderable() {
        return parent;
    }

    public ExprEvaluator getExprEvaluator() {
        ExprEvaluator[] evaluators = ExprNodeUtilityQuery.getEvaluatorsNoCompile(parent.getChildNodes());
        if (threadingProfile == ThreadingProfile.LARGE) {
            return new ExprConcatNodeForgeEvalWNew(this, evaluators);
        } else {
            return new ExprConcatNodeForgeEvalThreadLocal(this, evaluators);
        }
    }

    public ExprForgeConstantType getForgeConstantType() {
        return ExprForgeConstantType.NONCONST;
    }

    public CodegenExpression evaluateCodegen(EPTypeClass requiredType, CodegenMethodScope codegenMethodScope, ExprForgeCodegenSymbol exprSymbol, CodegenClassScope codegenClassScope) {
        return new InstrumentationBuilderExpr(this.getClass(), this, "ExprConcat", requiredType, codegenMethodScope, exprSymbol, codegenClassScope).build();
    }

    public CodegenExpression evaluateCodegenUninstrumented(EPTypeClass requiredType, CodegenMethodScope codegenMethodScope, ExprForgeCodegenSymbol exprSymbol, CodegenClassScope codegenClassScope) {
        return ExprConcatNodeForgeEvalWNew.codegen(this, codegenMethodScope, exprSymbol, codegenClassScope);
    }

    public EPTypeClass getEvaluationType() {
        return EPTypePremade.STRING.getEPType();
    }
}
