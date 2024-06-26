package tech.makers.demo;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import tech.makers.demo.screens.LevelCompletionScreen;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class LevelCompletionScreenTest  extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        LevelCompletionScreenTest.stage = stage;
    }


    private static Stage stage;
    private LevelCompletionScreen levelCompletionScreen;
    private EscapeRoomGame game;




    @BeforeEach
    void setUp() throws Exception {
        // Mock the EscapeRoomGame class
        game = mock(EscapeRoomGame.class);
        interact(() -> {
            try {
                stage = FxToolkit.registerPrimaryStage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            levelCompletionScreen = new LevelCompletionScreen(stage, game);
        });
    }

    @AfterEach
    void tearDown() throws Exception {
        TestApp.stopApp();
    }

    @Test
    public void testInitialize() throws Exception {
        // Assert that the scene is not null
        assertNotNull(levelCompletionScreen.getScene());

        // Set the scene and show the stage
        interact(() -> {
            stage.setScene(levelCompletionScreen.getScene());
            stage.show();
        });

        // Verify that the UI elements are present and correct
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Next Level"));

        // Click the "Next Level" button
        clickOn(".button");

        // Wait for all JavaFX events to be processed
        WaitForAsyncUtils.waitForFxEvents();

        // Verify that the game.startNextLevel() method was called
        verify(game).startNextLevel();
    }

    @Test
    public void testTextAnimation() {
        interact(() -> {
            stage.setScene(levelCompletionScreen.getScene());
            stage.show();
        });

        WaitForAsyncUtils.sleep(6, TimeUnit.SECONDS); // Increase delay to ensure animation completes

        // Check if text is animated correctly
        Label speechBubble = lookup(".label").query();
        assertNotNull(speechBubble);
        String expectedText = "Congratulations on completing the level! Click the button to proceed to the next level.";
        assertEquals(expectedText.trim(), speechBubble.getText().trim());
    }
}

