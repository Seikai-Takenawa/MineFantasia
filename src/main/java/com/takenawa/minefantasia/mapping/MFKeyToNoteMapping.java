package com.takenawa.minefantasia.mapping;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MFKeyToNoteMapping {
    private static final Map<Integer, String> KEY_TO_NOTE_MAP = new HashMap<>();

    static {
        KEY_TO_NOTE_MAP.put(81, "6c");  // Q -> 6c
        KEY_TO_NOTE_MAP.put(87, "6d");  // W -> 6d
        KEY_TO_NOTE_MAP.put(69, "6e");  // E -> 6e
        KEY_TO_NOTE_MAP.put(82, "6f");  // R -> 6f
        KEY_TO_NOTE_MAP.put(84, "6g");  // T -> 6g
        KEY_TO_NOTE_MAP.put(89, "6a");  // Y -> 6a
        KEY_TO_NOTE_MAP.put(85, "6b");  // U -> 6b

        KEY_TO_NOTE_MAP.put(65, "5c");  // A -> 5c
        KEY_TO_NOTE_MAP.put(83, "5d");  // S -> 5d
        KEY_TO_NOTE_MAP.put(68, "5e");  // D -> 5e
        KEY_TO_NOTE_MAP.put(70, "5f");  // F -> 5f
        KEY_TO_NOTE_MAP.put(71, "5g");  // G -> 5g
        KEY_TO_NOTE_MAP.put(72, "5a");  // H -> 5a
        KEY_TO_NOTE_MAP.put(74, "5b");  // J -> 5b

        KEY_TO_NOTE_MAP.put(90, "4c");  // Z -> 4c
        KEY_TO_NOTE_MAP.put(88, "4d");  // X -> 4d
        KEY_TO_NOTE_MAP.put(67, "4e");  // C -> 4e
        KEY_TO_NOTE_MAP.put(86, "4f");  // V -> 4f
        KEY_TO_NOTE_MAP.put(66, "4g");  // B -> 4g
        KEY_TO_NOTE_MAP.put(78, "4a");  // N -> 4a
        KEY_TO_NOTE_MAP.put(77, "4b");  // M -> 4b
    }

    public static String getNoteForKey(int keyCode, boolean isUpper) {
        return KEY_TO_NOTE_MAP.get(keyCode);
    }

    public static String getNoteForKey(int keyCode) {
        return getNoteForKey(keyCode, false);
    }

    public static boolean isPlayableKey(int keyCode) {
        return KEY_TO_NOTE_MAP.containsKey(keyCode);
    }

    public static ResourceLocation getSoundLocation(String instrumentId, String note) {
        return ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, instrumentId + "/" + note);
    }

}
