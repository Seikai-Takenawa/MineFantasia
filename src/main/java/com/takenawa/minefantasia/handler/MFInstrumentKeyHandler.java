package com.takenawa.minefantasia.handler;

import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.mapping.MFKeyToNoteMapping;
import com.takenawa.minefantasia.sound.MFSoundsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
/*
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;
*/
@EventBusSubscriber(modid = MineFantasia.MODID, value = Dist.CLIENT)
public class MFInstrumentKeyHandler {
    /*Previous logics on key-note mapping, control and play.

    private static final Set<Integer> PRESSED_KEYS = new HashSet<>();
    private static final Set<Integer> PROCESSED_KEYS = new HashSet<>();
    private static long lastSoundTime = 0;
    private static final long SOUND_COOLDOWN = 1; // 50ms cooldown between sounds


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (MFInstrumentClientHandler.isPlayingInstrument()) {
            return;
        }

        int key = event.getKey();
        int action = event.getAction();

        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
            MFInstrumentClientHandler.stopPlaying();
            return;
        }

        if (MFKeyToNoteMapping.isPlayableKey(key)) {
            if (action == GLFW.GLFW_PRESS) {
                PRESSED_KEYS.add(key);
            } else if (action == GLFW.GLFW_RELEASE) {
                PRESSED_KEYS.remove(key);
                PROCESSED_KEYS.remove(key);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (MFInstrumentClientHandler.isPlayingInstrument()) {
            PRESSED_KEYS.clear();
            PROCESSED_KEYS.clear();
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSoundTime < SOUND_COOLDOWN) {
            return;
        }

        for (int key : PRESSED_KEYS) {
            if (!PROCESSED_KEYS.contains(key)) {
                playNoteForKey(key);
                PROCESSED_KEYS.add(key);
                lastSoundTime = currentTime;
                break;
            }
        }
    }
*/
    public static void playNoteForKey(int keyCode) {
        String note = MFKeyToNoteMapping.getNoteForKey(keyCode);
        if (note != null) {
            String instrumentId = MFInstrumentClientHandler.getCurrentInstrumentId();
            if (instrumentId != null) {
                String registerName = instrumentId + "/" + note;
                var soundEvent = MFSoundsRegistry.getRegisterEvent(registerName);

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
    }
}
