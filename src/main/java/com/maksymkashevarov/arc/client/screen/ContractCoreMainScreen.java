package com.maksymkashevarov.arc.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class ContractCoreMainScreen extends Screen {

    private static final int FRAME_TOP_BAR_HEIGHT = 28;
    private static final int FRAME_SIDEBAR_WIDTH = 12;
    private static final int FRAME_COLOR = 0xFF000000;
    private static final int PROFILE_FRAME_COLOR = 0xFF898989;
    private static final int HOME_CONTENT_COLOR = 0xFF898989;
    private static final int CONTENT_COLOR = 0xFFC8C8C8;
    private static final int TILE_COLOR = 0xFF1F1F1F;
    private static final int GREEN = 0xFF20B95A;
    private static final int DARK_TEXT = 0xFF111111;

    private final String agentName;
    private final long creditBalanceMinorUnits;
    private View activeView = View.HOME;
    private Button profileButton;

    public ContractCoreMainScreen(String agentName, long creditBalanceMinorUnits) {
        super(Component.literal("Contract Core"));
        this.agentName = agentName;
        this.creditBalanceMinorUnits = creditBalanceMinorUnits;
    }

    @Override
    protected void init() {
        this.profileButton = this.addRenderableWidget(
                Button.builder(Component.literal("Profile"), button -> this.activeView = View.AGENT_DATA)
                        .bounds(this.width - 84, 5, 76, 18)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (this.activeView == View.HOME) {
            this.renderHome(graphics);
        } else {
            this.renderAgentData(graphics);
        }
        this.profileButton.visible = this.activeView == View.HOME;
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // The opaque ARC frame is rendered directly. Do not blur it again.
    }

    private void renderFrame(GuiGraphics graphics, int frameColor, int contentColor) {
        graphics.fill(0, 0, this.width, this.height, frameColor);
        graphics.fill(FRAME_SIDEBAR_WIDTH, FRAME_TOP_BAR_HEIGHT,
                this.width, this.height, contentColor);
    }

    private void renderHome(GuiGraphics graphics) {
        this.renderFrame(graphics, FRAME_COLOR, HOME_CONTENT_COLOR);
    }

    private void renderAgentData(GuiGraphics graphics) {
        this.renderFrame(graphics, PROFILE_FRAME_COLOR, CONTENT_COLOR);
        this.renderAgentIconPlaceholder(graphics);

        String creditsLabel = "Credits:";
        String creditsValue = this.formatCredits(this.creditBalanceMinorUnits);
        int creditsX = this.width - this.font.width(creditsLabel)
                - this.font.width(creditsValue) - 18;

        graphics.drawString(this.font, creditsLabel, creditsX, 10, DARK_TEXT, false);
        graphics.drawString(this.font, creditsValue,
                creditsX + this.font.width(creditsLabel) + 6, 10, GREEN, false);

        int contentX = FRAME_SIDEBAR_WIDTH + 18;
        int contentY = FRAME_TOP_BAR_HEIGHT + 34;

        graphics.drawString(this.font, "Agent Name: " + this.agentName,
                contentX, contentY, DARK_TEXT, false);
        graphics.drawString(this.font, "Agent Status: AUTHORIZED",
                contentX, contentY + 24, DARK_TEXT, false);
        graphics.drawString(this.font, "ESC - Return",
                contentX, this.height - 18, 0xFF555555, false);
    }

    private String formatCredits(long minorUnits) {
        long wholeCredits = minorUnits / 100;
        long fractionalCredits = minorUnits % 100;
        return wholeCredits + "." + String.format("%02d", fractionalCredits);
    }

    private void renderAgentIconPlaceholder(GuiGraphics graphics) {
        // Small placeholder in the header; the player portrait can replace it later.
        graphics.fill(3, 3, 25, 25, TILE_COLOR);
        graphics.fill(10, 6, 18, 13, 0xFFBDBDBD);
        graphics.fill(7, 15, 21, 22, 0xFFBDBDBD);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.activeView != View.HOME) {
            this.activeView = View.HOME;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private enum View {
        HOME,
        AGENT_DATA
    }
}
