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
package com.espertech.esper.common.internal.event.variant;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.type.EPTypeClass;

/**
 * A variant event is a type that can represent many event types.
 */
public interface VariantEvent {
    EPTypeClass EPTYPE = new EPTypeClass(VariantEvent.class);

    /**
     * Returns the underlying event.
     *
     * @return underlying event
     */
    public EventBean getUnderlyingEventBean();
}
