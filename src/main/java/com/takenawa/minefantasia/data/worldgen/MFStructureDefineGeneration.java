package com.takenawa.minefantasia.data.worldgen;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.worldgen.MFStructureGeneration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MFStructureDefineGeneration extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.STRUCTURE, MFStructureGeneration::bootstrapStructures)
            .add(Registries.STRUCTURE_SET, MFStructureGeneration::bootstrapStructureSets)
            .add(Registries.TEMPLATE_POOL, MFStructureGeneration::bootstrapTemplatePools);

    public MFStructureDefineGeneration(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries, BUILDER, Set.of(MineFantasia.MODID));
    }
}
