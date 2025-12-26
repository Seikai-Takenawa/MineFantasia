package com.takenawa.minefantasia.geo.entity.instrument;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.manager.AnimatableManager;

public final class MFMiddleAgeSynthInstrumentEntity extends MFSynthInstrumentEntity {
    public MFMiddleAgeSynthInstrumentEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level, "middle_age_synth", 1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add();
    }
}
