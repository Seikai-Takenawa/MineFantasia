package com.takenawa.minefantasia.data;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.worldgen.MFBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MFBiomeTagsProvider extends BiomeTagsProvider {
    public MFBiomeTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, MineFantasia.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(MFBiomeTags.HAS_CONCERT_HALL)
                .addTag(BiomeTags.IS_FOREST)
                .add(Biomes.JUNGLE)
                .add(Biomes.SPARSE_JUNGLE)
                .addTag(BiomeTags.IS_TAIGA)
                .addTag(BiomeTags.IS_JUNGLE);
    }
}
