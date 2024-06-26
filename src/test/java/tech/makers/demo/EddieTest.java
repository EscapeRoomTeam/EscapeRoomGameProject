package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Eddie;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class EddieTest extends ApplicationTest {

    @Override
    public void start(javafx.stage.Stage stage) {
        // This method is required by ApplicationTest, but we don't need to set up a stage for these tests.
    }


    private Eddie eddie;
    @Mock
    private GraphicsContext mockGraphicsContext;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        eddie = new Eddie(100, 100, "/sprites/Eddie_idle_anim.png", "Hello, I'm Eddie!");
    }

    @Test
    public void testRender() {
        eddie.render(mockGraphicsContext);
        verify(mockGraphicsContext, times(1)).drawImage(any(Image.class), eq(100.0), eq(100.0), eq(25.0), eq(50.0));
    }

    @Test
    public void testUpdate() {
        long initialFrameTime = eddie.lastFrameTime;
        // Manually advancing time beyond FRAME_DURATION
        eddie.lastFrameTime = System.currentTimeMillis() - Eddie.FRAME_DURATION - 1;
        eddie.update();
        assert (eddie.currentFrame == 1); // Check if frame updated correctly
        // Restore original frame time for other tests
        eddie.lastFrameTime = initialFrameTime;
    }

    @Test
    public void testIsInRange() {
        boolean inRange = eddie.isInRange(90, 90);
        assert (inRange);
    }

    @Test
    public void testInteract() throws InterruptedException {
        Platform.runLater(() -> {
            eddie.interact();
        });
        Thread.sleep(1000);
    }

    @Test
    public void testLoadImage() {
        eddie.loadImage(mockGraphicsContext);
        verify(mockGraphicsContext, times(1)).drawImage(any(Image.class), eq(100.0), eq(100.0), eq(25.0), eq(50.0));
    }

    @Test
    public void testGetCurrentFrame() {
        assert (eddie.getCurrentFrame() == 0); // Initially, the current frame should be 0
    }
}





