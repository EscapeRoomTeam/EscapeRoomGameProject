package tech.makers.demo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.CableInteraction;
import tech.makers.demo.player.Inventory;
import tech.makers.demo.player.Player;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {


    private List<Puzzle> mockPuzzle;
    private List<Interaction> mockInteractions;
    private Door mockDoor;
    private Player player;
    private Inventory mockInventory;
    private CableInteraction mockCableInteraction;



    @BeforeEach
    public void setUp() {
        mockPuzzle = Collections.singletonList(mock(Puzzle.class));
        mockInteractions = Collections.singletonList(mock(Interaction.class));
        mockDoor = mock(Door.class);
        player = new Player(100, 200, mockInventory);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, player.getX(), 0.0);
        assertEquals(200, player.getY(), 0.0);
    }

    @Test
    public void testMoveUp() {
        player.moveUp(mockPuzzle, mockDoor, mockInteractions);
        assertEquals(180, player.getY(), 0.0);
    }

    @Test
    public void testMoveDown() {
        player.moveDown(mockPuzzle, mockDoor, mockInteractions);
        assertEquals(220, player.getY(), 0.0);
    }

    @Test
    public void testMoveLeft() {
        player.moveLeft(mockPuzzle, mockDoor, mockInteractions);
        assertEquals(80, player.getX(), 0.0);
    }

    @Test
    public void testMoveRight() {
        player.moveRight(mockPuzzle, mockDoor, mockInteractions);
        assertEquals(120, player.getX(), 0.0);
    }

    @Test
    public void testCheckPuzzleCollision() {
        when(mockPuzzle.get(0).intersects(eq(100.0), eq(200.0))).thenReturn(false);
        when(mockPuzzle.get(0).intersects(eq(300.0), eq(300.0))).thenReturn(true);

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





