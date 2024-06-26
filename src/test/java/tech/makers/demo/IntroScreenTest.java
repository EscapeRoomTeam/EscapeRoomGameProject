package tech.makers.demo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import tech.makers.demo.screens.IntroScreen;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class IntroScreenTest  {



    private Stage primaryStage;
    private EscapeRoomGame game;
    private IntroScreen introScreen;

    @BeforeEach
    public void setUp() throws Exception {
        game = mock(EscapeRoomGame.class);
        primaryStage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(stage -> {
            primaryStage = stage;
            introScreen = new IntroScreen(primaryStage, game);
            introScreen.show();
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void testTextAnimation(FxRobot robot) {
        WaitForAsyncUtils.sleep(6, TimeUnit.SECONDS); // Increase delay to ensure animation completes

        // Check if text is animated correctly
        Label speechBubble = robot.lookup(".label").query();
        assertNotNull(speechBubble);
        String expectedText = "Welcome to the Escape Room Game! Use the arrow keys to move, and press Enter to interact with objects.";
        assertEquals(expectedText.trim(), speechBubble.getText().trim());
    }

    @Test
    public void testStartButton(FxRobot robot) {
        WaitForAsyncUtils.waitForFxEvents();

        // Check if start button exists and works
        Button startButton = robot.lookup(".button").queryButton();
        verifyThat(startButton, hasText("Start Game"));

        // Simulate button click
        robot.clickOn(startButton);
        verify(game, times(1)).startGame(primaryStage);
    }
}
