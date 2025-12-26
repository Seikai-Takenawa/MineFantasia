package com.takenawa.minefantasia.geo.entity.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MFPlayerEntity extends LivingEntity implements GeoEntity {
    private Player targetPlayer;
    private HumanoidArm mainArm = HumanoidArm.RIGHT;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MFPlayerEntity(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_SPEED, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.STEP_HEIGHT, 0.6F);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return this.mainArm;
    }

    public void setMainArm(HumanoidArm arm) {
        this.mainArm = arm;
    }

    public void syncWithPlayer(Player player) {
        this.targetPlayer = player;
        this.setPos(player.getX(), player.getY(), player.getZ());
        this.setYRot(player.getYRot());
        this.setXRot(player.getXRot());

        this.setMainArm(player.getMainArm());

        this.setHealth(player.getHealth());
        this.setDeltaMovement(player.getDeltaMovement());
        this.setPose(player.getPose());

        this.setItemInHand(InteractionHand.MAIN_HAND, player.getMainHandItem());
        this.setItemInHand(InteractionHand.OFF_HAND, player.getOffhandItem());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.targetPlayer != null && !this.targetPlayer.isRemoved()) {
            this.syncWithPlayer(this.targetPlayer);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("Basic/Movment", 5, this::basicMovingAnimController));
    }

    private PlayState basicMovingAnimController(AnimationTest<GeoAnimatable> animTest) {
        if (animTest.isMoving()) {
            if (MFPlayerEntity.this.isSprinting()) {
                return animTest.setAndContinue(DefaultAnimations.RUN);
            } else if (MFPlayerEntity.this.isSwimming()) {
                return animTest.setAndContinue(DefaultAnimations.SWIM);
            } else if (MFPlayerEntity.this.isJumping()) {
                return animTest.setAndContinue(DefaultAnimations.JUMP);
            } else if (MFPlayerEntity.this.isCrouching()) {
                return animTest.setAndContinue(DefaultAnimations.CRAWL);
            } else {
                return animTest.setAndContinue(DefaultAnimations.WALK);
            }
        }
        return animTest.setAndContinue(DefaultAnimations.IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
