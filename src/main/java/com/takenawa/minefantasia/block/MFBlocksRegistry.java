package com.takenawa.minefantasia.block;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFBlocksRegistry {
    private static final DeferredRegister.Blocks BLOCK = MineFantasia.BLOCKS;

    public static final DeferredBlock<Block> PIANO = BLOCK.register("piano",
            MFPianoBlock::new);

    public static void registerModBlocks() {
        MineFantasia.LOGGER.info("Registering Blocks for MineFantasia...");
    }
}
