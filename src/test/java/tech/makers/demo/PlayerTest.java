package tech.makers.demo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.player.Player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest extends ApplicationTest {

    private Puzzle mockPuzzle;
    private Door mockDoor;
    private Player player;

    @Override
    public void start(javafx.stage.Stage stage) {
        // This method is required by ApplicationTest, but we don't need to set up a stage for these tests.
    }

    @BeforeEach
    public void setUp() {
        mockPuzzle = mock(Puzzle.class);
        mockDoor = mock(Door.class);
        player = new Player(100, 200);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, player.getX(), 0.0);
        assertEquals(200, player.getY(), 0.0);
    }

    @Test
    public void testMoveUp() {
        player.moveUp(mockPuzzle, mockDoor);
        assertEquals(180, player.getY(), 0.0);
    }

    @Test
    public void testMoveDown() {
        player.moveDown(mockPuzzle, mockDoor);
        assertEquals(220, player.getY(), 0.0);
    }

    @Test
    public void testMoveLeft() {
        player.moveLeft(mockPuzzle, mockDoor);
        assertEquals(80, player.getX(), 0.0);
    }

    @Test
    public void testMoveRight() {
        player.moveRight(mockPuzzle, mockDoor);
        assertEquals(120, player.getX(), 0.0);
    }

    @Test
    public void testCheckPuzzleCollision() {
        when(mockPuzzle.intersects(eq(100.0), eq(200.0))).thenReturn(false);
        when(mockPuzzle.intersects(eq(300.0), eq(300.0))).thenReturn(true);

        // Check for no collision
        assertFalse(player.checkPuzzleCollision(100, 200, mockPuzzle));

        // Check for collision
        player.x = 300;
        player.y = 300;
        assertTrue(player.checkPuzzleCollision(300, 300, mockPuzzle));
    }

    @Test
    public void testCheckDoorCollision() {
        when(mockDoor.intersects(eq(100.0), eq(200.0))).thenReturn(false);
        when(mockDoor.intersects(eq(600.0), eq(400.0))).thenReturn(true);

        // Check for no collision
        assertFalse(player.checkDoorCollision(100, 200, mockDoor));

        // Check for collision
        player.x = 600;
        player.y = 400;
        assertTrue(player.checkDoorCollision(600, 400, mockDoor));
    }

    @Test
    public void testRender() {
        Canvas canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        player.render(gc);
        assertEquals(Color.BLACK, gc.getFill());
    }
}





