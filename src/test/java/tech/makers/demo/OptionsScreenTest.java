package tech.makers.demo;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.makers.demo.screens.OptionsScreen;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OptionsScreenTest {

    @Mock
    private Stage primaryStageMock;

    @Mock
    private EscapeRoomGame gameMock;

    private OptionsScreen optionsScreen;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gameMock.getMusicVolume()).thenReturn(0.5);
        when(gameMock.getSEVolume()).thenReturn(0.7);

        optionsScreen = new OptionsScreen(primaryStageMock, gameMock);
    }

    @Test
    public void testSceneInitialization() {
        Scene scene = optionsScreen.getScene();

        assertNotNull(scene);
        assertEquals(768, scene.getWidth());
        assertEquals(576, scene.getHeight());
    }

    @Test
    public void testMusicVolumeSliderChange() {
        Scene scene = optionsScreen.getScene();
        optionsScreen.musicVolumeSlider.setValue(0.8);
        verify(gameMock).setMusicVolume(0.8);
    }

    @Test
    public void testSEVolumeSliderChange() {
        Scene scene = optionsScreen.getScene();

        // Simulate interaction with sound effects volume slider
        optionsScreen.seVolumeSlider.setValue(0.3);

        // Verify that gameMock's setSEVolume method is called with expected value
        verify(gameMock).setSEVolume(0.3);
    }

    @Test
    public void testResumeGameButtonAction() {
        Scene scene = optionsScreen.getScene();

        // Simulate click on resume game button
        optionsScreen.resumeButton.getOnAction().handle(null);

        // Verify that gameMock's resumeGame method is called
        verify(gameMock).resumeGame();
    }

    @Test
    public void testExitButtonAction() {
        Scene scene = optionsScreen.getScene();

        // Simulate click on exit game button
        optionsScreen.exitButton.getOnAction().handle(null);

        // Verify that primaryStageMock's close method is called
        verify(primaryStageMock).close();
    }
}

