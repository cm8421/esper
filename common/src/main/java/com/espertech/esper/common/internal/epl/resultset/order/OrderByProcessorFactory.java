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
package com.espertech.esper.common.internal.epl.resultset.order;

import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluatorContext;

/**
 * A processor for ordering output events according to the order specified in the order-by clause.
 */
public interface OrderByProcessorFactory {

    EPTypeClass EPTYPE = new EPTypeClass(OrderByProcessorFactory.class);

    OrderByProcessor instantiate(ExprEvaluatorContext exprEvaluatorContext);
}
