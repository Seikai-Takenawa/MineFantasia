package com.takenawa.minefantasia.screen;

import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import com.takenawa.minefantasia.handler.MFInstrumentKeyHandler;
import com.takenawa.minefantasia.mapping.MFKeyToNoteMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class MFInstrumentScreen extends Screen {
    public MFInstrumentScreen(Component title) {
        super(title);
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ESCAPE) {
            MFInstrumentClientHandler.stopPlaying();
            this.onClose();
            return true;
        }

        if (MFKeyToNoteMapping.isPlayableKey(event.key())) {
            MFInstrumentKeyHandler.playNoteForKey(event.key());
            return true;
        }

        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0x00000000, 0x00000000);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }
}
