package com.takenawa.minefantasia.geo.entity;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.geo.entity.instrument.MFMiddleAgeSynthInstrumentEntity;
import com.takenawa.minefantasia.geo.entity.player.MFPlayerEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MFGeoEntitiesRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = MineFantasia.ENTITY_TYPES;

    public static final DeferredHolder<EntityType<?>, EntityType<MFPlayerEntity>> MF_PLAYER = ENTITY_TYPES.register("basic_customized_player",
            () -> EntityType.Builder.of(MFPlayerEntity::new, MobCategory.MISC)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "basic_customized_player")))
    );

    public static final DeferredHolder<EntityType<?>, EntityType<MFMiddleAgeSynthInstrumentEntity>> MF_MIDDLE_AGE_SYNTH = ENTITY_TYPES.register("middle_age_synth",
            () -> EntityType.Builder.of(MFMiddleAgeSynthInstrumentEntity::new, MobCategory.MISC)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "middle_age_synth")))
    );

    public static void registerModEntityTypes() {
        MineFantasia.LOGGER.info("Registering Entity Types for MineFantasia...");
    }
}
