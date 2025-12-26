package com.takenawa.minefantasia.event;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.geo.entity.MFGeoEntitiesRegistry;
import com.takenawa.minefantasia.geo.entity.render.MFMiddleAgeSynthInstrumentRenderer;
import com.takenawa.minefantasia.geo.entity.render.MFPlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MineFantasia.MODID, value = Dist.CLIENT)
public class MFGeoEntityRendererRegistryEvent {
    @SubscribeEvent
    public static void registerGeoEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MFGeoEntitiesRegistry.MF_PLAYER.get(), MFPlayerRenderer::new);
        event.registerEntityRenderer(MFGeoEntitiesRegistry.MF_MIDDLE_AGE_SYNTH.get(), MFMiddleAgeSynthInstrumentRenderer::new);
    }
}
