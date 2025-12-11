package com.takenawa.minefantasia.mapping;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MFKeyToNoteMapping {
    private static final Map<Integer, String> KEY_TO_NOTE_MAP = new HashMap<>();

    static {
        KEY_TO_NOTE_MAP.put(81, "6c");  // Q
        KEY_TO_NOTE_MAP.put(87, "6d");  // W
        KEY_TO_NOTE_MAP.put(69, "6e");  // E
        KEY_TO_NOTE_MAP.put(82, "6f");  // R
        KEY_TO_NOTE_MAP.put(84, "6g");  // T
        KEY_TO_NOTE_MAP.put(89, "6a");  // Y
        KEY_TO_NOTE_MAP.put(85, "6b");  // U

        KEY_TO_NOTE_MAP.put(65, "5c");  // A
        KEY_TO_NOTE_MAP.put(83, "5d");  // S
        KEY_TO_NOTE_MAP.put(68, "5e");  // D
        KEY_TO_NOTE_MAP.put(70, "5f");  // F
        KEY_TO_NOTE_MAP.put(71, "5g");  // G
        KEY_TO_NOTE_MAP.put(72, "5a");  // H
        KEY_TO_NOTE_MAP.put(74, "5b");  // J

        KEY_TO_NOTE_MAP.put(90, "4c");  // Z
        KEY_TO_NOTE_MAP.put(88, "4d");  // X
        KEY_TO_NOTE_MAP.put(67, "4e");  // C
        KEY_TO_NOTE_MAP.put(86, "4f");  // V
        KEY_TO_NOTE_MAP.put(66, "4g");  // B
        KEY_TO_NOTE_MAP.put(78, "4a");  // N
        KEY_TO_NOTE_MAP.put(77, "4b");  // M
    }

    public static String getNoteForKey(int keyCode, boolean isUpper) {
        return KEY_TO_NOTE_MAP.get(keyCode);
    }

    public static String getNoteForKey(int keyCode) {
        return getNoteForKey(keyCode, false);
    }

    public static boolean isPlayableKey(int keyCode) {
        return !Objects.equals(KEY_TO_NOTE_MAP.get(keyCode), "");
    }

    public static ResourceLocation getSoundLocation(String instrumentId, String note) {
        return ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, instrumentId + "/" + note);
    }

    public static void changeNoteMapping(int keyCode, String note) {
        KEY_TO_NOTE_MAP.replace(keyCode, note);
    }
}
