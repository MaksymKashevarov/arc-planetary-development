package com.maksymkashevarov.arc.agent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class AgentProfile {

    public static final Codec<AgentProfile> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("agent_name").forGetter(AgentProfile::getAgentName),
                    Codec.BOOL.fieldOf("authorized").forGetter(AgentProfile::isAuthorized)
            ).apply(instance, AgentProfile::new)
    );

    private String agentName = "";
    private boolean authorized = false;

    public AgentProfile() {
    }

    private AgentProfile(String agentName, boolean authorized) {
        this.agentName = agentName;
        this.authorized = authorized;
    }

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
