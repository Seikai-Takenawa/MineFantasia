package com.takenawa.minefantasia.instrument;

import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;

import java.util.ArrayList;
import java.util.List;

public final class MFPianoInstrument implements MFInstruments {
    private static final String instrumentId = "piano";

    @Override
    public String getInstrumentId() {
        return instrumentId;
    }

    @Override
    public int getMinOctave() {return 3;}

    @Override
    public int getMaxOctave() {return 7;}

    @Override
    public void registerNotes() {
        for (int octave = getMinOctave(); octave <= getMaxOctave(); octave++) {
            for (String note : basicNoteNames()) {
                String soundName = octave + note;
                MFInstrumentNoteSoundsRegistry.registerInstrumentNoteSounds(instrumentId, soundName);
            }
        }
    }

    @Override
    public List<String> allNoteNames() {
        List<String> noteNames = new ArrayList<>();

        for (int octave = getMinOctave(); octave <= getMaxOctave(); octave++) {
            for (String note : basicNoteNames()) {
                String noteName = octave + note;
                String recordPathName = instrumentId + "/" + noteName;
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
