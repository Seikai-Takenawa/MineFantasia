package com.takenawa.minefantasia.event;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.block.MFBlocksRegistry;
import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import net.minecraft.core.BlockPos;
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

        BlockState state = level.getBlockState(pos);

        if (state.is(MFBlocksRegistry.PIANO.get())) {
            MFInstrumentClientHandler.startPlaying("piano");
            MFInstrumentClientHandler.setIsPlayingPiano(true);
        }
    }
}
