package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;
import tech.makers.demo.assets.Eddie;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelTest {

    private Player mockPlayer;
    private Puzzle mockPuzzle;
    private Door mockDoor;
    private Eddie mockHelperCharacter;
    private GraphicsContext mockGc;
    private Level level;

    @BeforeAll // starting the Test App before all tests
    public static void initJfx() {
        TestApp.launchApp();
    }
    @BeforeEach
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockPuzzle = mock(Puzzle.class);
        mockDoor = mock(Door.class);
        mockGc = mock(GraphicsContext.class);
        level = new Level(mockPlayer, mockPuzzle, mockDoor, mockHelperCharacter);
    }

    @Test
    public void testConstructor() {
        assertEquals(mockPlayer, level.getPlayer());
        assertEquals(mockPuzzle, level.getPuzzle());
        assertEquals(mockDoor, level.getDoor());
    }

    @Test
    public void testRender() {
        level.render(mockGc);

        verify(mockPlayer, times(1)).render(mockGc);
        verify(mockPuzzle, times(1)).render(mockGc);
        verify(mockDoor, times(1)).render(mockGc);
    }

    @Test
    public void testUpdate() {
        level.update();

        verify(mockPuzzle, times(1)).checkPlayerInRange(mockPlayer);
        verify(mockDoor, times(1)).checkPlayerInRange(mockPlayer);
        verify(mockDoor, times(1)).checkUnlock(mockPuzzle);
    }

    @Test
    public void testGetPlayer() {
        assertEquals(mockPlayer, level.getPlayer());
    }

    @Test
    public void testGetPuzzle() {
        assertEquals(mockPuzzle, level.getPuzzle());
    }

    @Test
    public void testGetDoor() {
        assertEquals(mockDoor, level.getDoor());
    }
}

