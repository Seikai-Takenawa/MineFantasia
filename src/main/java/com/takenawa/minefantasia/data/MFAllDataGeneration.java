package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MineFantasia.MODID)
public class MFAllDataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(
                true,
                new MFSoundsProvider(output)
        );

        generator.addProvider(
                true,
                new MFBiomeTagsProvider(output, event.getLookupProvider())
        );

        generator.addProvider(
                true,
                new MFStructureDefineGeneration(output, event.getLookupProvider())
        );
    }
}
