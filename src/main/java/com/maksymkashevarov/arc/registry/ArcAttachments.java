package com.maksymkashevarov.arc.registry;

import com.maksymkashevarov.arc.ArcMod;
import com.maksymkashevarov.arc.agent.AgentProfile;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ArcAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ArcMod.MOD_ID);

    public static final Supplier<AttachmentType<AgentProfile>> AGENT_PROFILE =
            ATTACHMENTS.register(
                    "agent_profile",
                    () -> AttachmentType.builder(AgentProfile::new)
                            .serialize(AgentProfile.CODEC)
                            .copyOnDeath()
                            .build()
            );

    private ArcAttachments() {
    }

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
