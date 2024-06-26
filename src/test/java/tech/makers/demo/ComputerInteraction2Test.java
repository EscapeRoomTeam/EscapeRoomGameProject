package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.ComputerInteraction2;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComputerInteraction2Test extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private Alert mockAlert;

    @Mock
    private Door mockDoor;

    private Sound mockSound; // Mock the Sound class

    private ComputerInteraction2 interaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockSound = Mockito.mock(Sound.class); // Create a mock Sound object
        interaction = new ComputerInteraction2(0, 0, "/sprites/Computer 2.png", mockDoor);
        interaction.sound = mockSound; // Inject the mock sound into the interaction object
    }

    @Test
    public void testConstructor_initializesProperties() {
        assertFalse(interaction.hasUSB);
        assertFalse(interaction.osReinstalled);
    }

    @Test
    public void testRender_drawsImageAtCorrectLocation() {
        double expectedX = 100;
        double expectedY = 50;

        interaction.x = expectedX;
        interaction.y = expectedY;

        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(any(Image.class), eq(expectedX), eq(expectedY), eq(96), eq(144));
    }

    @Test
    public void testSetHasUSB_updatesHasUSBFlag() {
        interaction.setHasUSB(true);

        assertTrue(interaction.hasUSB);
    }

    @Test
    public void testInteract_noUSB_playsSoundAndShowsAlert() {
        Platform.runLater(() -> interaction.interact());
        verify(mockSound).setFile(15);
        verify(mockSound).setVolume(-10.0f);
        verify(mockSound).play();

        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("You need to find the USB drive to reinstall the operating system.");
    }

    @Test
    public void testInteract_reinstallOS_playsSoundShowsAlertAndUnlocksDoor() {
        interaction.setHasUSB(true);
        Platform.runLater(() -> interaction.interact());
        verify(mockSound).setFile(2);
        verify(mockSound).setVolume(-25.0f);
        verify(mockSound).play();

        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("Operating system reinstalled! You can now do the coding challenge.");

        verify(mockDoor).unlock();
        assertTrue(interaction.osReinstalled);
    }

    @Test
    public void testInteract_alreadyReinstalled_showsAlert() {
        interaction.setHasUSB(true);
        interaction.osReinstalled = true;

        Platform.runLater(() -> interaction.interact());
        verify(mockSound, times(0)).setFile(anyInt()); // Sound not played
        verify(mockSound, times(0)).setVolume(anyFloat());
        verify(mockSound, times(0)).play();

        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("You have already reinstalled the operating system and completed the challenge.");

        verify(mockDoor, times(0)).unlock(); // Door not unlocked again
    }

    @Test
    public void testShowAlert_createsAlertWithCorrectProperties() {
        Platform.runLater(() -> interaction.showAlert("Test message"));

        Alert capturedAlert = verify(mockAlert); // Capture the mocked Alert object
        assertEquals("Information", capturedAlert.getTitle());
        assertEquals(null, capturedAlert.getHeaderText()); // Verify header text is null
        assertEquals("Test message", capturedAlert.getContentText());
    }

}