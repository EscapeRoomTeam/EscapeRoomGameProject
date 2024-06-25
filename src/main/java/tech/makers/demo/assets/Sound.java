package tech.makers.demo.assets;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public Clip clip;
    private final URL[] soundURL = new URL[30];
    private float volume = -10.0f;

    public Sound() {
        soundURL[0] = getClass().getResource("/tech/makers/demo/Sound/TempMainScore.wav");
        soundURL[1] = getClass().getResource("/tech/makers/demo/Sound/SE_DoorOpen.wav");
        soundURL[2] = getClass().getResource("/tech/makers/demo/Sound/SE_Computer.wav");
        soundURL[3] = getClass().getResource("/tech/makers/demo/Sound/SE_Footstep2.wav");
        soundURL[4] = getClass().getResource("/tech/makers/demo/Sound/SE_Footstep3.wav");
        soundURL[5] = getClass().getResource("/tech/makers/demo/Sound/SE_Footstep4.wav");
        soundURL[6] = getClass().getResource("/tech/makers/demo/Sound/SE_Footstep5.wav");
        soundURL[7] = getClass().getResource("/tech/makers/demo/Sound/Vox1.wav");
        soundURL[8] = getClass().getResource("/tech/makers/demo/Sound/Vox2.wav");
        soundURL[9] = getClass().getResource("/tech/makers/demo/Sound/Vox5.wav");
        soundURL[10] = getClass().getResource("/tech/makers/demo/Sound/Vox6.wav");
        soundURL[11] = getClass().getResource("/tech/makers/demo/Sound/Vox7.wav");
        soundURL[12] = getClass().getResource("/tech/makers/demo/Sound/Vox8.wav");
        soundURL[13] = getClass().getResource("/tech/makers/demo/Sound/SE_Correct.wav");
        soundURL[14] = getClass().getResource("/tech/makers/demo/Sound/SE_Correct2.wav");
        soundURL[15] = getClass().getResource("/tech/makers/demo/Sound/SE_Wrong.wav");
        soundURL[16] = getClass().getResource("/tech/makers/demo/Sound/AmbientMenuMusic.wav");
        soundURL[17] = getClass().getResource("/tech/makers/demo/Sound/SE_ClickBassy.wav");
        soundURL[18] = getClass().getResource("/tech/makers/demo/Sound/SE_ClickBright.wav");
        soundURL[19] = getClass().getResource("/tech/makers/demo/Sound/TEST_CLICK.wav");
        soundURL[20] = getClass().getResource("/tech/makers/demo/Sound/SE_RouterOn.wav");
        soundURL[21] = getClass().getResource("/tech/makers/demo/Sound/SE_Typing.wav");


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
            clip.setFramePosition(0);
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
    public void setVolume(float volume) {
        this.volume = volume;
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // Reduce volume by a number of decibels.
        }
    }
    public float getVolume() {
        return volume;
    }
}