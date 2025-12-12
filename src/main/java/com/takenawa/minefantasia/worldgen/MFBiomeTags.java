package com.takenawa.minefantasia.worldgen;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MFBiomeTags {
    public static final TagKey<Biome> HAS_CONCERT_HALL = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "can_place_structure/concert_hall")
    );
}
