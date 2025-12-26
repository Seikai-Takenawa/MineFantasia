package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.instrument.*;
import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.List;

public class MFInstrumentNoteSoundsDefinitionProvider extends SoundDefinitionsProvider {
    public MFInstrumentNoteSoundsDefinitionProvider(PackOutput output) {
        super(output, MineFantasia.MODID);
    }

    @Override
    public void registerSounds() {
        registerInstrumentNoteSounds(new MFHarpInstrument());
        registerInstrumentNoteSounds(new MFKalimbaInstrument());
        registerInstrumentNoteSounds(new MFPianoInstrument());
        registerInstrumentNoteSounds(new MFMiddleAgeSynthInstrument());
    }

    private void registerInstrumentNoteSounds(MFInstruments instrument) {
        List<String> noteRegisterNames = instrument.getNoteNames();

        for (String noteRegisterName : noteRegisterNames) {
            ResourceLocation soundFileLocation = MFInstrumentNoteSoundsRegistry.getRegisterEvent(noteRegisterName).get().location();

            this.add(noteRegisterName,
                    definition()
                            .with(sound(soundFileLocation))
                            .subtitle("key.minefantasia." + noteRegisterName)
            );

            MineFantasia.LOGGER.debug("Registered sound definition: {}", noteRegisterName);
        }
        MineFantasia.LOGGER.debug("Registered {} {} sound definitions", noteRegisterNames.size(), instrument.getInstrumentId());
    }
}
