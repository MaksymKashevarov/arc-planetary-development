package com.maksymkashevarov.arc.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class ContractCoreMainScreen extends Screen {

    private static final int TOP_BAR_HEIGHT = 52;
    private static final int SIDEBAR_WIDTH = 42;
    private static final int FRAME_COLOR = 0xFF000000;
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
                        .bounds(this.width - 208, 8, 200, 20)
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

    private void renderHome(GuiGraphics graphics) {
        graphics.fill(0, 0, this.width, this.height, FRAME_COLOR);
        graphics.fill(SIDEBAR_WIDTH, TOP_BAR_HEIGHT, this.width, this.height, HOME_CONTENT_COLOR);
    }

    private void renderAgentData(GuiGraphics graphics) {
        // Preserve the existing Agent Data layout and behavior.
        final int profileTopBarHeight = 92;
        final int profileSidebarWidth = 108;
        graphics.fill(0, 0, this.width, this.height, 0xFF898989);
        graphics.fill(profileSidebarWidth, profileTopBarHeight, this.width, this.height, CONTENT_COLOR);
        this.renderAgentIconPlaceholder(graphics, profileSidebarWidth, profileTopBarHeight);

        String creditsLabel = "Credits:";
        String creditsValue = this.formatCredits(this.creditBalanceMinorUnits);
        int creditsX = this.width - 145;

        graphics.drawString(this.font, creditsLabel, creditsX, 34, DARK_TEXT, false);
        graphics.drawString(this.font, creditsValue,
                creditsX + this.font.width(creditsLabel) + 6, 34, GREEN, false);

        int contentX = profileSidebarWidth + 22;
        int contentY = profileTopBarHeight + 34;

        graphics.drawString(this.font, "Agent Name: " + this.agentName,
                contentX, contentY, DARK_TEXT, false);
        graphics.drawString(this.font, "Agent Status: AUTHORIZED",
                contentX, contentY + 24, DARK_TEXT, false);
        graphics.drawString(this.font, "ESC - Return",
                contentX + 18, this.height - 18, 0xFF555555, false);
    }

    private String formatCredits(long minorUnits) {
        long wholeCredits = minorUnits / 100;
        long fractionalCredits = minorUnits % 100;
        return wholeCredits + "." + String.format("%02d", fractionalCredits);
    }

    private void renderAgentIconPlaceholder(GuiGraphics graphics, int sidebarWidth, int topBarHeight) {
        graphics.fill(8, 8, sidebarWidth - 8, topBarHeight - 8, TILE_COLOR);
        int centerX = sidebarWidth / 2;
        graphics.fill(centerX - 18, 16, centerX + 18, 22, 0xFFBDBDBD);
        graphics.fill(centerX - 24, 22, centerX + 24, 46, 0xFFBDBDBD);
        graphics.fill(centerX - 18, 46, centerX + 18, 54, 0xFFBDBDBD);
        graphics.fill(centerX - 28, 58, centerX + 28, 64, 0xFFBDBDBD);
        graphics.fill(centerX - 34, 64, centerX - 28, topBarHeight - 14, 0xFFBDBDBD);
        graphics.fill(centerX + 28, 64, centerX + 34, topBarHeight - 14, 0xFFBDBDBD);
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
