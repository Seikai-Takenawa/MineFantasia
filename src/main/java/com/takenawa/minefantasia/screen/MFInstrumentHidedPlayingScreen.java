package com.takenawa.minefantasia.screen;

import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import com.takenawa.minefantasia.handler.MFInstrumentKeyHandler;
import com.takenawa.minefantasia.mapping.MFKeyToNoteMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class MFInstrumentHidedPlayingScreen extends Screen {
    private int eyeButtonX, eyeButtonY;
    private int upButtonX, upButtonY;
    private int downButtonX, downButtonY;
    private final int eyeButtonSize = 20;
    private final int octaveButtonSize = 20;

    protected MFInstrumentHidedPlayingScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        eyeButtonX = this.width - eyeButtonSize - 10;
        eyeButtonY = this.height - eyeButtonSize - 10;
        upButtonX = 10;
        upButtonY = this.height - octaveButtonSize * 2 - 20;
        downButtonX = 10;
        downButtonY = this.height - octaveButtonSize - 10;
    }

    @Override
    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean bool) {
        if (event.button() == 0) {
            if (isMouseOverEyeButton(event.x(), event.y())) {
                this.onClose();
                Minecraft.getInstance().setScreen(new MFInstrumentPlayingScreen(Component.literal("")));
                return true;
            }

            if (isMouseOverUpButton(event.x(), event.y())) {
                MFKeyToNoteMapping.raiseOctave();
                return true;
            }

            if (isMouseOverDownButton(event.x(), event.y())) {
                MFKeyToNoteMapping.lowerOctave();
                return true;
            }
        }
        return super.mouseClicked(event, bool);
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        int key = event.key();

        if (key == GLFW.GLFW_KEY_ESCAPE) {
            MFInstrumentClientHandler.stopPlaying();
            this.onClose();
            return true;
        }

        if (MFKeyToNoteMapping.isPlayableKey(key)) {
            MFInstrumentKeyHandler.playNoteForKey(key);
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderEyeButton(guiGraphics, mouseX, mouseY);
        renderOctaveButtons(guiGraphics, mouseX, mouseY);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void renderEyeButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonColor = 0x4A808080;
        int borderColor = 0xFFFFFFFF;

        if (isMouseOverEyeButton(mouseX, mouseY)) {
            buttonColor = 0x4AFFA500;
            borderColor = 0xFFFFFF00;
        }

        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + eyeButtonSize, eyeButtonY + eyeButtonSize,
                buttonColor, buttonColor
        );

        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + eyeButtonSize, eyeButtonY + 1,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + 1, eyeButtonY + eyeButtonSize,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX + eyeButtonSize - 1, eyeButtonY,
                eyeButtonX + eyeButtonSize, eyeButtonY + eyeButtonSize,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY + eyeButtonSize - 1,
                eyeButtonX + eyeButtonSize, eyeButtonY + eyeButtonSize,
                borderColor, borderColor
        );

        String eyeSymbol = "👁";
        int textX = eyeButtonX + eyeButtonSize / 2 - 4;
        int textY = eyeButtonY + eyeButtonSize / 2 - 4;
        guiGraphics.drawString(this.font, eyeSymbol, textX, textY, 0xFF000000, false);
    }

    private void renderOctaveButtons(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int upButtonColor = isMouseOverUpButton(mouseX, mouseY) ? 0x4AFFA500 : 0x4A808080;
        int upBorderColor = isMouseOverUpButton(mouseX, mouseY) ? 0xFFFFFF00 : 0xFFFFFFFF;

        renderButtonBackground(guiGraphics, upButtonColor, upBorderColor, upButtonX, upButtonY);

        String upSymbol = "↑";
        int upTextX = upButtonX + octaveButtonSize / 2 - 3;
        int upTextY = upButtonY + octaveButtonSize / 2 - 4;
        guiGraphics.drawString(this.font, upSymbol, upTextX, upTextY, 0xFF000000, false);

        int downButtonColor = isMouseOverDownButton(mouseX, mouseY) ? 0x4AFFA500 : 0x4A808080;
        int downBorderColor = isMouseOverDownButton(mouseX, mouseY) ? 0xFFFFFF00 : 0xFFFFFFFF;

        renderButtonBackground(guiGraphics, downButtonColor, downBorderColor, downButtonX, downButtonY);

        String downSymbol = "↓";
        int downTextX = downButtonX + octaveButtonSize / 2 - 3;
        int downTextY = downButtonY + octaveButtonSize / 2 - 4;
        guiGraphics.drawString(this.font, downSymbol, downTextX, downTextY, 0xFF000000, false);
    }

    private void renderButtonBackground(GuiGraphics guiGraphics, int buttonColor, int borderColor, int buttonX, int buttonY) {
        guiGraphics.fillGradient(
                buttonX, buttonY,
                buttonX + octaveButtonSize, buttonY + octaveButtonSize,
                buttonColor, buttonColor
        );

        guiGraphics.fillGradient(
                buttonX, buttonY,
                buttonX + octaveButtonSize, buttonY + 1,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                buttonX, buttonY,
                buttonX + 1, buttonY + octaveButtonSize,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                buttonX + octaveButtonSize - 1, buttonY,
                buttonX + octaveButtonSize, buttonY + octaveButtonSize,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                buttonX, buttonY + octaveButtonSize - 1,
                buttonX + octaveButtonSize, buttonY + octaveButtonSize,
                borderColor, borderColor
        );
    }

    private boolean isMouseOverUpButton(double mouseX, double mouseY) {
        return mouseX >= upButtonX && mouseX <= upButtonX + octaveButtonSize &&
                mouseY >= upButtonY && mouseY <= upButtonY + octaveButtonSize;
    }

    private boolean isMouseOverDownButton(double mouseX, double mouseY) {
        return mouseX >= downButtonX && mouseX <= downButtonX + octaveButtonSize &&
                mouseY >= downButtonY && mouseY <= downButtonY + octaveButtonSize;
    }

    private boolean isMouseOverEyeButton(double mouseX, double mouseY) {
        return mouseX >= eyeButtonX && mouseX <= eyeButtonX + eyeButtonSize &&
                mouseY >= eyeButtonY && mouseY <= eyeButtonY + eyeButtonSize;
    }
}
