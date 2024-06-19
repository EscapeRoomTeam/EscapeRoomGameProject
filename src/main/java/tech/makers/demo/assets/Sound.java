package tech.makers.demo.assets;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public Clip clip;
    private final URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/tech/makers/demo/Sound/TempMainScore.wav");
        soundURL[1] = getClass().getResource("/tech/makers/demo/Sound/SE_DoorOpen.wav");
        soundURL[2] = getClass().getResource("/tech/makers/demo/Sound/SE_Computer.wav");

        // Log to verify URLs
        for (int i = 0; i < soundURL.length; i++) {
            if (soundURL[i] != null) {
                System.out.println("Loaded sound file: " + soundURL[i]);
            } else {
                System.out.println("Sound file not found at index: " + i);
            }
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                System.err.println("Sound URL is null for index: " + i);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Audio clip loaded successfully for index: " + i);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.err.println("Error loading sound file at index: " + i);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("General error loading sound file at index: " + i);
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        } else {
            System.err.println("Clip is null, cannot play sound.");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Clip is null, cannot loop sound.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.err.println("Clip is null, cannot stop sound.");
        }
    }
}