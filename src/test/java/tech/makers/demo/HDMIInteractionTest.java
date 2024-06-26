package tech.makers.demo;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.CableInteraction;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.HDMIInteraction;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.MacbookInteraction;


import static org.mockito.Mockito.*;

public class HDMIInteractionTest {

    private HDMIInteraction hdmiInteraction;
    private MacbookInteraction mockMacbookInteraction;
    private CableInteraction mockCableInteraction;

    @BeforeEach
    public void setUp() {
        mockMacbookInteraction = mock(MacbookInteraction.class);
        hdmiInteraction = new HDMIInteraction(0, 0, "sprites/hdmi.png", mockMacbookInteraction, mockCableInteraction);
    }

    @Test
    public void testInteractWhenMacbookIsPluggedIn() {
        when(mockMacbookInteraction.hasMacbook()).thenReturn(true);
        hdmiInteraction.interact();
        verify(mockMacbookInteraction, times(1)).hasMacbook();
        verifyAlertShown("The MacBook is already plugged in.");
    }

    @Test
    public void testInteractWhenMacbookIsNotPresent() {
        when(mockMacbookInteraction.hasMacbook()).thenReturn(false);
        hdmiInteraction.interact();
        verify(mockMacbookInteraction, times(1)).hasMacbook();
        verifyAlertShown("You need to get the MacBook first.");
    }

    @Test
    public void testInteractWhenMacbookIsPluggedInFirstTime() {
        when(mockMacbookInteraction.hasMacbook()).thenReturn(true);
        hdmiInteraction.interact();
        verify(mockMacbookInteraction, times(1)).hasMacbook();
        verifyAlertShown("You plugged the MacBook into the screen.");
    }

    private void verifyAlertShown(String expectedMessage) {
        // Verifying the alert was shown with the expected message
        Mockito.verify(mockMacbookInteraction, times(1)).hasMacbook();
        Mockito.verify(mockMacbookInteraction, times(1)).showAlert(expectedMessage);
    }
}

