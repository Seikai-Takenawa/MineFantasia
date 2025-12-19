package com.takenawa.minefantasia.block;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MFPureColorBackgroundBlock extends Block {
    public MFPureColorBackgroundBlock(String colorId) {
        super(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "pcb_" + colorId)))
                .sound(SoundType.STONE)
        );
    }
}
