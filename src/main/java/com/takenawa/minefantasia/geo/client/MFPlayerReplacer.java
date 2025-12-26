package com.takenawa.minefantasia.geo.client;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.geo.entity.MFGeoEntitiesRegistry;
import com.takenawa.minefantasia.geo.entity.player.MFPlayerEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = MineFantasia.MODID, value = Dist.CLIENT)
public class MFPlayerReplacer {
    private static final Map<UUID, MFPlayerEntity> playerReplacements = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;

        Player player = event.getEntity();
        UUID playerId = player.getUUID();

        if (!playerReplacements.containsKey(playerId)) {
            MFPlayerEntity mfPlayer = new MFPlayerEntity(
                    MFGeoEntitiesRegistry.MF_PLAYER.get(),
                    player.level()
            );
            mfPlayer.syncWithPlayer(player);
            playerReplacements.put(playerId, mfPlayer);
        } else {
            MFPlayerEntity mfPlayer = playerReplacements.get(playerId);
            if (mfPlayer != null && !mfPlayer.isRemoved()) {
                mfPlayer.syncWithPlayer(player);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        event.setCanceled(true);
    }
}
