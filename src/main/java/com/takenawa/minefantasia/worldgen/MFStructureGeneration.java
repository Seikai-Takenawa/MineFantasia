package com.takenawa.minefantasia.worldgen;

import com.mojang.datafixers.util.Pair;
import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MFStructureGeneration {
    public static final ResourceKey<Structure> CONCERT_HALL = ResourceKey.create(
            Registries.STRUCTURE,
            ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "concert_hall")
    );

    public static final ResourceKey<StructureSet> CONCERT_HALL_SET = ResourceKey.create(
            Registries.STRUCTURE_SET,
            ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "concert_hall")
    );

    public static final ResourceKey<StructureTemplatePool> CONCERT_HALL_START_POOL = ResourceKey.create(
            Registries.TEMPLATE_POOL,
            ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "concert_hall")
    );

    public static void bootstrapStructures(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolRegistry = context.lookup(Registries.TEMPLATE_POOL);

        Structure concertHall = new JigsawStructure(
                new Structure.StructureSettings(
                        biomeRegistry.getOrThrow(MFBiomeTags.HAS_CONCERT_HALL),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_BOX
                ),
                poolRegistry.getOrThrow(CONCERT_HALL_START_POOL),
                1,
                ConstantHeight.ZERO,
                false,
                Heightmap.Types.WORLD_SURFACE_WG
        );

        context.register(CONCERT_HALL, concertHall);
    }

    public static void bootstrapStructureSets(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structureRegistry = context.lookup(Registries.STRUCTURE);

        StructureSet concertHallSet = new StructureSet(
                structureRegistry.getOrThrow(CONCERT_HALL),
                new RandomSpreadStructurePlacement(
                        32,
                        12,
                        RandomSpreadType.LINEAR,
                        0
                )
        );
        context.register(CONCERT_HALL_SET, concertHallSet);
    }

    public static void bootstrapTemplatePools(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePoolRegistry = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorListRegistry = context.lookup(Registries.PROCESSOR_LIST);

        Holder<StructureTemplatePool> emptyPool = templatePoolRegistry.getOrThrow(
                ResourceKey.create(Registries.TEMPLATE_POOL,
                        ResourceLocation.fromNamespaceAndPath("minecraft", "empty"))
        );

        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getOrThrow(
                ResourceKey.create(Registries.PROCESSOR_LIST,
                        ResourceLocation.fromNamespaceAndPath("minecraft", "empty"))
        );

        Function<StructureTemplatePool.Projection, SinglePoolElement> elementFactory =
                StructurePoolElement.single(
                        ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "concert_hall").toString(),
                        emptyProcessorList,
                        LiquidSettings.IGNORE_WATERLOGGING
                );

        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> entryFactories =
                List.of(Pair.of(elementFactory,1));

        StructureTemplatePool concertHallPool = new StructureTemplatePool(
                emptyPool,
                entryFactories,
                StructureTemplatePool.Projection.RIGID
        );

        context.register(CONCERT_HALL_START_POOL, concertHallPool);
    }
}
