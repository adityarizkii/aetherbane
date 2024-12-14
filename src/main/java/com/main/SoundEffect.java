package com.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {

    private static Clip clip;
    private static Thread soundThread;
    private static boolean isSoundRunning = false;

    public static void playSound(String soundFilePath) {
        if (isSoundRunning) {
            stopSound();
        }

        soundThread = new Thread(() -> {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                isSoundRunning = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        });

        soundThread.start();
    }

    public static void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (soundThread != null && soundThread.isAlive()) {
            soundThread.interrupt();
        }
        isSoundRunning = false;
    }

    public static boolean isSoundPlaying() {
        return clip != null && clip.isRunning();
    }
}
