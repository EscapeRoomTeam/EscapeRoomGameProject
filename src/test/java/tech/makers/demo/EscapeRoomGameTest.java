package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.assets.Sound;

import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.LevelManager;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;
import tech.makers.demo.screens.OptionsScreen;

import java.util.List;


import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(ApplicationExtension.class)
public class EscapeRoomGameTest  {

    @InjectMocks
    private EscapeRoomGame app;

    @Mock
    private Sound soundMock;

    @Mock
    private LevelManager levelManagerMock;

    @Mock
    private OptionsScreen optionsScreenMock;

    @Mock
    private Level currentLevelMock;

    @Mock
    private Player playerMock;

    @Mock
    private List<Interaction> interactionsMock;

    @Mock
    private Door doorMock;

    @Mock
    private Eddie helperCharacterMock;

    @Mock
    private List<Puzzle> puzzlesMock;

    @Start
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        app = new EscapeRoomGame();
        app.sound = soundMock;
        app.levelManager = levelManagerMock;
        app.optionsScreen = optionsScreenMock;

        when(levelManagerMock.getCurrentLevel()).thenReturn(currentLevelMock);
        when(currentLevelMock.getPlayer()).thenReturn(playerMock);
        when(currentLevelMock.getDoor()).thenReturn(doorMock);
        when(currentLevelMock.getHelperCharacter()).thenReturn(helperCharacterMock);
        when(currentLevelMock.getPuzzles()).thenReturn(puzzlesMock);
        when(currentLevelMock.getInteractions()).thenReturn(interactionsMock);
        when(playerMock.getX()).thenReturn((double) 0);

        app.start(stage);
    }


    @Test
    public void testOptionsMenu() {
        Platform.runLater(() -> {
            Stage primaryStage = new Stage();
            app.startGame(primaryStage);
            Scene scene = primaryStage.getScene();
            app.toggleOptionsMenu();
            WaitForAsyncUtils.waitForFxEvents();
            assertTrue(app.isOptionsMenuVisible);
        });
    }

    private void interact(Object o) {
    }

    @Test
    public void testStartGame(){
        Platform.runLater(() -> {
            Stage primaryStage = new Stage();
            app.startGame(primaryStage);
            Scene scene = primaryStage.getScene();
            assertNotNull(scene);
        });
    }

    @Test
    public void testPlayerMovement(FxRobot robot) {
        try {
            FxToolkit.setupStage(stage -> {
                app.startGame(stage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.RIGHT);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(0.0, playerMock.x, 0.1);
    }

    @Test
    public void testInteractionWithObjects(FxRobot robot) {
        try {
            FxToolkit.setupStage(stage -> {
                app.startGame(stage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Puzzle puzzleMock = mock(Puzzle.class);
        Interaction interactionMock = mock(Interaction.class);
        when(doorMock.isInRange()).thenReturn(true);
        when(helperCharacterMock.isInRange(anyInt(), anyInt())).thenReturn(true);
        puzzleMock.inRange = true;
        when(interactionMock.isInRange()).thenReturn(true);
        robot.press(KeyCode.E);
        robot.release(KeyCode.E);
        WaitForAsyncUtils.waitForFxEvents();


    }

    @Test
    public void testLevelCompletion(FxRobot robot) {
        try {
            FxToolkit.setupStage(stage -> {
                app.startGame(stage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            app.completeLevel();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(robot.lookup(".button").queryAs(Button.class).isVisible());
    }

    @Test
    public void testMusicControls() {
        try {
            FxToolkit.setupStage(stage -> {
                app.startGame(stage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Play music
        app.playMusic(1);
        verify(soundMock, times(1)).setFile(1);
        verify(soundMock, times(3)).play();
        verify(soundMock, times(2)).loop();

        // Stop music
        app.stopMusic(1);
        verify(soundMock, times(1)).stop();
    }

    @Test
    public void testToggleOptionsMenu(FxRobot robot) {
        Platform.runLater(() -> {
            Stage primaryStage = new Stage();
            app.startGame(primaryStage);
            Scene scene = primaryStage.getScene();
            app.toggleOptionsMenu();
            WaitForAsyncUtils.waitForFxEvents();
            assertTrue(app.isOptionsMenuVisible);
        });
    }
}







