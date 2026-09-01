package com.maksymkashevarov.arc.network;

import com.maksymkashevarov.arc.network.handler.ArcClientPayloadHandler;
import com.maksymkashevarov.arc.network.handler.ArcServerPayloadHandler;
import com.maksymkashevarov.arc.network.payload.AuthorizeAgentPayload;
import com.maksymkashevarov.arc.network.payload.OpenAgentAuthorizationPayload;
import com.maksymkashevarov.arc.network.payload.OpenContractCorePayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class ArcNetworking {

    private static final String NETWORK_VERSION = "1";

    private ArcNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NETWORK_VERSION);

        registrar.playToClient(
                OpenAgentAuthorizationPayload.TYPE,
                OpenAgentAuthorizationPayload.STREAM_CODEC,
                ArcClientPayloadHandler::handleOpenAuthorization
        );

        registrar.playToClient(
                OpenContractCorePayload.TYPE,
                OpenContractCorePayload.STREAM_CODEC,
                ArcClientPayloadHandler::handleOpenContractCore
        );

        registrar.playToServer(
                AuthorizeAgentPayload.TYPE,
                AuthorizeAgentPayload.STREAM_CODEC,
                ArcServerPayloadHandler::handleAuthorize
        );
    }
}
