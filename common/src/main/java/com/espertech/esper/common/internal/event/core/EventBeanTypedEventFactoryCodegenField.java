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
package com.espertech.esper.common.internal.event.core;

import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenFieldSharable;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpression;
import com.espertech.esper.common.internal.context.module.EPStatementInitServices;

import static com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpressionBuilder.exprDotMethod;

public class EventBeanTypedEventFactoryCodegenField implements CodegenFieldSharable {
    public final static EventBeanTypedEventFactoryCodegenField INSTANCE = new EventBeanTypedEventFactoryCodegenField();

    private EventBeanTypedEventFactoryCodegenField() {
    }

    public EPTypeClass type() {
        return EventBeanTypedEventFactory.EPTYPE;
    }

    public CodegenExpression initCtorScoped() {
        return exprDotMethod(EPStatementInitServices.REF, EPStatementInitServices.GETEVENTBEANTYPEDEVENTFACTORY);
    }
}

