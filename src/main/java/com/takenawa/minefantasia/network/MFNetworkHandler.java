package com.takenawa.minefantasia.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

public class MFNetworkHandler {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1")
                .executesOn(HandlerThread.NETWORK);

        registrar.playBidirectional(
                MFNotePlayingPayload.NOTE_PLAYING_DATA_TYPE,
                MFNotePlayingPayload.STREAM_CODEC,
                MFServerPayloadHandler::handleInstrumentNote
        );
    }

    @SubscribeEvent
    public static void registerClient(RegisterClientPayloadHandlersEvent event) {
        event.register(
                MFNotePlayingPayload.NOTE_PLAYING_DATA_TYPE,
                HandlerThread.NETWORK,
                MFClientPayloadHandler::handleInstrumentNote
        );
    }
}
