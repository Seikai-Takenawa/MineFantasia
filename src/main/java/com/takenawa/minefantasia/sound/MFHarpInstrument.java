package com.takenawa.minefantasia.sound;

import java.util.ArrayList;
import java.util.List;

public class MFHarpInstrument implements MFInstruments {
    private static final String INSTRUMENT_ID = "harp";
    private static final String BASIC_PROPERTY = "handhold";

    @Override
    public String getInstrumentId() {
        return INSTRUMENT_ID;
    }

    @Override
    public String getBasicProperty() {
        return BASIC_PROPERTY;
    }

    @Override
    public String[] basicNoteNames() {
        return new String[]{"c", "d", "e", "f", "g", "a", "b"};
    }

    @Override
    public void registerNotes() {
        for (int octave = getMinOctave(); octave <= getMaxOctave(); octave++) {
            for (String note : basicNoteNames()) {
                String soundName = octave + note;
                MFSoundsRegistry.registerInstrumentNoteSounds(INSTRUMENT_ID, soundName);
            }
        }
    }

    @Override
    public List<String> allNoteNames() {
        List<String> noteNames = new ArrayList<>();

        for (int octave = getMinOctave(); octave <= getMaxOctave(); octave++) {
            for (String note : basicNoteNames()) {
                String noteName = octave + note;
                String recordPathName = INSTRUMENT_ID + "/" + noteName;
                noteNames.add(recordPathName);
            }
        }
        return noteNames;
    }

    @Override
    public List<String> getNoteNames() {
        return allNoteNames();
    }
}
