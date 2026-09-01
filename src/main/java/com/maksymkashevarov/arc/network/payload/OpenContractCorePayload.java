package com.maksymkashevarov.arc.network.payload;

import com.maksymkashevarov.arc.ArcMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenContractCorePayload(String agentName) implements CustomPacketPayload {

    public static final Type<OpenContractCorePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(ArcMod.MOD_ID, "open_contract_core"));

    public static final StreamCodec<ByteBuf, OpenContractCorePayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    OpenContractCorePayload::agentName,
                    OpenContractCorePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
