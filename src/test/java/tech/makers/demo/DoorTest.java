package tech.makers.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoorTest {
    private Door door;
    private Player player;
    private Puzzle puzzle;

    @BeforeEach
    public void setUp() {
        door = new Door(600, 400);
        player = mock(Player.class);
        puzzle = mock(Puzzle.class);
    }

    @Test
    public void testCheckUnlock() {
        when(puzzle.isSolved()).thenReturn(true);
        door.checkUnlock(puzzle);
        assertFalse(door.locked);
    }

    @Test
    public void testCheckPlayerInRange() {
        when(player.getX()).thenReturn(590.0);
        when(player.getY()).thenReturn(390.0);
        door.checkPlayerInRange(player);
        assertTrue(door.inRange);
    }

    @Test
    public void testIntersects() {
        assertTrue(door.intersects(610.0, 410.0));
        assertFalse(door.intersects(700.0, 500.0));
    }
}

