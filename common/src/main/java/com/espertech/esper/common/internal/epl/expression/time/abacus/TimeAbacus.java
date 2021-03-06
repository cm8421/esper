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
package com.espertech.esper.common.internal.epl.expression.time.abacus;

import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenClassScope;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenMethodScope;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpression;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpressionRef;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public interface TimeAbacus extends Serializable {
    EPTypeClass EPTYPE = new EPTypeClass(TimeAbacus.class);

    long deltaForSecondsNumber(Number timeInSeconds);

    long deltaForSecondsDouble(double seconds);

    long calendarSet(long fromTime, Calendar cal);

    long calendarGet(Calendar cal, long remainder);

    long getOneSecond();

    Date toDate(long ts);

    CodegenExpression calendarSetCodegen(CodegenExpression startLong, CodegenExpression cal, CodegenMethodScope codegenMethodScope, CodegenClassScope codegenClassScope);

    CodegenExpression calendarGetCodegen(CodegenExpression cal, CodegenExpression startRemainder, CodegenClassScope codegenClassScope);

    CodegenExpression toDateCodegen(CodegenExpression ts);

    CodegenExpression deltaForSecondsDoubleCodegen(CodegenExpressionRef sec, CodegenClassScope codegenClassScope);
}
