package com.takenawa.minefantasia;

import com.takenawa.minefantasia.block.MFBlocksRegistry;
import com.takenawa.minefantasia.item.MFItemsRegistry;
import com.takenawa.minefantasia.network.MFNetworkHandler;
import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;
import com.takenawa.minefantasia.tab.MFCreativeModTab;
import com.takenawa.minefantasia.worldgen.structure.MFStructurePieceTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(MineFantasia.MODID)
public class MineFantasia {
    public static final String MODID = "minefantasia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<SoundEvent>  SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, MineFantasia.MODID);

    public MineFantasia(IEventBus modEventBus) {
        MFBlocksRegistry.registerModBlocks();
        BLOCKS.register(modEventBus);
        MFItemsRegistry.registerModItems();
        ITEMS.register(modEventBus);
        MFInstrumentNoteSoundsRegistry.registerModSounds();
        SOUNDS.register(modEventBus);
        MFCreativeModTab.registerModCreativeModeTabs();
        CREATIVE_MODE_TABS.register(modEventBus);
        MFStructurePieceTypes.registerModStructurePieceType();
        STRUCTURE_PIECE_TYPES.register(modEventBus);

        modEventBus.register(MFNetworkHandler.class);
    }
}
