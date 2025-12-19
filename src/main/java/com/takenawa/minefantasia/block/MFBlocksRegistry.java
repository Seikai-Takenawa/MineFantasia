package com.takenawa.minefantasia.block;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFBlocksRegistry {
    private static final DeferredRegister.Blocks BLOCK = MineFantasia.BLOCKS;

    public static final DeferredBlock<Block> PIANO = BLOCK.register("piano",
            MFPianoBlock::new);

    public static final DeferredBlock<Block> PCB_WHITE = BLOCK.register("pcb_white",
            MFPureColorBackgroundBlockWhite::new);

    public static final DeferredBlock<Block> PCB_BLACK = BLOCK.register("pcb_black",
            MFPureColorBackgroundBlockBlack::new);

    public static final DeferredBlock<Block> PCB_GREEN = BLOCK.register("pcb_green",
            MFPureColorBackgroundBlockGreen::new);

    public static final DeferredBlock<Block> PCB_RED = BLOCK.register("pcb_red",
            MFPureColorBackgroundBlockRed::new);

    public static final DeferredBlock<Block> PCB_BLUE = BLOCK.register("pcb_blue",
            MFPureColorBackgroundBlockBlue::new);

    public static void registerModBlocks() {
        MineFantasia.LOGGER.info("Registering Blocks for MineFantasia...");
    }
}
