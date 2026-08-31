package com.maksymkashevarov.arc.network.payload;

import com.maksymkashevarov.arc.ArcMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AuthorizeAgentPayload(String agentName) implements CustomPacketPayload {

    public static final Type<AuthorizeAgentPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(ArcMod.MOD_ID, "authorize_agent"));

    public static final StreamCodec<ByteBuf, AuthorizeAgentPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    AuthorizeAgentPayload::agentName,
                    AuthorizeAgentPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
