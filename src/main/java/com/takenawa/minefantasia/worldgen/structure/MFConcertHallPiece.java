package com.takenawa.minefantasia.worldgen.structure;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.jetbrains.annotations.NotNull;

public class MFConcertHallPiece extends TemplateStructurePiece {
    public MFConcertHallPiece(
            StructurePieceSerializationContext context,
            CompoundTag tag
    ) {
        super(
                MFStructurePieceTypes.CONCERT_HALL.get(),
                tag,
                context.structureTemplateManager(),
                loc -> makeSettings(Rotation.valueOf(String.valueOf(tag.getString("Rot"))))
        );
    }

    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(Mirror.NONE)
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
    }

    @Override
    protected void handleDataMarker(
            @NotNull String function,
            @NotNull BlockPos pos,
            @NotNull ServerLevelAccessor level,
            @NotNull RandomSource random,
            @NotNull BoundingBox box
    ) {
        MineFantasia.LOGGER.debug("Handling data marker: {} at {}", function, pos);
    }

    @Override
    protected void addAdditionalSaveData(
            @NotNull StructurePieceSerializationContext context,
            @NotNull CompoundTag tag
    ) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rot", this.placeSettings.getRotation().name());
    }
}
