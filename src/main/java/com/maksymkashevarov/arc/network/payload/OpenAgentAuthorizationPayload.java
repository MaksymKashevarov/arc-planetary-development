package com.maksymkashevarov.arc.network.payload;

import com.maksymkashevarov.arc.ArcMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenAgentAuthorizationPayload() implements CustomPacketPayload {

    public static final OpenAgentAuthorizationPayload INSTANCE = new OpenAgentAuthorizationPayload();

    public static final Type<OpenAgentAuthorizationPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(ArcMod.MOD_ID, "open_agent_authorization"));

    public static final StreamCodec<ByteBuf, OpenAgentAuthorizationPayload> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
