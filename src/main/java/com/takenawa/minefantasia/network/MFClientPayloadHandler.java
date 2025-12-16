package com.takenawa.minefantasia.network;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MFClientPayloadHandler {
    public static void handleInstrumentNote(final MFNotePlayingPayload data, final IPayloadContext context) {
        MineFantasia.LOGGER.debug("Received note playing data from server");

        context.enqueueWork(() -> playNoteFromNetwork(data.instrumentId(), data.key())).exceptionally(e -> {
            context.disconnect(Component.translatable("minefantasia.networking.failed", e.getMessage()));
            return null;
        });
    }

    public static void playNoteFromNetwork(String instrumentId, String key) {
        String registerName = instrumentId + "/" + key;
        var soundEvent = MFInstrumentNoteSoundsRegistry.getRegisterEvent(registerName);

        if (soundEvent != null) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player != null) {
                player.level().playSound(
                        player,
                        player.getX(), player.getY(), player.getZ(),
                        soundEvent.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }
    }
}
