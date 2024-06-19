package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.levels.Level;
import tech.makers.demo.levels.LevelManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelManagerTest {
    private GraphicsContext mockGc;
    private LevelManager levelManager;

    @BeforeAll // starting the Test App before all tests
    public static void initJfx() {
        TestApp.launchApp();
    }

    @BeforeEach //Making sure that the LevelManager is reset before each test
    public void setUp() {
        mockGc = mock(GraphicsContext.class);
        levelManager = new LevelManager(mockGc);
    }

    @Test
    public void testInitialization() {
        Level currentLevel = levelManager.getCurrentLevel();
        assertNotNull(currentLevel);
        assertEquals(100, currentLevel.getPlayer().getX());
        assertEquals(100, currentLevel.getPlayer().getY());
    }

    @Test
    public void testLoadNextLevel() {
        levelManager.loadNextLevel();
        Level currentLevel = levelManager.getCurrentLevel();
        assertNotNull(currentLevel);
        assertEquals("What is the capital of France?", currentLevel.getPuzzle().getQuestion());
    }

    @Test
    public void testLoadNextLevelAtEnd() {
        levelManager.loadNextLevel();
        levelManager.loadNextLevel(); // Try to load beyond the last level

        Level currentLevel = levelManager.getCurrentLevel();
        assertEquals("What is the capital of France?", currentLevel.getPuzzle().getQuestion()); // Verifies it's still the last level

        // Attempting to load the next level beyond the last one should not change the level
        levelManager.loadNextLevel();
        assertEquals("What is the capital of France?", currentLevel.getPuzzle().getQuestion()); // Still the last level
    }

    @Test //testing if the render method is called
    public void testRender() {
        levelManager.render();
        Level currentLevel = levelManager.getCurrentLevel();
        verify(mockGc, times(1)).setFill(any());
        verify(mockGc, times(1)).fillRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test //testing if the update method is called
    public void testUpdate() {
        Level mockLevel = mock(Level.class);
        Level[] levels = { mockLevel, mock(Level.class) };
        levelManager = new LevelManager(mockGc) {
            @Override
            protected void initializeLevels() {
                setLevels(levels);
            }
        };
        levelManager.update();
        verify(mockLevel, times(1)).update();
    }
}



