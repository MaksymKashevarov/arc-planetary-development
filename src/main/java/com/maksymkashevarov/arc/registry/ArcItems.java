package com.maksymkashevarov.arc.registry;

import com.maksymkashevarov.arc.ArcMod;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArcMod.MOD_ID);

    public static final DeferredItem<BlockItem> CONTRACT_CORE =
            ITEMS.registerSimpleBlockItem("contract_core", ArcBlocks.CONTRACT_CORE);

    private ArcItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
