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
package com.espertech.esper.common.internal.epl.enummethod.eval.aggregate;

import com.espertech.esper.common.internal.epl.enummethod.eval.EnumForge;
import com.espertech.esper.common.internal.epl.expression.core.ExprForge;
import com.espertech.esper.common.internal.event.arr.ObjectArrayEventType;

public abstract class EnumAggregateForge implements EnumForge {

    protected ExprForge initialization;
    protected ExprForge innerExpression;
    protected int streamNumLambda;
    protected ObjectArrayEventType resultEventType;

    public EnumAggregateForge(ExprForge initialization, ExprForge innerExpression, int streamNumLambda, ObjectArrayEventType resultEventType) {
        this.initialization = initialization;
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.resultEventType = resultEventType;
    }

    public int getStreamNumSize() {
        return streamNumLambda + 2;
    }

    public int getStreamNumLambda() {
        return streamNumLambda;
    }

    public ObjectArrayEventType getResultEventType() {
        return resultEventType;
    }

    public ExprForge getInitialization() {
        return initialization;
    }

    public ExprForge getInnerExpression() {
        return innerExpression;
    }
}
