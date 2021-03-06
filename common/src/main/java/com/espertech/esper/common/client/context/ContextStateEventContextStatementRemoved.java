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
package com.espertech.esper.common.client.context;

/**
 * Context partition state event indicating a statement removed.
 */
public class ContextStateEventContextStatementRemoved extends ContextStateEvent {

    private final String statementDeploymentId;
    private final String statementName;

    /**
     * Ctor.
     *
     * @param runtimeURI            runtime URI
     * @param contextName           context name
     * @param contextDeploymentId   deployment id of create-context statement
     * @param statementDeploymentId statement deployment id
     * @param statementName         statement name
     */
    public ContextStateEventContextStatementRemoved(String runtimeURI, String contextDeploymentId, String contextName, String statementDeploymentId, String statementName) {
        super(runtimeURI, contextDeploymentId, contextName);
        this.statementDeploymentId = statementDeploymentId;
        this.statementName = statementName;
    }

    /**
     * Returns the statement name.
     *
     * @return name
     */
    public String getStatementName() {
        return statementName;
    }

    /**
     * Returns the deployment id
     *
     * @return deployment id
     */
    public String getStatementDeploymentId() {
        return statementDeploymentId;
    }
}
