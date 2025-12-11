package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.instrument.MFHarpInstrument;
import com.takenawa.minefantasia.instrument.MFInstruments;
import com.takenawa.minefantasia.instrument.MFKalimbaInstrument;
import com.takenawa.minefantasia.sound.MFSoundsRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.List;

public class MFSoundsProvider extends SoundDefinitionsProvider {
    public MFSoundsProvider(PackOutput output) {
        super(output, MineFantasia.MODID);
    }

    @Override
    public void registerSounds() {
        registerInstrumentNoteSounds(new MFHarpInstrument());
        registerInstrumentNoteSounds(new MFKalimbaInstrument());
    }

    private void registerInstrumentNoteSounds(MFInstruments instrument) {
        List<String> noteRegisterNames = instrument.getNoteNames();

        for (String noteRegisterName : noteRegisterNames) {
            ResourceLocation soundFileLocation = MFSoundsRegistry.getRegisterEvent(noteRegisterName).get().location();

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
