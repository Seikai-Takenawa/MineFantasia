package com.takenawa.minefantasia.item;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFItemsRegistry {
    private static final DeferredRegister.Items ITEM = MineFantasia.ITEMS;

    public static final DeferredItem<Item> HARP = ITEM.register("harp",
            MFHarpItem::new);

    public static void registerModItems() {
        MineFantasia.LOGGER.info("Registering Items for MineFantasia...");
    }
}
