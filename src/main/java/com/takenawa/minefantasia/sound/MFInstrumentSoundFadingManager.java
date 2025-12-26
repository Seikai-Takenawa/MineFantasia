package com.takenawa.minefantasia.sound;

import com.takenawa.minefantasia.MineFantasia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;

public class MFInstrumentSoundFadingManager {
    public static class MFFadingSoundInstance extends AbstractTickableSoundInstance {
        private boolean shouldFadeOut = false;
        private int fadeTicks = 0;
        private int fadeDuration = 10;

        public MFFadingSoundInstance(SoundEvent soundEvent) {
            super(soundEvent, SoundSource.PLAYERS, RandomSource.create());
            this.volume = 1.0f;
            this.pitch = 1.0f;
            this.relative = false;
        }

        public void startFadeOut() {
            this.shouldFadeOut = true;
            this.fadeTicks = 0;
        }

        public void stopping() {
            this.shouldFadeOut = true;
            this.fadeTicks = fadeDuration;
            this.volume = 0.0f;
        }

        @Override
        public void tick() {
            if (shouldFadeOut && fadeTicks < fadeDuration) {
                fadeTicks++;
                this.volume = 1.0f - ((float) fadeTicks / fadeDuration);
            } else if (fadeTicks >= fadeDuration) {
                this.stop();
            }
        }

        @Override
        public boolean isStopped() {
            return super.isStopped() || (shouldFadeOut && fadeTicks >= fadeDuration);
        }

        public boolean isFadingOut() {
            return shouldFadeOut;
        }

        public boolean isPlaying() {
            return !isStopped() && volume > 0.01f;
        }

        public void changeDuration(int fadeDuration) {
            this.fadeDuration = fadeDuration;
        }
    }

    private static final Map<Integer, MFFadingSoundInstance> activeSounds = new HashMap<>();

    public static void playSoundWithFade(int keyCode, SoundEvent soundEvent) {
        if (activeSounds.containsKey(keyCode)) {
            MFFadingSoundInstance existing = activeSounds.get(keyCode);
            if (!existing.isStopped()) {
                existing.stopping();
            }
        }

        MFFadingSoundInstance instance = new MFFadingSoundInstance(soundEvent);
        activeSounds.put(keyCode, instance);
        Minecraft.getInstance().getSoundManager().play(instance);
        MineFantasia.LOGGER.debug("now playing sound instance with id:{}", keyCode);
    }

    public static void startFadeOut(int keyCode, int fadeDuration) {
        if (activeSounds.containsKey(keyCode)) {
            MFFadingSoundInstance instance = activeSounds.get(keyCode);
            if (instance.isPlaying() && !instance.isFadingOut()) {
                instance.changeDuration(fadeDuration);
                instance.startFadeOut();
                MineFantasia.LOGGER.debug("start fading sound instance with id:{}", keyCode);
            }
        }
    }

    public static void cleanup() {
        activeSounds.entrySet().removeIf(entry -> entry.getValue().isStopped());
    }
}
