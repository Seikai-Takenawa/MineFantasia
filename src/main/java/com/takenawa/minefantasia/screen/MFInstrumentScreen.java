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
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0x2AFFF8DC, 0x2AFFF8DC);

        int rows = 3;
        int cols = 7;

        int blockSpacing = 8;

        int gridWidth = (int)(this.width * 0.6);
        int gridHeight = (int)(gridWidth * ((float)rows / cols));

        int blockSize = Math.min(
                (gridWidth - (cols - 1) * blockSpacing) / cols,
                (gridHeight - (rows - 1) * blockSpacing) / rows
        );

        gridWidth = blockSize * cols + blockSpacing * (cols - 1);
        gridHeight = blockSize * rows + blockSpacing * (rows - 1);

        int gridStartX = (this.width - gridWidth) / 2;
        int gridStartY = (this.height - gridHeight) / 2;

        int[] colors = {
                0x4AFF3EA7, 0x4AFF60B6, 0x4AFF76C0, 0x4AFF93CE, 0x4AFFB5DD,
                0x4AFFBFE2, 0x4AFFD5EC, 0x4A1F9DFF, 0x4A3AA9FF, 0x4A55B4FF,
                0x4A67BCFF, 0x4A77C2FC, 0x4A8FCDFC, 0x4AB1DDFF, 0x4AFF5C00,
                0x4AFF7120, 0x4AFE813B, 0x4AFF8D4D, 0x4AFF9457, 0x4AFFAA7A,
                0x4AFFBA93
        };

        int colorIndex = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = gridStartX + col * (blockSize + blockSpacing);
                int y = gridStartY + row * (blockSize + blockSpacing);

                int color = colors[colorIndex % colors.length];
                guiGraphics.fillGradient(
                        x, y,
                        x + blockSize, y + blockSize,
                        color, color
                );

                guiGraphics.fillGradient(
                        x, y,
                        x + blockSize, y + 1,
                        0xFFFFFFFF, 0xFFFFFFFF
                );
                guiGraphics.fillGradient(
                        x, y,
                        x + 1, y + blockSize,
                        0xFFFFFFFF, 0xFFFFFFFF
                );
                guiGraphics.fillGradient(
                        x + blockSize - 1, y,
                        x + blockSize, y + blockSize,
                        0xFFFFFFFF, 0xFFFFFFFF
                );
                guiGraphics.fillGradient(
                        x, y + blockSize - 1,
                        x + blockSize, y + blockSize,
                        0xFFFFFFFF, 0xFFFFFFFF
                );

                colorIndex++;
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }
}
