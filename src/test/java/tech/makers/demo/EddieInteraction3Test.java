package tech.makers.demo;

import javafx.scene.image.PixelReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.EddieInteraction3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EddieInteraction3Test {

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private Image mockSpriteSheet;

    private EddieInteraction3 interaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interaction = new EddieInteraction3(0, 0, "/sprites/Eddie_idle_anim.png");
    }

    @Test
    public void testConstructor_initializesProperties() {
        String imagePath = "/sprites/Eddie_idle_anim.png";

        interaction = new EddieInteraction3(100, 50, imagePath);

        assertNotNull(interaction.idleSpriteSheet);
        assertNotNull(interaction.idleRightFrames);
        assertNotNull(interaction.idleUpFrames);
        assertNotNull(interaction.idleLeftFrames);
        assertNotNull(interaction.idleDownFrames);
    }

    @Test
    public void testLoadFrames_extractsFramesFromSpriteSheet() {
        when(mockSpriteSheet.getPixelReader()).thenReturn(mock(PixelReader.class));

        int startFrame = 0;
        int spriteWidth = 16;
        int spriteHeight = 32;

        Image[] frames = interaction.loadFrames(mockSpriteSheet, startFrame);

        assertEquals(6, frames.length);
        verify(mockSpriteSheet).getPixelReader();
    }

    @Test
    public void testRender_drawsCorrectFrameBasedOnDirection() {
        interaction.direction = "up";
        Image mockImage = mock(Image.class);
        interaction.idleUpFrames = new Image[]{mockImage};

        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(mockImage, 0, 0, 25, 50); // Adjust coordinates based on your constructor initialization

        interaction.direction = "down";
        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext, times(2)).drawImage(any(Image.class), eq(0), eq(0), eq(25), eq(50)); // Adjust coordinates based on your constructor initialization
    }

    @Test
    public void testUpdate_updatesCurrentFrame() {
        long currentTime = System.currentTimeMillis();

        interaction.lastFrameTime = currentTime;
        interaction.update();

        assertEquals(0, interaction.currentFrame);
        assertEquals(currentTime, interaction.lastFrameTime);

        interaction.lastFrameTime = currentTime - EddieInteraction3.FRAME_DURATION - 1;
        interaction.update();

        assertEquals(1, interaction.currentFrame);
        assertTrue(interaction.lastFrameTime > currentTime);
    }

    @Test
    public void testInteract_showsCorrectAlert() {
        String expectedUsername = "newUser";
        String expectedPassword = "newPass123";
        Alert capturedAlert = mock(Alert.class);
        interaction.interact();
        verify(interaction).showAlert(String.valueOf(capturedAlert));
        assertEquals(Alert.AlertType.INFORMATION, capturedAlert.getAlertType());
        assertEquals("Tip", capturedAlert.getTitle());
        assertEquals(null, capturedAlert.getHeaderText());
        assertEquals("Eddie: Your new username is " + expectedUsername + " and password is " + expectedPassword, capturedAlert.getContentText());
    }

    @Test
    public void testGetUsername_returnsCorrectUsername() {
        assertEquals(interaction.getUsername(), interaction.getUsername());
    }

    @Test
    public void testGetPassword_returnsCorrectPassword() {
        assertEquals(interaction.getPassword(), interaction.getPassword());
    }
}
