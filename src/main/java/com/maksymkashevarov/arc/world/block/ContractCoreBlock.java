package com.maksymkashevarov.arc.world.block;

import com.maksymkashevarov.arc.agent.AgentProfile;
import com.maksymkashevarov.arc.network.payload.OpenAgentAuthorizationPayload;
import com.maksymkashevarov.arc.network.payload.OpenContractCorePayload;
import com.maksymkashevarov.arc.registry.ArcAttachments;
import com.maksymkashevarov.arc.world.block.entity.ContractCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;

public final class ContractCoreBlock extends BaseEntityBlock {

    public static final MapCodec<ContractCoreBlock> CODEC = simpleCodec(ContractCoreBlock::new);

    public ContractCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ContractCoreBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            AgentProfile profile = serverPlayer.getData(ArcAttachments.AGENT_PROFILE);

            if (!profile.isAuthorized()) {
                PacketDistributor.sendToPlayer(serverPlayer, OpenAgentAuthorizationPayload.INSTANCE);
            } else {
                PacketDistributor.sendToPlayer(
                        serverPlayer,
                        new OpenContractCorePayload(profile.getAgentName())
                );
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
