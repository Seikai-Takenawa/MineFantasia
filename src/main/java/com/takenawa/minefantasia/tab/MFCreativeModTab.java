package com.takenawa.minefantasia.tab;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.item.MFItemsRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFCreativeModTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = MineFantasia.CREATIVE_MODE_TABS;

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TAB.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.minefantasia"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .displayItems((parameters, output) -> {
                output.accept(MFItemsRegistry.HARP);
                output.accept(MFItemsRegistry.KALIMBA);
            }).build());

    public static void registerModCreativeModeTabs() {
        MineFantasia.LOGGER.info("Registering Creative Mode Tab for MineFantasia...");
    }
}
