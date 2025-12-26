package com.takenawa.minefantasia.item;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.handler.MFInstrumentClientHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MFInstrumentItem extends Item {
    private final String instrumentId;

    public MFInstrumentItem(Properties properties, String instrumentId) {
        super(properties.stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, instrumentId))));
        this.instrumentId = instrumentId;
    }

    @Override
    public @NotNull InteractionResult use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide()) {
            MFInstrumentClientHandler.startPlaying(instrumentId);
        }
        return InteractionResult.SUCCESS;
    }
}
