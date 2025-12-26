package com.takenawa.minefantasia.geo.entity.render;

import com.takenawa.minefantasia.geo.entity.player.MFPlayerEntity;
import com.takenawa.minefantasia.geo.entity.model.MFPlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MFPlayerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<MFPlayerEntity, R> {
    public MFPlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new MFPlayerModel());
        this.shadowRadius = 0.5F;
    }
}
