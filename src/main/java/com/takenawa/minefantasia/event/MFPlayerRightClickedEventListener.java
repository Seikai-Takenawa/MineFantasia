package com.takenawa.minefantasia.event;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.block.MFBlocksRegistry;
import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MineFantasia.MODID)
public class MFPlayerRightClickedEventListener {
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemInHand = player.getItemInHand(hand);

        BlockState state = level.getBlockState(pos);

        if (state.is(MFBlocksRegistry.PIANO.get())) {
            MFInstrumentClientHandler.startPlaying("piano");
            MFInstrumentClientHandler.setIsPlayingPiano(true);
        } else if (state.is(MFBlocksRegistry.PCB_WHITE.get()) && itemInHand.isEmpty()) {
            level.setBlock(pos, MFBlocksRegistry.PCB_BLACK.get().defaultBlockState(), 3);
        } else if (state.is(MFBlocksRegistry.PCB_BLACK.get()) && itemInHand.isEmpty()) {
            level.setBlock(pos, MFBlocksRegistry.PCB_GREEN.get().defaultBlockState(), 3);
        } else if (state.is(MFBlocksRegistry.PCB_GREEN.get()) && itemInHand.isEmpty()) {
            level.setBlock(pos, MFBlocksRegistry.PCB_RED.get().defaultBlockState(), 3);
        }else if (state.is(MFBlocksRegistry.PCB_RED.get()) && itemInHand.isEmpty()) {
            level.setBlock(pos, MFBlocksRegistry.PCB_BLUE.get().defaultBlockState(), 3);
        }else if (state.is(MFBlocksRegistry.PCB_BLUE.get()) && itemInHand.isEmpty()) {
            level.setBlock(pos, MFBlocksRegistry.PCB_WHITE.get().defaultBlockState(), 3);
        }
    }
}
