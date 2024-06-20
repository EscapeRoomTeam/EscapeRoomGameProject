package tech.makers.demo;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.LabeledMatchers;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.gui.LevelCompletionScreen;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(ApplicationExtension.class)
public class LevelCompletionScreenTest {

    private static Stage stage;
    private LevelCompletionScreen levelCompletionScreen;
    private EscapeRoomGame game;

    @BeforeEach
    void setUp(FxRobot robot) throws Exception {
        // Mock the EscapeRoomGame class
        game = mock(EscapeRoomGame.class);

        // Initialize the LevelCompletionScreen
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new LevelCompletionScreenTest.TestApp());

        robot.interact(() -> {
            try {
                stage = FxToolkit.registerPrimaryStage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            levelCompletionScreen = new LevelCompletionScreen(stage, game);
        });
    }

    @Test
    public void testInitialize(FxRobot robot) throws Exception {
        // Assert that the scene is not null
        assertNotNull(levelCompletionScreen.getScene());

        // Set the scene and show the stage
        robot.interact(() -> {
            stage.setScene(levelCompletionScreen.getScene());
            stage.show();
        });

        // Verify that the UI elements are present and correct
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Next Level"));

        // Click the "Next Level" button
        robot.clickOn(".button");

        // Verify that the game.startNextLevel() method was called
        verify(game).startNextLevel();
    }

    // Test application class for TestFX
    public static class TestApp extends javafx.application.Application {
        @Override
        public void start(Stage stage) {
            // This method is called on the JavaFX Application Thread.
            LevelCompletionScreenTest.stage = stage;
        }
    }
}