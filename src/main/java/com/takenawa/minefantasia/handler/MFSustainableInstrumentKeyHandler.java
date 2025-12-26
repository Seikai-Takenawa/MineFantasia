package com.takenawa.minefantasia.handler;

import com.takenawa.minefantasia.mapping.MFKeyToNoteMapping;
import com.takenawa.minefantasia.network.MFNetworkHelper;
import com.takenawa.minefantasia.sound.MFInstrumentNoteSoundsRegistry;
import com.takenawa.minefantasia.sound.MFInstrumentSoundFadingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class MFSustainableInstrumentKeyHandler {
    public static void playSustainNoteForKey(int keyCode) {
        String note = MFKeyToNoteMapping.getNoteForKey(keyCode);
        LocalPlayer player;
        if (note != null) {
            String instrumentId = MFInstrumentClientHandler.getCurrentInstrumentId();
            if (instrumentId != null) {
                String registerName = instrumentId + "/" + note;
                var soundEvent = MFInstrumentNoteSoundsRegistry.getRegisterEvent(registerName);

                if (soundEvent != null) {
                    Minecraft mc = Minecraft.getInstance();
                    player = mc.player;

                    if (player != null) {
                        MFInstrumentSoundFadingManager.playSoundWithFade(keyCode, soundEvent.get());
                    }

                    if (player != null && player.level().isClientSide()) {
                        MFNetworkHelper.sendNoteToServer(instrumentId, note);
                    }
                }
            }
        }
    }
}
