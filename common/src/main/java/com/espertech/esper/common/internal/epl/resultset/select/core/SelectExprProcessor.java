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
package com.espertech.esper.common.internal.epl.resultset.select.core;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.type.EPTypeClass;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluatorContext;

/**
 * Interface for processors of select-clause items, implementors are computing results based on matching events.
 */
public interface SelectExprProcessor {
    EPTypeClass EPTYPE = new EPTypeClass(SelectExprProcessor.class);
    EPTypeClass EPTYPEARRAY = new EPTypeClass(SelectExprProcessor[].class);

    /**
     * Computes the select-clause results and returns an event of the result event type that contains, in it's
     * properties, the selected items.
     *
     * @param eventsPerStream      - is per stream the event
     * @param isNewData            - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @param isSynthesize         - set to true to indicate that synthetic events are required for an iterator result set
     * @param exprEvaluatorContext context
     * @return event with properties containing selected items
     */
    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize, ExprEvaluatorContext exprEvaluatorContext);
}
