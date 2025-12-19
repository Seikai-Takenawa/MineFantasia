package com.takenawa.minefantasia.instrument;

import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;

import java.util.ArrayList;
import java.util.List;

public final class MFKalimbaInstrument implements MFInstruments {
    private static final String INSTRUMENT_ID = "kalimba";
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
    public List<String> getNoteNames() {
        return allNoteNames();
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
    public void registerNotes() {
        for (int octave = getMinOctave(); octave <= getMaxOctave(); octave++) {
            for (String note : basicNoteNames()) {
                String soundName = octave + note;
                MFInstrumentNoteSoundsRegistry.registerInstrumentNoteSounds(INSTRUMENT_ID, soundName);
            }
        }
    }
}
