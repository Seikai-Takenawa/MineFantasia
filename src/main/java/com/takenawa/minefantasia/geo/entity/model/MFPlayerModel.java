package com.takenawa.minefantasia.geo.entity.model;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.geo.entity.player.MFPlayerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MFPlayerModel extends DefaultedEntityGeoModel<MFPlayerEntity> {
    private final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "basic_customized_player");
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "textures/entity/basic_customized_player.png");
    private final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "basic_customized_player");

    public MFPlayerModel() {
        super(ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID,"basic_customized_player"));
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return this.MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return this.TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MFPlayerEntity animatable) {
        return this.ANIMATION;
    }
}
