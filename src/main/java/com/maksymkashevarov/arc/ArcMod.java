package com.maksymkashevarov.arc;

import com.maksymkashevarov.arc.network.ArcNetworking;
import com.maksymkashevarov.arc.registry.ArcAttachments;
import com.maksymkashevarov.arc.registry.ArcBlockEntities;
import com.maksymkashevarov.arc.registry.ArcBlocks;
import com.maksymkashevarov.arc.registry.ArcItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ArcMod.MOD_ID)
public final class ArcMod {

    public static final String MOD_ID = "arc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ArcMod(IEventBus modEventBus, ModContainer modContainer) {
        ArcBlocks.register(modEventBus);
        ArcItems.register(modEventBus);
        ArcBlockEntities.register(modEventBus);
        ArcAttachments.register(modEventBus);

        modEventBus.addListener(ArcNetworking::register);

        LOGGER.info("ARC: Planetary Development initialized");
    }
}
