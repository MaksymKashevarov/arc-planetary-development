package com.maksymkashevarov.arc.network.handler;

import com.maksymkashevarov.arc.agent.AgentProfile;
import com.maksymkashevarov.arc.network.payload.AuthorizeAgentPayload;
import com.maksymkashevarov.arc.network.payload.OpenContractCorePayload;
import com.maksymkashevarov.arc.registry.ArcAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ArcServerPayloadHandler {

    private static final int MAX_AGENT_NAME_LENGTH = 32;

    private ArcServerPayloadHandler() {
    }

    public static void handleAuthorize(AuthorizeAgentPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        String agentName = payload.agentName().strip();

        if (agentName.isEmpty() || agentName.length() > MAX_AGENT_NAME_LENGTH) {
            serverPlayer.sendSystemMessage(
                    Component.literal("ARC authorization rejected: agent name must contain 1-32 characters.")
            );
            return;
        }

        AgentProfile profile = serverPlayer.getData(ArcAttachments.AGENT_PROFILE);

        if (profile.isAuthorized()) {
            return;
        }

        profile.authorize(agentName);
        serverPlayer.setData(ArcAttachments.AGENT_PROFILE, profile);

        serverPlayer.sendSystemMessage(
                Component.literal("ARC authorization complete. Agent: " + profile.getAgentName())
        );

        PacketDistributor.sendToPlayer(
                serverPlayer,
                new OpenContractCorePayload(
                        profile.getAgentName(),
                        profile.getCreditBalanceMinorUnits()
                )
        );
    }
}
