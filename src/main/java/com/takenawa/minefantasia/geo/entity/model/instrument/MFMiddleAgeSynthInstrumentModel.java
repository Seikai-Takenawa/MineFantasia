package com.takenawa.minefantasia.geo.entity.model.instrument;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.geo.entity.instrument.MFMiddleAgeSynthInstrumentEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MFMiddleAgeSynthInstrumentModel extends DefaultedEntityGeoModel<MFMiddleAgeSynthInstrumentEntity> {
    private final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "middle_age_synth");
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "textures/entity/middle_age_synth.png");
    private final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "middle_age_synth");

    public MFMiddleAgeSynthInstrumentModel() {
        super(ResourceLocation.fromNamespaceAndPath(MineFantasia.MODID, "middle_age_synth"));
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
    public ResourceLocation getAnimationResource(MFMiddleAgeSynthInstrumentEntity animatable) {
        return this.ANIMATION;
    }
}
