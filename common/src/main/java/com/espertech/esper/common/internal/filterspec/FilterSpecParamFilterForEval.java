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
package com.espertech.esper.common.internal.filterspec;

import com.espertech.esper.common.internal.context.util.StatementContextFilterEvalEnv;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluatorContext;

public interface FilterSpecParamFilterForEval {
    /**
     * Returns the filter value representing the endpoint.
     *
     * @param matchedEvents        is the prior results
     * @param exprEvaluatorContext eval context
     * @param filterEvalEnv        env
     * @return filter value
     */
    public Object getFilterValue(MatchedEventMap matchedEvents, ExprEvaluatorContext exprEvaluatorContext, StatementContextFilterEvalEnv filterEvalEnv);
}
