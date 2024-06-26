package tech.makers.demo;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Alert;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.ComputerInteraction2;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.USBInteraction;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class USBInteractionTest {

    @Mock
    private Alert mockAlert;

    @Mock
    private ComputerInteraction2 mockComputerInteraction2;

    private USBInteraction interaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interaction = new USBInteraction(0, 0, "sprites/safe22.gif", mockComputerInteraction2);
    }

    @Test
    public void testConstructor_initializesProperties() {
        assertFalse(interaction.hasUSB);
    }

    @Test
    public void testInteract_noUSB_picksUpUSBAndUpdatesComputerInteraction() {
        interaction.interact();
        assertTrue(interaction.hasUSB);
        verify(interaction.computerInteraction2).setHasUSB(true); // Verify interaction with mock

        verify(interaction).showAlert("You picked up the USB drive.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("You picked up the USB drive.");
    }

    @Test
    public void testInteract_alreadyHasUSB_showsAlert() {
        interaction.hasUSB = true;
        Platform.runLater(() -> interaction.interact());
        assertFalse(interaction.computerInteraction2.hasUSB); // ComputerInteraction shouldn't be modified
        verify(interaction, times(0)).computerInteraction2.setHasUSB(anyBoolean()); // No interaction with mock

        verify(interaction).showAlert("You already have the USB drive.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("You already have the USB drive.");
    }

    @Test
    public void testShowAlert_createsAlertWithCorrectProperties() {
        interaction.showAlert("Test message");
        Alert capturedAlert = verify(mockAlert); // Capture the mocked Alert object
        assertEquals("Information", capturedAlert.getTitle());
        assertEquals(null, capturedAlert.getHeaderText());
        assertEquals("Test message", capturedAlert.getContentText());
    }
}

