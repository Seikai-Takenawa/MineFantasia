package com.takenawa.minefantasia.network;

import com.takenawa.minefantasia.MineFantasia;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class MFNetworkHelper {
    public static void sendNoteToServer(String instrumentId, String key) {
        MFNotePlayingPayload payload = new MFNotePlayingPayload(instrumentId, key);
        ClientPacketDistributor.sendToServer(payload);
        MineFantasia.LOGGER.debug("Successfully send note playing data to server.");
    }
}
