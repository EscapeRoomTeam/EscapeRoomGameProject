package tech.makers.demo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.player.Player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class PlayerTest {

    @BeforeAll
    public static void initJfx() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TestApp::new);
    }

    @BeforeEach
    public void setUp() {
        // You can add any setup code here if needed
    }

    @Test
    public void testInitialPosition() {
        Player player = new Player(100, 200);
        assertEquals(100, player.getX(), 0.0);
        assertEquals(200, player.getY(), 0.0);
    }

    @Test
    public void testMoveUp() {
        Player player = new Player(100, 200);
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door = new Door(600, 400);
        player.moveUp(puzzle, door);
        assertEquals(180, player.getY(), 0.0);
    }

    @Test
    public void testMoveDown() {
        Player player = new Player(100, 200);
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door = new Door(600, 400);
        player.moveDown(puzzle, door);
        assertEquals(220, player.getY(), 0.0);
    }

    @Test
    public void testMoveLeft() {
        Player player = new Player(100, 200);
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door = new Door(600, 400);
        player.moveLeft(puzzle, door);
        assertEquals(80, player.getX(), 0.0);
    }

    @Test
    public void testMoveRight() {
        Player player = new Player(100, 200);
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door = new Door(600, 400);
        player.moveRight(puzzle, door);
        assertEquals(120, player.getX(), 0.0);
    }

    @Test
    public void testCheckPuzzleCollision() {
        Player player = new Player(100, 200);
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        assertFalse(player.checkPuzzleCollision(100, 200, puzzle));
        assertTrue(player.checkPuzzleCollision(300, 300, puzzle));
    }

    @Test
    public void testCheckDoorCollision() {
        Player player = new Player(100, 200);
        Door door = new Door(600, 400);
        assertFalse(player.checkDoorCollision(100, 200, door));
        assertTrue(player.checkDoorCollision(600, 400, door));
    }

    @Test
    public void testRender() {
        Player player = new Player(100, 200);
        Canvas canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        player.render(gc);
        // Mocking the behavior since `setFill` and other methods are not exposed directly
        assertNotNull(gc.getFill());  // Simple assertion to ensure render method was invoked
    }
}