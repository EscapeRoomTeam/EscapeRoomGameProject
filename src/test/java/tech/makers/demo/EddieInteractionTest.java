package tech.makers.demo;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levelManagement.levels.LevelOne.interactions.ComputerInteraction;
import tech.makers.demo.levelManagement.levels.LevelOne.interactions.EddieInteraction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EddieInteractionTest {

    @Mock
    private ComputerInteraction mockComputerInteraction;

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private Image mockSpriteSheet;

    @Mock
    private PixelReader mockPixelReader;

    @Mock
    private WritableImage mockWritableImage1;

    @Mock
    private WritableImage mockWritableImage2;

    private EddieInteraction interaction;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interaction = new EddieInteraction(0, 0, "", null);
    }



    @Test
    public void testConstructor_initializesProperties() {
        String imagePath = "/sprites/Eddie_idle_anim.png";

        interaction = new EddieInteraction(100, 50, imagePath, mockComputerInteraction);

        assertEquals(mockComputerInteraction, interaction.computerInteraction);
        assertNotNull(interaction.idleSpriteSheet);
        assertNotNull(interaction.idleRightFrames);
        assertNotNull(interaction.idleUpFrames);
        assertNotNull(interaction.idleLeftFrames);
        assertNotNull(interaction.idleDownFrames);
    }

    @Test
    public void testRender_drawsCharacterAtCorrectPositionAndSize() {
        GraphicsContext mockGraphicsContext = mock(GraphicsContext.class);
        EddieInteraction interaction = new EddieInteraction(100, 50, "/sprites/Eddie_idle_anim.png", null);
        Image mockImage = mock(Image.class);
        interaction.idleUpFrames = new Image[]{mockImage};

        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(any(Image.class), eq(100), eq(50), eq(25), eq(50)); // Adjust width and height based on your implementation
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
    public void testUpdate_updatesFrameForAnimation() {
        EddieInteraction interaction = new EddieInteraction(0, 0, "", null);
        long startTime = System.currentTimeMillis();

        interaction.update();

        assertEquals(0, interaction.currentFrame);
        assertTrue(System.currentTimeMillis() - startTime >= 0); // Update happens immediately or with a very small delay

        interaction.update(); // Update again after some time

        assertEquals(1, interaction.currentFrame);
        assertTrue(System.currentTimeMillis() - startTime >= EddieInteraction.FRAME_DURATION); // Update happens after FRAME_DURATION milliseconds
    }


    @Test
    public void testInteract_showsCorrectAlertForRouterStatus() {
        String wifiPassword = "secret_password";
        Alert capturedAlertOn = mock(Alert.class);
        Alert capturedAlertOff = mock(Alert.class);

        ComputerInteraction mockComputerInteraction = mock(ComputerInteraction.class);
        when(mockComputerInteraction.isRouterOn()).thenReturn(true, false); // Simulate both on and off scenarios
        when(mockComputerInteraction.getWifiPassword()).thenReturn(wifiPassword);

        EddieInteraction interaction = new EddieInteraction(0, 0, "/sprites/Eddie_idle_anim.png", mockComputerInteraction);


        interaction.interact();
        assertEquals(Alert.AlertType.INFORMATION, capturedAlertOn. getAlertType());
        assertEquals("Eddie: Here's the Wi-Fi password you need: " + wifiPassword, capturedAlertOn.getContentText());


        interaction.interact();
        assertEquals(Alert.AlertType.INFORMATION, capturedAlertOff.getAlertType());


    }

}


