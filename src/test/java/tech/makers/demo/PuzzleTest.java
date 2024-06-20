package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.player.Player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PuzzleTest {
    private Puzzle puzzle;
    private Player player;

    @BeforeAll
    public static void initJfx() {
        TestApp.launchApp();
    }

    @BeforeEach
    public void setUp() {
        puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        player = mock(Player.class);
    }

    @Test
    public void testConstructor() {
        double x = 10.0;
        double y = 20.0;
        String question = "What is the answer to life, the universe, and everything?";
        String answer = "42";
        Puzzle puzzle = new Puzzle(x, y, question, answer);
        assertEquals(x, puzzle.getX(), 0.0);
        assertEquals(y, puzzle.getY(), 0.0);
        assertEquals(question, puzzle.getQuestion());
        assertEquals(answer, puzzle.getAnswer());
        assertFalse(puzzle.isSolved());
    }

    @Test
    public void testRender_notSolved() {
        GraphicsContext mockContext = mock(GraphicsContext.class); // Mock the GraphicsContext

        puzzle.render(mockContext);

        // Verify that drawImage was called with the correct parameters
        verify(mockContext).drawImage(any(), eq(300.0), eq(300.0), eq(96.0), eq(144.0));
    }

    @Test
    public void testRender_solved() {
        puzzle.solved = true;
        GraphicsContext mockContext = mock(GraphicsContext.class); // Mock the GraphicsContext

        puzzle.render(mockContext);

        // Verify that drawImage was called with the correct parameters
        verify(mockContext).drawImage(any(), eq(300.0), eq(300.0), eq(96.0), eq(144.0));
    }

    @Test
    public void testCheckPlayerInRange() {
        when(player.getX()).thenReturn(290.0);
        when(player.getY()).thenReturn(290.0);
        puzzle.checkPlayerInRange(player);
        assertTrue(puzzle.inRange);
    }

    @Test
    public void testIntersects() {
        assertTrue(puzzle.intersects(310, 310));
        assertFalse(puzzle.intersects(400, 400));
    }
}