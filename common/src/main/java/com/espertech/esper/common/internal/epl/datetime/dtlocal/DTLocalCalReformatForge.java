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
package com.espertech.esper.common.internal.epl.datetime.dtlocal;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenClassScope;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenMethodScope;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpression;
import com.espertech.esper.common.internal.epl.datetime.reformatop.ReformatForge;
import com.espertech.esper.common.internal.epl.datetime.reformatop.ReformatOp;
import com.espertech.esper.common.internal.epl.expression.codegen.ExprForgeCodegenSymbol;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluatorContext;

import java.util.Calendar;

public class DTLocalCalReformatForge extends DTLocalReformatForgeBase {
    public DTLocalCalReformatForge(ReformatForge reformatForge) {
        super(reformatForge);
    }

    public DTLocalEvaluator getDTEvaluator() {
        return new DTLocalCalReformatEval(reformatForge.getOp());
    }

    public CodegenExpression codegen(CodegenExpression inner, EPTypeClass innerType, CodegenMethodScope codegenMethodScope, ExprForgeCodegenSymbol exprSymbol, CodegenClassScope codegenClassScope) {
        return reformatForge.codegenCal(inner, codegenMethodScope, exprSymbol, codegenClassScope);
    }

    private static class DTLocalCalReformatEval extends DTLocalReformatEvalBase {
        private DTLocalCalReformatEval(ReformatOp reformatOp) {
            super(reformatOp);
        }

        public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
            return reformatOp.evaluate((Calendar) target, eventsPerStream, isNewData, exprEvaluatorContext);
        }
    }
}
