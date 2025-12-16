package com.takenawa.minefantasia.network;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MFServerPayloadHandler {
    public static void handleInstrumentNote(final MFNotePlayingPayload data, final IPayloadContext context) {
        MineFantasia.LOGGER.debug("Received note playing data from client");

        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sender) {
                ServerLevel level = sender.level();
                Vec3 pos = sender.position();

                PacketDistributor.sendToPlayersNear(
                        level,
                        sender,
                        pos.x,
                        pos.y,
                        pos.z,
                        24,
                        data
                );
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("minefantasia.networking.failed", e.getMessage()));
            return null;
        });
    }
}
