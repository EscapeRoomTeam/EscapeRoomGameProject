package tech.makers.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.screens.HomeScreen;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HomeScreenTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }
    private Stage mockStage;
    private EscapeRoomGame game;
    private Sound backgroundMusic;
    private Sound buttonSound;
    private HomeScreen homeScreen;



    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new javafx.application.Application() {
            @Override
            public void start(Stage primaryStage) {
                mockStage = mock(Stage.class);
                game = Mockito.mock(EscapeRoomGame.class);
                backgroundMusic = Mockito.mock(Sound.class);
                buttonSound = Mockito.mock(Sound.class);

                homeScreen = new HomeScreen(mockStage, game);

                primaryStage.setScene(homeScreen.getScene());
                primaryStage.show();
            }
        });
    }

    @Test
    public void testInitializeSceneNotNull() {
        Scene scene = homeScreen.getScene();
        assertNotNull(scene, "Scene should not be null");
    }

    @Test
    public void testStartButtonAction() {
        reset(buttonSound, backgroundMusic);
        Button startButton = lookup(".button").queryButton();
        clickOn(startButton);
        verify(buttonSound, times(1)).play();
        verify(backgroundMusic, times(1)).stop();
        verifyNoMoreInteractions(buttonSound, backgroundMusic);
    }

    @Test
    public void testHelpButtonAction() {
        reset(buttonSound);
        Button helpButton = lookup(".button").nth(1).queryButton();
        clickOn(helpButton);
        // Add verification for showing help, if possible
    }

    @Test
    public void testExitButtonAction() {
        reset(buttonSound, mockStage);
        Button exitButton = lookup(".button").nth(2).queryButton();
        clickOn(exitButton);
        verify(buttonSound, times(1)).play();
        verify(mockStage, times(1)).close();
    }

    @Test
    public void testStopMusic() {
        homeScreen.stopMusic();
        verify(backgroundMusic, times(1)).stop();
    }

    @Test
    public void testStopAudio() {
        homeScreen.stopAudio();
        verify(buttonSound, times(1)).stop();
    }
}





