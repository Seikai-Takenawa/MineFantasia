package com.takenawa.minefantasia.geo.entity.instrument;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class MFSynthInstrumentEntity extends Entity implements GeoEntity {
    private static final EntityDataAccessor<Boolean> DUMMY_DATA = SynchedEntityData.defineId(MFSynthInstrumentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions DEFAULT_DIMENSIONS = EntityDimensions.scalable(1.0f, 2.0f);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String instrumentId;
    private final int fadeDuration;

    public MFSynthInstrumentEntity(EntityType<? extends Entity> type, Level level, String instrumentId, int fadeDuration) {
        super(type, level);
        this.setNoGravity(true);
        this.refreshDimensions();
        this.instrumentId = instrumentId;
        this.fadeDuration = fadeDuration;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        builder.define(DUMMY_DATA, false);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return DEFAULT_DIMENSIONS;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick() {
        super.tick();

        this.setDeltaMovement(Vec3.ZERO);

        if (this.getDeltaMovement().lengthSqr() > 0) {
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith(Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return super.isPushable();
    }

    @Override
    public boolean hurtServer(@NotNull ServerLevel level, @NotNull DamageSource damageSource, float amount) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {}

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {}

    public String getInstrumentId() {
        return instrumentId;
    }

    public int getFadeDuration() {
        return fadeDuration;
    }
}
