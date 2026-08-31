package com.maksymkashevarov.arc;

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
        LOGGER.info("ARC: Planetary Development initialized");
    }
}