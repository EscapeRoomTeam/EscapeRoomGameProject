package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Obstacle;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;
import tech.makers.demo.assets.Eddie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelTest {

    private Player mockPlayer;
    private List<Puzzle> mockPuzzles;
    private Door mockDoor;
    private Eddie mockHelperCharacter;
    private GraphicsContext mockGc;
    private Level level;
    private List<Interaction> mockInteractions;
    private List<Obstacle> mockObstacles;


    @BeforeEach
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockPuzzles = new ArrayList<>();
        mockPuzzles.add(mock(Puzzle.class));
        mockHelperCharacter = mock(Eddie.class);
        mockDoor = mock(Door.class);
        mockGc = mock(GraphicsContext.class);
        mockInteractions = new ArrayList<>();
        mockInteractions.add(mock(Interaction.class));
        level = new Level(mockPlayer, mockPuzzles, mockDoor, mockHelperCharacter, mockInteractions, mockObstacles);
    }

    @Test
    public void testConstructor() {
        assertEquals(mockPlayer, level.getPlayer());
        assertEquals(mockPuzzles, level.getPuzzles());
        assertEquals(mockDoor, level.getDoor());
    }

    @Test
    public void testRender() {
        level.render(mockGc);

        verify(mockPlayer, times(1)).render(mockGc);
        for (Puzzle puzzle : mockPuzzles) {
            verify(puzzle, times(1)).render(mockGc);
        }
        verify(mockDoor, times(1)).render(mockGc);
        verify(mockHelperCharacter, times(1)).render(mockGc);
    }

    @Test
    public void testUpdate() {
        level.update();

        for (Puzzle puzzle : mockPuzzles) {
            verify(puzzle, times(1)).checkPlayerInRange(mockPlayer);
        }
        verify(mockDoor, times(1)).checkPlayerInRange(mockPlayer);

    }

    @Test
    public void testGetPlayer() {
        assertEquals(mockPlayer, level.getPlayer());
    }

    @Test
    public void testGetPuzzle() {
        assertEquals(mockPuzzles, level.getPuzzles());
    }

    @Test
    public void testGetDoor() {
        assertEquals(mockDoor, level.getDoor());
    }
}

