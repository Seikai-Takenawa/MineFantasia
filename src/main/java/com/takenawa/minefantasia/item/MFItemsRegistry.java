package com.takenawa.minefantasia.item;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.block.MFBlocksRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFItemsRegistry {
    private static final DeferredRegister.Items ITEM = MineFantasia.ITEMS;

    public static final DeferredItem<Item> HARP = ITEM.register("harp",
            MFHarpItem::new);

    public static final DeferredItem<Item> KALIMBA = ITEM.register("kalimba",
            MFKalimbaItem::new);

    public static final DeferredItem<BlockItem> PIANO = ITEM.registerSimpleBlockItem("piano",
            MFBlocksRegistry.PIANO);

    public static void registerModItems() {
        MineFantasia.LOGGER.info("Registering Items for MineFantasia...");
    }
}
