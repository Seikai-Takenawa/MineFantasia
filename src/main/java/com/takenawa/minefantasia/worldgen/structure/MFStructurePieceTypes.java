package com.takenawa.minefantasia.worldgen.structure;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MFStructurePieceTypes {
    private static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPE = MineFantasia.STRUCTURE_PIECE_TYPES;

    public static final Supplier<StructurePieceType> CONCERT_HALL =
            STRUCTURE_PIECE_TYPE.register("concert_hall", () -> MFConcertHallPiece::new);

    public static void registerModStructurePieceType() {
        MineFantasia.LOGGER.info("Registering Structure Piece Type for MineFantasia...");
    }
}
