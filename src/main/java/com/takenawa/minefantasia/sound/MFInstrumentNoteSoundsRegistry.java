package com.takenawa.minefantasia.sound;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.instrument.MFHarpInstrument;
import com.takenawa.minefantasia.instrument.MFInstruments;
import com.takenawa.minefantasia.instrument.MFKalimbaInstrument;
import com.takenawa.minefantasia.instrument.MFPianoInstrument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MFInstrumentNoteSoundsRegistry {
    private static final DeferredRegister<SoundEvent> SOUND = MineFantasia.SOUNDS;

    private static final Map<String, Supplier<SoundEvent>> NOTE_SOUND_REGISTRY = new HashMap<>();

    private static final List<MFInstruments> INSTRUMENT_REGISTRIES = new ArrayList<>();

    public static void registerInstrument(MFInstruments instrument) {
        INSTRUMENT_REGISTRIES.add(instrument);
    }

    static {
        registerInstrument(new MFHarpInstrument());
        registerInstrument(new MFKalimbaInstrument());
        registerInstrument(new MFPianoInstrument());
    }

    public static void registerAllNoteSounds() {
        for (MFInstruments instrument : INSTRUMENT_REGISTRIES) {
            instrument.registerNotes();
        }
    }

    public static void registerInstrumentNoteSounds(String instrumentId, String noteName) {
        String register_name = instrumentId + "/" + noteName;

        if (NOTE_SOUND_REGISTRY.containsKey(register_name)) {
            NOTE_SOUND_REGISTRY.get(register_name);
            return;
        }

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, register_name);
        Supplier<SoundEvent> event = SOUND.register(register_name, () ->
                SoundEvent.createVariableRangeEvent(location)
        );

        NOTE_SOUND_REGISTRY.put(register_name, event);
        MineFantasia.LOGGER.info("Registered sound event {}", register_name);
    }

    public static Supplier<SoundEvent> getRegisterEvent(String name) {
        return NOTE_SOUND_REGISTRY.get(name);
    }

    public static void registerModSounds() {
        MineFantasia.LOGGER.info("Registering Sounds for MineFantasia...");
        registerAllNoteSounds();
    }
}
