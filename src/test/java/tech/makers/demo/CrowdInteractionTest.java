package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.CrowdInteraction;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CrowdInteractionTest {
    @Mock
    private Image mockImage;

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private Alert mockAlert;

    private CrowdInteraction interaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interaction = new CrowdInteraction(0, 0, ""); // Image path not relevant for these tests
    }

    @Test
    public void testConstructor_initializesProperties() {
        double expectedWidth = 250;
        double expectedHeight = 150;

        assertEquals(expectedWidth, interaction.width);
        assertEquals(expectedHeight, interaction.height);
    }

    @Test
    public void testInteract_showsAlert() {
        Platform.runLater(() -> interaction.interact());
        verify(interaction).showAlert(); // Verify method call
    }

    @Test
    public void testShowAlert_createsAlertWithCorrectProperties() {
        interaction.showAlert();
        Alert capturedAlert = verify(mockAlert);
        assertEquals("Information", capturedAlert.getTitle());
        assertEquals(null, capturedAlert.getHeaderText());
        assertEquals("I'm excited to see what the students present!", capturedAlert.getContentText());
    }

    @Test
    public void testRender_drawsImageAtCorrectLocation() {
        double expectedX = 100;
        double expectedY = 50;

        interaction.x = expectedX;
        interaction.y = expectedY;

        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(any(Image.class), eq(expectedX), eq(expectedY), eq(interaction.width), eq(interaction.height));
    }

    @Test
    public void testCollidesWith_detectsCollisionCorrectly() {
        double playerX = 120;
        double playerY = 70;
        double playerWidth = 30;
        double playerHeight = 40;

        // Player within interaction bounds
        assertTrue(interaction.collidesWith(playerX, playerY, playerWidth, playerHeight));

        // Player outside interaction bounds (left)
        playerX = 50;
        assertFalse(interaction.collidesWith(playerX, playerY, playerWidth, playerHeight));

        // Player outside interaction bounds (top)
        playerX = 120;
        playerY = 20;
        assertFalse(interaction.collidesWith(playerX, playerY, playerWidth, playerHeight));

        // Player outside interaction bounds (right)
        playerX = 200;
        playerY = 70;
        assertFalse(interaction.collidesWith(playerX, playerY, playerWidth, playerHeight));

        // Player outside interaction bounds (bottom)
        playerX = 120;
        playerY = 130;
        assertFalse(interaction.collidesWith(playerX, playerY, playerWidth, playerHeight));
    }
}

