package com.maksymkashevarov.arc.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class ContractCoreMainScreen extends Screen {

    private static final int TOP_BAR_HEIGHT = 92;
    private static final int SIDEBAR_WIDTH = 108;

    private static final int TOP_BAR_COLOR = 0xFF898989;
    private static final int SIDEBAR_COLOR = 0xFF898989;
    private static final int CONTENT_COLOR = 0xFFC8C8C8;
    private static final int TILE_COLOR = 0xFF1F1F1F;
    private static final int GREEN = 0xFF20B95A;
    private static final int ORANGE = 0xFFFF7A1A;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int DARK_TEXT = 0xFF111111;

    private final String agentName;
    private View activeView = View.HOME;

    private Button agentDataButton;

    public ContractCoreMainScreen(String agentName) {
        super(Component.literal("Contract Core"));
        this.agentName = agentName;
    }

    @Override
    protected void init() {
        this.agentDataButton = this.addRenderableWidget(
                Button.builder(
                                Component.literal("Agent Data"),
                                button -> this.activeView = View.AGENT_DATA
                        )
                        .bounds(8, 38, 92, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        this.renderFrame(graphics);

        if (this.activeView == View.HOME) {
            this.renderHome(graphics);
        } else {
            this.renderAgentData(graphics);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderFrame(GuiGraphics graphics) {
        graphics.fill(
                0,
                0,
                this.width,
                TOP_BAR_HEIGHT,
                TOP_BAR_COLOR
        );

        graphics.fill(
                0,
                TOP_BAR_HEIGHT,
                SIDEBAR_WIDTH,
                this.height,
                SIDEBAR_COLOR
        );

        graphics.fill(
                SIDEBAR_WIDTH,
                TOP_BAR_HEIGHT,
                this.width,
                this.height,
                CONTENT_COLOR
        );
    }

    private void renderHome(GuiGraphics graphics) {
        this.agentDataButton.visible = true;

        graphics.drawString(
                this.font,
                "Auth:",
                18,
                16,
                WHITE,
                false
        );

        graphics.drawString(
                this.font,
                this.agentName,
                18 + this.font.width("Auth:") + 5,
                16,
                GREEN,
                false
        );

        this.renderSidebarTile(graphics, TOP_BAR_HEIGHT + 12, GREEN);
        this.renderSidebarTile(graphics, TOP_BAR_HEIGHT + 88, ORANGE);
    }

    private void renderAgentData(GuiGraphics graphics) {
        this.agentDataButton.visible = false;

        this.renderAgentIconPlaceholder(graphics);

        String creditsLabel = "Credits:";
        String creditsValue = "0.00";
        int creditsX = this.width - 220;

        graphics.drawString(
                this.font,
                creditsLabel,
                creditsX,
                34,
                DARK_TEXT,
                false
        );

        graphics.drawString(
                this.font,
                creditsValue,
                creditsX + this.font.width(creditsLabel) + 6,
                34,
                GREEN,
                false
        );

        int contentX = SIDEBAR_WIDTH + 54;
        int contentY = TOP_BAR_HEIGHT + 38;

        graphics.drawString(
                this.font,
                "Agent Name: " + this.agentName,
                contentX,
                contentY,
                DARK_TEXT,
                false
        );

        graphics.drawString(
                this.font,
                "Agent Status: AUTHORIZED",
                contentX,
                contentY + 24,
                DARK_TEXT,
                false
        );

        graphics.drawString(
                this.font,
                "ESC - Return",
                contentX,
                this.height - 24,
                0xFF555555,
                false
        );
    }

    private void renderSidebarTile(GuiGraphics graphics, int top, int accentColor) {
        int left = 4;
        int right = SIDEBAR_WIDTH - 12;
        int bottom = top + 70;

        graphics.fill(left, top, right, bottom, TILE_COLOR);

        int cx = (left + right) / 2;
        int cy = (top + bottom) / 2;

        graphics.fill(cx - 18, cy - 24, cx + 18, cy - 18, accentColor);
        graphics.fill(cx - 24, cy - 18, cx - 18, cy + 18, accentColor);
        graphics.fill(cx + 18, cy - 18, cx + 24, cy + 18, accentColor);
        graphics.fill(cx - 18, cy + 18, cx + 18, cy + 24, accentColor);
    }

    private void renderAgentIconPlaceholder(GuiGraphics graphics) {
        graphics.fill(8, 8, SIDEBAR_WIDTH - 8, TOP_BAR_HEIGHT - 8, TILE_COLOR);

        int centerX = SIDEBAR_WIDTH / 2;

        graphics.fill(centerX - 18, 16, centerX + 18, 22, 0xFFBDBDBD);
        graphics.fill(centerX - 24, 22, centerX + 24, 46, 0xFFBDBDBD);
        graphics.fill(centerX - 18, 46, centerX + 18, 54, 0xFFBDBDBD);

        graphics.fill(centerX - 28, 58, centerX + 28, 64, 0xFFBDBDBD);
        graphics.fill(centerX - 34, 64, centerX - 28, TOP_BAR_HEIGHT - 14, 0xFFBDBDBD);
        graphics.fill(centerX + 28, 64, centerX + 34, TOP_BAR_HEIGHT - 14, 0xFFBDBDBD);
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
