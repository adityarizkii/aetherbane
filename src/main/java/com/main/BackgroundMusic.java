package com.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundMusic {

    private static Clip clip;
    private static Thread musicThread;
    private static boolean isMusicRunning = false;

    public static void playBGM(String musicFilePath) {
        if (isMusicRunning) {
            stopMusic();
        }

        musicThread = new Thread(() -> {
            try {
                File musicFile = new File(musicFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                isMusicRunning = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        });
        
        musicThread.start();
    }

    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();  
        }
        isMusicRunning = false;
    }

    public static boolean isMusicPlaying() {
        return clip != null && clip.isRunning();
    }
}
