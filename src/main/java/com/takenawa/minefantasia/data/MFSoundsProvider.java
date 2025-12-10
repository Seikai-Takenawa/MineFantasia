package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.sound.MFHarpInstrument;
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
        registerHarpNoteSounds();
    }

    private void registerHarpNoteSounds() {
        MFHarpInstrument harp = new MFHarpInstrument();
        List<String> noteRegisterNames = harp.getNoteNames();

        for (String noteRegisterName : noteRegisterNames) {
            ResourceLocation soundFileLocation = MFSoundsRegistry.getRegisterEvent(noteRegisterName).get().location();

            this.add(noteRegisterName,
                    definition()
                            .with(sound(soundFileLocation))
                            .subtitle("key.minefantasia." + noteRegisterName)
            );

            MineFantasia.LOGGER.info("Registered sound definition: {}", noteRegisterName);
        }
        MineFantasia.LOGGER.info("Registered {} harp sound definitions", noteRegisterNames.size());
    }
}
