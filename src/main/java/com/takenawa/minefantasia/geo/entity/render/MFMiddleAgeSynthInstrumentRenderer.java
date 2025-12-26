package com.takenawa.minefantasia.geo.entity.render;

import com.takenawa.minefantasia.geo.entity.instrument.MFMiddleAgeSynthInstrumentEntity;
import com.takenawa.minefantasia.geo.entity.model.instrument.MFMiddleAgeSynthInstrumentModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MFMiddleAgeSynthInstrumentRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<MFMiddleAgeSynthInstrumentEntity, R> {
    public MFMiddleAgeSynthInstrumentRenderer(EntityRendererProvider.Context context) {
        super(context, new MFMiddleAgeSynthInstrumentModel());
    }
}
