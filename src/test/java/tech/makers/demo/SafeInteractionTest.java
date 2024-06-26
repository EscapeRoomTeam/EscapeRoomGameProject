package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.ComputerInteraction2;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.EddieInteraction2;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.SafeInteraction;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SafeInteractionTest {


    @Mock
    private EddieInteraction2 mockEddieInteraction2;

    @Mock
    private ComputerInteraction2 mockComputerInteraction2;

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private TextInputDialog mockDialog;

    @Mock
    private Alert mockAlert;

    @Mock
    private Optional<String> mockOptionalString;

    @Mock
    private Sound mockSound;

    private SafeInteraction interaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockSound = new Sound(); // Use a real instance for mockSound
        interaction = new SafeInteraction(100, 50, "/sprites/safe2.gif", mockEddieInteraction2, mockComputerInteraction2);
        interaction.sound = mockSound; // Inject mockSound into interaction
        interaction.setDialog(mockDialog);
        interaction.setAlert(mockAlert);
    }

    @Test
    public void testConstructor_initializesProperties() {
        assertFalse(interaction.isOpen);
    }

    @Test
    public void testRender_drawsImageAtCorrectLocation() {
        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(any(), eq(100.0), eq(50.0), eq(48.0), eq(96.0));
    }

    @Test
    public void testInteract_safeClosed_correctCode_opensSafeAndUpdatesComputerInteraction() {
        when(mockEddieInteraction2.getSafeCode()).thenReturn("1234");
        when(mockDialog.showAndWait()).thenReturn(mockOptionalString);
        when(mockOptionalString.isPresent()).thenReturn(true);
        when(mockOptionalString.get()).thenReturn("1234");
        Platform.runLater(() -> interaction.interact());
        verify(mockSound).setFile(22);
        verify(mockSound).setVolume(-17.0f);
        verify(mockSound).play();
        verify(mockComputerInteraction2).setHasUSB(true);
        assertTrue(interaction.isOpen);
        verify(interaction).showAlert("Safe opened! You found the USB drive.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("Safe opened! You found the USB drive.");
    }

    @Test
    public void testInteract_safeClosed_incorrectCode_showsAlertAndPlaysSound() {
        when(mockEddieInteraction2.getSafeCode()).thenReturn("1234");
        when(mockDialog.showAndWait()).thenReturn(mockOptionalString);
        when(mockOptionalString.isPresent()).thenReturn(true);
        when(mockOptionalString.get()).thenReturn("5678");
        Platform.runLater(() -> interaction.interact());
        verify(mockSound).setFile(15);
        verify(mockSound).setVolume(-10.0f);
        verify(mockSound).play();
        assertFalse(interaction.isOpen);
        verify(mockComputerInteraction2, times(0)).setHasUSB(anyBoolean());
        verify(interaction).showAlert("Incorrect code. Try again.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("Incorrect code. Try again.");
    }

    @Test
    public void testInteract_safeClosed_noInputFromDialog_showsAlert() {
        when(mockEddieInteraction2.getSafeCode()).thenReturn("1234");
        when(mockDialog.showAndWait()).thenReturn(mockOptionalString);
        when(mockOptionalString.isPresent()).thenReturn(false);
        Platform.runLater(() -> interaction.interact());
        verify(mockSound, times(0)).setFile(anyInt());
        verify(mockSound, times(0)).setVolume(anyFloat());
        verify(mockSound, times(0)).play();
        assertFalse(interaction.isOpen);
        verify(mockComputerInteraction2, times(0)).setHasUSB(anyBoolean());
        verify(interaction).showAlert("You didn't enter a code.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("You didn't enter a code.");
    }

    @Test
    public void testInteract_safeOpen_showsAlert() {
        interaction.isOpen = true;
        Platform.runLater(() -> interaction.interact());
        verify(mockDialog, times(0)).showAndWait();
        verify(mockSound, times(0)).setFile(anyInt());
        verify(mockSound, times(0)).setVolume(anyFloat());
        verify(mockSound, times(0)).play();
        verify(mockComputerInteraction2, times(0)).setHasUSB(anyBoolean());
        verify(interaction).showAlert("The safe is already open.");
        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("The safe is already open.");
    }

    @Test
    public void testShowAlert_createsAlertWithCorrectProperties() {
        Platform.runLater(() -> interaction.showAlert("Test message"));

        verify(mockAlert).setTitle("Information");
        verify(mockAlert).setHeaderText(null);
        verify(mockAlert).setContentText("Test message");
    }
}



