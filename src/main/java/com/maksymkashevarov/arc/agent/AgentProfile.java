package com.maksymkashevarov.arc.agent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class AgentProfile {

    public static final Codec<AgentProfile> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("agent_name").forGetter(AgentProfile::getAgentName),
                    Codec.BOOL.fieldOf("authorized").forGetter(AgentProfile::isAuthorized),
                    Codec.LONG.fieldOf("credit_balance_minor_units")
                            .forGetter(AgentProfile::getCreditBalanceMinorUnits)
            ).apply(instance, AgentProfile::new)
    );

    private String agentName = "";
    private boolean authorized = false;
    private long creditBalanceMinorUnits = 0L;

    public AgentProfile() {
    }

    private AgentProfile(
            String agentName,
            boolean authorized,
            long creditBalanceMinorUnits
    ) {
        this.agentName = agentName;
        this.authorized = authorized;
        this.creditBalanceMinorUnits = creditBalanceMinorUnits;
    }

    public String getAgentName() {
        return agentName;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public long getCreditBalanceMinorUnits() {
        return creditBalanceMinorUnits;
    }

    public void authorize(String agentName) {
        this.agentName = agentName;
        this.authorized = true;
    }
}
