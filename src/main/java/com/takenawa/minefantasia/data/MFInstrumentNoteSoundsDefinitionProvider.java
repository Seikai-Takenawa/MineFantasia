package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.instrument.MFHarpInstrument;
import com.takenawa.minefantasia.instrument.MFInstruments;
import com.takenawa.minefantasia.instrument.MFKalimbaInstrument;
import com.takenawa.minefantasia.instrument.MFPianoInstrument;
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

            MineFantasia.LOGGER.info("Registered sound definition: {}", noteRegisterName);
        }
        MineFantasia.LOGGER.info("Registered {} {} sound definitions", noteRegisterNames.size(), instrument.getInstrumentId());
    }
}
