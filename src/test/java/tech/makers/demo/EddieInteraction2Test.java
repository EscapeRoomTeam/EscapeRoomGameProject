package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.image.PixelReader;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javafx.scene.control.Alert;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.EddieInteraction2;


import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class EddieInteraction2Test extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        stage.show();
    }

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private Image mockSpriteSheet;

    private EddieInteraction2 interaction;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interaction = new EddieInteraction2(0, 0, "/sprites/Eddie_idle_anim.png");
    }



    @Test
    public void testConstructor_initializesProperties() {
        String imagePath = "/sprites/Eddie_idle_anim.png";

        interaction = new EddieInteraction2(100, 50, imagePath);

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
        verify(mockSpriteSheet, times(6)).getPixelReader();
    }

    @Test
    public void testRender_drawsCorrectFrameBasedOnDirection() {
        interaction.direction = "up";
        Image mockImage = mock(Image.class);
        interaction.idleUpFrames = new Image[]{mockImage};

        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext).drawImage(mockImage, 0, 0, 25, 50);

        interaction.direction = "down";
        interaction.render(mockGraphicsContext);

        verify(mockGraphicsContext, times(2)).drawImage(any(Image.class), eq(0), eq(0), eq(25), eq(50));
    }

    @Test
    public void testUpdate_updatesCurrentFrame() {
        long currentTime = System.currentTimeMillis();

        interaction.lastFrameTime = currentTime;
        interaction.update();

        assertEquals(0, interaction.currentFrame);
        assertEquals(currentTime, interaction.lastFrameTime);

        interaction.lastFrameTime = currentTime - EddieInteraction2.FRAME_DURATION - 1;
        interaction.update();

        assertEquals(1, interaction.currentFrame);
        assertTrue(interaction.lastFrameTime == currentTime);
    }

    @Test
    public void testInteract_showsCorrectAlert() throws InterruptedException {
        String expectedSafeCode = "1234";
        Platform.runLater(() -> {
            interaction.interact();
        });
        Thread.sleep(1000);
    }




    @Test
    public void testGetSafeCode_returnsCorrectCode() {
        assertEquals(interaction.getSafeCode(), interaction.safeCode);
    }
}

