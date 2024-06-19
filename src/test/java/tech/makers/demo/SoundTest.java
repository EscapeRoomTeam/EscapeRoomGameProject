package tech.makers.demo;

import org.junit.Test;
import static org.junit.Assert.*;

import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundTest {

    @Test
    public void testSetFile() {
        Sound sound = new Sound();
        sound.setFile(0);
        assertNotNull(sound.clip);
    }

    @Test
    public void testPlay() {
        Sound sound = new Sound();
        sound.setFile(0);
        sound.play();
        try {
            Thread.sleep(1000); // 1 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(sound.clip.isRunning());
    }

    @Test
    public void testLoop() {
        Sound sound = new Sound();
        sound.setFile(0);
        sound.loop();

        // Add a delay to allow time for the sound to start looping
        try {
            Thread.sleep(1000); // 1 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(sound.clip.isRunning());
    }

    @Test
    public void testStop() {
        Sound sound = new Sound();
        sound.setFile(0);
        sound.play();
        sound.stop();
        assertFalse(sound.clip.isRunning());
    }

}
