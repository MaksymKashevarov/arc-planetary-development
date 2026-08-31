package com.maksymkashevarov.arc.registry;

import com.maksymkashevarov.arc.ArcMod;
import com.maksymkashevarov.arc.world.block.ContractCoreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArcMod.MOD_ID);

    public static final DeferredBlock<ContractCoreBlock> CONTRACT_CORE = BLOCKS.registerBlock(
            "contract_core",
            ContractCoreBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F)
                    .sound(SoundType.METAL)
    );

    private ArcBlocks() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
