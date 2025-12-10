package com.takenawa.minefantasia.sound;

import java.util.List;

public interface MFInstruments {
    String getInstrumentId();
    String getBasicProperty();
    List<String> getNoteNames();
    void registerNotes();

    default int getMinOctave() {
        return 4;
    }

    default int getMaxOctave() {
        return 6;
    }

    default String[] basicNoteNames() {
        return new String[]{"c", "uc", "d", "ud", "e", "f", "uf", "g", "ug", "a", "ua", "b"};
    }

    default List<String> allNoteNames() {
        return List.of(new String[]{});
    }

}
