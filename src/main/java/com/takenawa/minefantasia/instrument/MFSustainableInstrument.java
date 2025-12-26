package com.takenawa.minefantasia.instrument;

import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class MFSustainableInstrument implements MFInstruments {
    private final String instrumentId;
    private final int minOctave;
    private final int maxOctave;

    public MFSustainableInstrument(String instrumentId,  int minOctave, int maxOctave) {
        this.instrumentId = instrumentId;
        this.minOctave = minOctave;
        this.maxOctave = maxOctave;
    }

    @Override
    public int getMinOctave() {
        return minOctave;
    }

    @Override
    public int getMaxOctave() {return maxOctave;}

    @Override
    public String getInstrumentId() {
        return this.instrumentId;
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
                String recordPathName = getInstrumentId() + "/" + noteName;
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
                MFInstrumentNoteSoundsRegistry.registerInstrumentNoteSounds(getInstrumentId(), soundName);

            }
        }
    }
}
