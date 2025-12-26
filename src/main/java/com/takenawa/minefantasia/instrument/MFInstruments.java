package com.takenawa.minefantasia.instrument;

import java.util.List;

public interface MFInstruments {
    String getInstrumentId();
    List<String> getNoteNames();
    List<String> allNoteNames();
    void registerNotes();

    default int getMinOctave() {
        return 4;
    }

    default int getMaxOctave() {
        return 6;
    }

    default String[] basicNoteNames() {
        return new String[]{"c", "sc", "d", "sd", "e", "f", "sf", "g", "sg", "a", "sa", "b"};
    }
}
