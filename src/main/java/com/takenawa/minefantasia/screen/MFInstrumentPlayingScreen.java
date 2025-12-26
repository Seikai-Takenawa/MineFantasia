package com.takenawa.minefantasia.screen;

import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import com.takenawa.minefantasia.handler.MFInstrumentKeyHandler;
import com.takenawa.minefantasia.mapping.MFKeyToNoteMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class MFInstrumentPlayingScreen extends Screen {
    private boolean isSettingMode = false;
    private int selectedKeyCode = -1;
    private EditBox noteInputBox;
    private int gridStartX, gridStartY;
    private int blockSize;
    private int settingsButtonX, settingsButtonY;
    private int eyeButtonX, eyeButtonY;
    private int upButtonX, upButtonY;
    private int downButtonX, downButtonY;
    private final int blockSpacing = 8;
    private final int rows = 3;
    private final int cols = 7;
    private final int settingsButtonSize = 20;
    private final int eyeButtonSize = 20;
    private final int octaveButtonSize = 20;
    private final Map<Integer, Long> pressedKeys = new HashMap<>();
    private static final Map<Integer, Integer[]> KEY_MAPPING = new HashMap<>();
    private static final long KEY_PRESS_DURATION = 150;

    static {
        KEY_MAPPING.put(0, new Integer[]{81, 87, 69, 82, 84, 89, 85});  // Q,W,E,R,T,Y,U
        KEY_MAPPING.put(1, new Integer[]{65, 83, 68, 70, 71, 72, 74});  // A,S,D,F,G,H,J
        KEY_MAPPING.put(2, new Integer[]{90, 88, 67, 86, 66, 78, 77});  // Z,X,C,V,B,N,M
    }

    public MFInstrumentPlayingScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        int gridWidth = (int)(this.width * 0.6);
        int gridHeight = (int)(gridWidth * ((float)rows / cols));

        blockSize = Math.min(
                (gridWidth - (cols - 1) * blockSpacing) / cols,
                (gridHeight - (rows - 1) * blockSpacing) / rows
        );

        int actualGridWidth = blockSize * cols + blockSpacing * (cols - 1);
        int actualGridHeight = blockSize * rows + blockSpacing * (rows - 1);

        gridStartX = (this.width - actualGridWidth) / 2;
        gridStartY = (this.height - actualGridHeight) / 2;

        settingsButtonX = this.width - settingsButtonSize - 10;
        settingsButtonY = this.height - settingsButtonSize - 10;

        eyeButtonX = settingsButtonX;
        eyeButtonY = settingsButtonY - eyeButtonSize - 10;

        upButtonX = 10;
        upButtonY = this.height - octaveButtonSize * 2 - 20;

        downButtonX = 10;
        downButtonY = this.height - octaveButtonSize - 10;

        noteInputBox = new EditBox(
                this.font,
                this.width / 2 - 60, this.height / 2 + 80,
                120, 20,
                Component.translatable("screen.minefantasia.note_input")
        );
        noteInputBox.setMaxLength(3);
        noteInputBox.setVisible(isSettingMode);
        noteInputBox.setCanLoseFocus(true);
        this.addRenderableWidget(noteInputBox);
    }

    @Override
    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean bool) {
        if (event.button() == 0) {
            double mouseX = event.x();
            double mouseY = event.y();

            int inputBoxX = this.width / 2 - 60;
            int inputBoxY = this.height / 2 + 80;
            int inputBoxWidth = 120;
            int inputBoxHeight = 20;

            boolean clickedInputBox = mouseX >= inputBoxX && mouseX <= inputBoxX + inputBoxWidth &&
                    mouseY >= inputBoxY && mouseY <= inputBoxY + inputBoxHeight;

            if (clickedInputBox) {
                noteInputBox.setFocused(true);
                return true;
            }

            if (isMouseOverEyeButton(event.x(), event.y())) {
                this.onClose();
                Minecraft.getInstance().setScreen(new MFInstrumentHidedPlayingScreen(Component.literal("")));
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

            if (isMouseOverSettingsButton(event.x(), event.y())) {
                isSettingMode = !isSettingMode;
                noteInputBox.setVisible(isSettingMode);
                if (!isSettingMode) {
                    if (selectedKeyCode != -1){
                        saveNoteMapping();
                    }
                    selectedKeyCode = -1;
                    noteInputBox.setFocused(false);
                }
                noteInputBox.setValue("");
                return true;
            }

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (isMouseOverBlock(event.x(), event.y(), row, col)) {
                        handleBlockClick(row, col);
                        return true;
                    }
                }
            }

            if (isSettingMode) {
                noteInputBox.setFocused(false);
            }
        }
        return super.mouseClicked(event, bool);
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        int key = event.key();
        if (!isSettingMode && MFKeyToNoteMapping.isPlayableKey(key)) {
            pressedKeys.put(key, System.currentTimeMillis());
        }

        if (key == GLFW.GLFW_KEY_ESCAPE) {
            if (isSettingMode && selectedKeyCode != -1) {
                selectedKeyCode = -1;
                noteInputBox.setValue("");
                noteInputBox.setFocused(false);
            }else if(!isSettingMode) {
                MFInstrumentClientHandler.stopPlaying();
                this.onClose();
            }
            return true;
        }

        if (isSettingMode) {
            if (noteInputBox.isFocused()) {
                if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {
                    saveNoteMapping();
                    selectedKeyCode = -1;
                    noteInputBox.setValue("");
                    noteInputBox.setFocused(false);
                    return true;
                }
                return noteInputBox.keyPressed(event);
            }
            if (MFKeyToNoteMapping.isPlayableKey(key)) {
                for (int row = 0; row < rows; row++) {
                    Integer[] rowKeys = KEY_MAPPING.get(row);
                    if (rowKeys != null) {
                        for (Integer rowKey : rowKeys) {
                            if (rowKey == key) {
                                selectedKeyCode = key;
                                noteInputBox.setValue("");
                                return true;
                            }
                        }
                    }
                }
            }
            return true;
        }

        if (MFKeyToNoteMapping.isPlayableKey(key)) {
            MFInstrumentKeyHandler.playNoteForKey(key);
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(@NotNull CharacterEvent event) {
        if (isSettingMode && noteInputBox.isFocused()) {
            return noteInputBox.charTyped(event);
        }
        return super.charTyped(event);
    }

    @Override
    public boolean keyReleased(@NotNull KeyEvent event) {
        pressedKeys.remove(event.key());
        return super.keyReleased(event);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0x2AFFF8DC, 0x2AFFF8DC);

        int rows = this.rows;
        int cols = this.cols;

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

                int baseColor = colors[colorIndex % colors.length];
                boolean isKeyPressed = false;

                Integer[] rowKeys = KEY_MAPPING.get(row);
                if (rowKeys != null && col < rowKeys.length) {
                    int keyCode = rowKeys[col];
                    Long pressTime = pressedKeys.get(keyCode);
                    if (pressTime != null) {
                        long timeSincePress = System.currentTimeMillis() - pressTime;
                        if (timeSincePress < KEY_PRESS_DURATION) {
                            isKeyPressed = true;
                        } else {
                            pressedKeys.remove(keyCode);
                        }
                    }
                }

                int color = isKeyPressed ? darkenColor(baseColor) : baseColor;
                guiGraphics.fillGradient(
                        x, y,
                        x + blockSize, y + blockSize,
                        color, color
                );

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

                if (row < KEY_MAPPING.size()) {
                    rowKeys = KEY_MAPPING.get(row);
                    if (rowKeys != null && col < rowKeys.length) {
                        int keyCode = rowKeys[col];
                        String note = MFKeyToNoteMapping.getNoteForKey(keyCode);
                        if (note != null) {
                            int textX = x + blockSize / 2 - 4;
                            int textY = y + blockSize / 2 - 4;
                            guiGraphics.drawString(this.font, note, textX, textY, 0xFFFFFFFF, false);
                        }
                    }
                }
                colorIndex++;
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        long currentTime = System.currentTimeMillis();
        pressedKeys.entrySet().removeIf(entry ->
                currentTime - entry.getValue() > KEY_PRESS_DURATION
        );

        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        renderSettingsButton(guiGraphics, mouseX, mouseY);
        renderEyeButton(guiGraphics, mouseX, mouseY);
        renderOctaveButtons(guiGraphics, mouseX, mouseY);

        if (selectedKeyCode != -1) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Integer[] rowKeys = KEY_MAPPING.get(row);
                    if (rowKeys != null && rowKeys.length > col && rowKeys[col] == selectedKeyCode) {
                        int x = gridStartX + col * (blockSize + blockSpacing);
                        int y = gridStartY + row * (blockSize + blockSpacing);

                        guiGraphics.fillGradient(
                                x - 2, y - 2,
                                x + blockSize + 2, y,
                                0xFFFFD700, 0xFFFFD700
                        );
                        guiGraphics.fillGradient(
                                x - 2, y - 2,
                                x, y + blockSize + 2,
                                0xFFFFD700, 0xFFFFD700
                        );
                        guiGraphics.fillGradient(
                                x + blockSize, y,
                                x + blockSize + 2, y + blockSize + 2,
                                0xFFFFD700, 0xFFFFD700
                        );
                        guiGraphics.fillGradient(
                                x, y + blockSize,
                                x + blockSize + 2, y + blockSize + 2,
                                0xFFFFD700, 0xFFFFD700
                        );
                        break;
                    }
                }
            }
        }

        String modeText = isSettingMode ?
                Component.translatable("screen.minefantasia.setting_mode").getString() :
                Component.translatable("screen.minefantasia.playing_mode").getString();
        int textColor = isSettingMode ? 0xFFFFA500 : 0xFFFFFF;
        guiGraphics.drawString(this.font, modeText, 10, 10, textColor, false);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private int darkenColor(int color) {
        int alpha = (color >> 24) & 0xFF;
        int red = (int) (((color >> 16) & 0xFF) * (float) 0.3);
        int green = (int) (((color >> 8) & 0xFF) * (float) 0.3);
        int blue = (int) ((color & 0xFF) * (float) 0.3);

        return (alpha << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
    }

    private void renderSettingsButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonColor = 0x4A808080;
        int borderColor = 0xFFFFFFFF;

        if (isMouseOverSettingsButton(mouseX, mouseY)) {
            buttonColor = 0x4AFFA500;
            borderColor = 0xFFFFFF00;
        }

        if (isSettingMode) {
            buttonColor = 0x4A32CD32;
        }

        renderButtonBackground(guiGraphics, buttonColor, borderColor, settingsButtonX, settingsButtonY);

        String gearSymbol = "⚙";
        int textX = settingsButtonX + settingsButtonSize / 2 - 3;
        int textY = settingsButtonY + settingsButtonSize / 2 - 4;
        guiGraphics.drawString(this.font, gearSymbol, textX, textY, 0xFF000000, false);
    }

    private void renderEyeButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonColor = 0x4A808080;
        int borderColor = 0xFFFFFFFF;

        if (isMouseOverEyeButton(mouseX, mouseY)) {
            buttonColor = 0x4AFFA500;
            borderColor = 0xFFFFFF00;
        }

        renderButtonBackground(guiGraphics, buttonColor, borderColor, eyeButtonX, eyeButtonY);

        String eyeSymbol = "👁";
        int textX = eyeButtonX + eyeButtonSize / 2 - 4;
        int textY = eyeButtonY + eyeButtonSize / 2 - 4;
        guiGraphics.drawString(this.font, eyeSymbol, textX, textY, 0xFF000000, false);
    }

    private void renderButtonBackground(GuiGraphics guiGraphics, int buttonColor, int borderColor, int eyeButtonX, int eyeButtonY) {
        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + 20, eyeButtonY + 20,
                buttonColor, buttonColor
        );

        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + 20, eyeButtonY + 1,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY,
                eyeButtonX + 1, eyeButtonY + 20,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX + 20 - 1, eyeButtonY,
                eyeButtonX + 20, eyeButtonY + 20,
                borderColor, borderColor
        );
        guiGraphics.fillGradient(
                eyeButtonX, eyeButtonY + 20 - 1,
                eyeButtonX + 20, eyeButtonY + 20,
                borderColor, borderColor
        );
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

    private boolean isMouseOverSettingsButton(double mouseX, double mouseY) {
        return mouseX >= settingsButtonX && mouseX <= settingsButtonX + settingsButtonSize &&
                mouseY >= settingsButtonY && mouseY <= settingsButtonY + settingsButtonSize;
    }

    private boolean isMouseOverEyeButton(double mouseX, double mouseY) {
        return mouseX >= eyeButtonX && mouseX <= eyeButtonX + eyeButtonSize &&
                mouseY >= eyeButtonY && mouseY <= eyeButtonY + eyeButtonSize;
    }

    private boolean isMouseOverUpButton(double mouseX, double mouseY) {
        return mouseX >= upButtonX && mouseX <= upButtonX + octaveButtonSize &&
                mouseY >= upButtonY && mouseY <= upButtonY + octaveButtonSize;
    }

    private boolean isMouseOverDownButton(double mouseX, double mouseY) {
        return mouseX >= downButtonX && mouseX <= downButtonX + octaveButtonSize &&
                mouseY >= downButtonY && mouseY <= downButtonY + octaveButtonSize;
    }

    private boolean isMouseOverBlock(double mouseX, double mouseY, int row, int col) {
        int x = gridStartX + col * (blockSize + blockSpacing);
        int y = gridStartY + row * (blockSize + blockSpacing);
        return mouseX >= x && mouseX <= x + blockSize &&
                mouseY >= y && mouseY <= y + blockSize;
    }

    private void handleBlockClick(int row, int col) {
        if (row < KEY_MAPPING.size()) {
            Integer[] rowKeys = KEY_MAPPING.get(row);
            if (rowKeys != null && col < rowKeys.length) {
                int keyCode = rowKeys[col];

                if (isSettingMode) {
                    selectedKeyCode = keyCode;
                    noteInputBox.setValue("");
                    noteInputBox.setFocused(false);
                } else {
                    pressedKeys.put(keyCode, System.currentTimeMillis());
                    MFInstrumentKeyHandler.playNoteForKey(keyCode);
                }
            }
        }
    }

    private void saveNoteMapping() {
        if (selectedKeyCode != -1) {
            String newNote = noteInputBox.getValue().trim();
            if (!newNote.isEmpty()) {
                MFKeyToNoteMapping.changeNoteMapping(selectedKeyCode, newNote);
            }
        }
    }
}
