package com.maksymkashevarov.arc.agent;

public final class AgentProfile {

    private String agentName = "";
    private boolean authorized = false;

    public String getAgentName() {
        return agentName;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void authorize(String agentName) {
        this.agentName = agentName;
        this.authorized = true;
    }
}