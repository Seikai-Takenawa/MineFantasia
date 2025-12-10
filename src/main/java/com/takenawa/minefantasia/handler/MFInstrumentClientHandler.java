package com.takenawa.minefantasia.handler;

import com.mojang.blaze3d.platform.Window;
import com.takenawa.minefantasia.MineFantasia;
import com.takenawa.minefantasia.item.MFInstrumentItem;
import com.takenawa.minefantasia.screen.MFInstrumentScreen;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@EventBusSubscriber(modid = MineFantasia.MODID)
public class MFInstrumentClientHandler {
    private static boolean isPlaying = false;
    private static String currentInstrumentId = null;
    private static CameraType previousCameraType = null;

    public static boolean isPlayingInstrument() {
        return isPlaying;
    }

    public static String getCurrentInstrumentId() {
        return currentInstrumentId;
    }

    public static void startPlaying(String instrumentId) {
        if (isPlaying) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        isPlaying = true;
        currentInstrumentId = instrumentId;
        previousCameraType = mc.options.getCameraType();

        mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
        clearAllSounds();

        mc.options.hideGui = true;

        Window window = mc.getWindow();
        window.setAllowCursorChanges(false);

        Minecraft.getInstance().setScreen(new MFInstrumentScreen(Component.translatable("screen.minefantasia.harp")));
    }

    public static void stopPlaying() {
        if (!isPlaying) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        isPlaying = false;
        currentInstrumentId = null;

        if (previousCameraType != null) {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
        }
        resumeBackgroundMusic();

        mc.options.hideGui = false;

        Window window = mc.getWindow();
        window.setAllowCursorChanges(true);

        mc.player.setYRot(mc.player.getYRot());
        mc.player.setXRot(mc.player.getXRot());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) {
            if (isPlaying) {
                stopPlaying();
            }
            return;
        }

        if (isPlaying) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (!(mainHandItem.getItem() instanceof MFInstrumentItem)) {
                stopPlaying();
            }
        }
    }

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (isPlaying) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                event.setYaw(mc.player.getYRot());
                event.setPitch(mc.player.getXRot());
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (isPlaying) {
            if (sound != null && sound.getSource() == SoundSource.MUSIC) {
                pauseBackgroundMusic(sound);
            }
        }
    }

    private static void pauseBackgroundMusic(SoundInstance sound) {
        Minecraft mc = Minecraft.getInstance();
        mc.getSoundManager().stop(sound);
    }

    private static void resumeBackgroundMusic() {
        Minecraft mc = Minecraft.getInstance();
        mc.getSoundManager().resume();
    }

    private static void clearAllSounds() {
        Minecraft mc = Minecraft.getInstance();
        mc.getSoundManager().stop();
    }
}
