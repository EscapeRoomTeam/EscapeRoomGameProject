package tech.makers.demo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import tech.makers.demo.gui.IntroScreen;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class IntroScreenTest extends org.testfx.framework.junit5.ApplicationTest {

    private Stage primaryStage;
    private EscapeRoomGame game;
    private IntroScreen introScreen;

    @Start
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.game = mock(EscapeRoomGame.class);
        this.introScreen = new IntroScreen(primaryStage, game);
        introScreen.show();
    }

    @Test
    public void testTextAnimation() {
        WaitForAsyncUtils.sleep(6, TimeUnit.SECONDS); // Increase delay to ensure animation completes

        // Check if text is animated correctly
        Label speechBubble = lookup(".label").query();
        assertNotNull(speechBubble);
        String expectedText = "Welcome to the Escape Room Game! Use the arrow keys to move, and press Enter to interact with objects.";
        assertEquals(expectedText.trim(), speechBubble.getText().trim());
    }

    @Test
    public void testStartButton() {
        WaitForAsyncUtils.waitForFxEvents();

        // Check if start button exists and works
        Button startButton = lookup(".button").queryButton();
        verifyThat(startButton, hasText("Start Game"));

        // Simulate button click
        clickOn(startButton);
        verify(game, times(1)).startGame(primaryStage);
    }
}