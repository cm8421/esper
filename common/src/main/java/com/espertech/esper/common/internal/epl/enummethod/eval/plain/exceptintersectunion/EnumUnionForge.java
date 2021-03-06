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
package com.espertech.esper.common.internal.epl.enummethod.eval.plain.exceptintersectunion;

import com.espertech.esper.common.internal.bytecodemodel.base.CodegenClassScope;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenMethodScope;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpression;
import com.espertech.esper.common.internal.epl.enummethod.codegen.EnumForgeCodegenParams;
import com.espertech.esper.common.internal.epl.enummethod.eval.EnumEval;
import com.espertech.esper.common.internal.epl.enummethod.eval.EnumForge;
import com.espertech.esper.common.internal.epl.expression.core.ExprEnumerationForge;

public class EnumUnionForge implements EnumForge {

    private final int numStreams;
    protected final ExprEnumerationForge evaluatorForge;
    protected final boolean scalar;

    public EnumUnionForge(int numStreams, ExprEnumerationForge evaluatorForge, boolean scalar) {
        this.numStreams = numStreams;
        this.evaluatorForge = evaluatorForge;
        this.scalar = scalar;
    }

    public int getStreamNumSize() {
        return numStreams;
    }

    public EnumEval getEnumEvaluator() {
        return new EnumUnionForgeEval(this, evaluatorForge.getExprEvaluatorEnumeration());
    }

    public CodegenExpression codegen(EnumForgeCodegenParams premade, CodegenMethodScope codegenMethodScope, CodegenClassScope codegenClassScope) {
        return EnumUnionForgeEval.codegen(this, premade, codegenMethodScope, codegenClassScope);
    }
}
