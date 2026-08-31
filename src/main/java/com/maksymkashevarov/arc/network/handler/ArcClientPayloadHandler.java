package com.maksymkashevarov.arc.network.handler;

import com.maksymkashevarov.arc.client.screen.AgentAuthorizationScreen;
import com.maksymkashevarov.arc.network.payload.OpenAgentAuthorizationPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ArcClientPayloadHandler {

    private ArcClientPayloadHandler() {
    }

    public static void handleOpenAuthorization(
            OpenAgentAuthorizationPayload payload,
            IPayloadContext context
    ) {
        Minecraft.getInstance().setScreen(new AgentAuthorizationScreen());
    }
}
