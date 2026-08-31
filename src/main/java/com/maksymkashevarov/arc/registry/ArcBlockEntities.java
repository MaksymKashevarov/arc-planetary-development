package com.maksymkashevarov.arc.registry;

import com.maksymkashevarov.arc.ArcMod;
import com.maksymkashevarov.arc.world.block.entity.ContractCoreBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ArcMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ContractCoreBlockEntity>> CONTRACT_CORE =
            BLOCK_ENTITIES.register(
                    "contract_core",
                    () -> BlockEntityType.Builder.of(
                            ContractCoreBlockEntity::new,
                            ArcBlocks.CONTRACT_CORE.get()
                    ).build(null)
            );

    private ArcBlockEntities() {
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
