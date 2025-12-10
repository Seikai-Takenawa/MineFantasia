package com.takenawa.minefantasia.tab;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFCreativeModTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = MineFantasia.CREATIVE_MODE_TABS;

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TAB.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.minefantasia"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .displayItems((parameters, output) -> {
                //output.accept()
            }).build());

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    public static void registerModCreativeModeTabs() {
        MineFantasia.LOGGER.info("Registering Creative Mode Tab for MineFantasia...");
    }
}
