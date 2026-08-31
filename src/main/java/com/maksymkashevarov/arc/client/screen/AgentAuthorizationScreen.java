package com.maksymkashevarov.arc.client.screen;

import com.maksymkashevarov.arc.network.payload.AuthorizeAgentPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public final class AgentAuthorizationScreen extends Screen {

    private static final int PANEL_WIDTH = 240;
    private static final int PANEL_HEIGHT = 140;
    private static final int FIELD_WIDTH = 200;

    private EditBox agentNameBox;

    public AgentAuthorizationScreen() {
        super(Component.literal("ARC Authorization"));
    }

    @Override
    protected void init() {
        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        this.agentNameBox = new EditBox(
                this.font,
                panelX + 20,
                panelY + 58,
                FIELD_WIDTH,
                20,
                Component.literal("Agent Name")
        );
        this.agentNameBox.setMaxLength(32);

        this.addRenderableWidget(this.agentNameBox);

        this.addRenderableWidget(
                Button.builder(
                                Component.literal("AUTHORIZE"),
                                button -> authorize()
                        )
                        .bounds(panelX + 20, panelY + 96, FIELD_WIDTH, 20)
                        .build()
        );
    }

    private void authorize() {
        String agentName = this.agentNameBox.getValue().strip();

        if (agentName.isEmpty()) {
            return;
        }

        PacketDistributor.sendToServer(new AuthorizeAgentPayload(agentName));
        this.onClose();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        int panelX = (this.width - PANEL_WIDTH) / 2;
        int panelY = (this.height - PANEL_HEIGHT) / 2;

        graphics.fill(
                panelX - 1,
                panelY - 1,
                panelX + PANEL_WIDTH + 1,
                panelY + PANEL_HEIGHT + 1,
                0xFF8A8A8A
        );

        graphics.fill(
                panelX,
                panelY,
                panelX + PANEL_WIDTH,
                panelY + PANEL_HEIGHT,
                0xFF20242A
        );

        graphics.drawCenteredString(
                this.font,
                "ARC AUTHORIZATION",
                this.width / 2,
                panelY + 16,
                0xFFFFFFFF
        );

        graphics.drawString(
                this.font,
                "Agent Name",
                panelX + 20,
                panelY + 45,
                0xFFC6CBD2,
                false
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
