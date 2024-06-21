package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PuzzleTest extends ApplicationTest {

    private Puzzle puzzle;
    private Player mockPlayer;
    private TextInputDialog mockDialog;

    @Override
    public void start(javafx.stage.Stage stage) {
        // This method is required by ApplicationTest, but we don't need to set up a stage for these tests.
    }

    @BeforeEach
    public void setUp() {
        puzzle = new Puzzle(300, 300, "What is the capital of France?", "Paris", "/tech/makers/demo/assets/door.png");
        mockPlayer = mock(Player.class);
        mockDialog = mock(TextInputDialog.class);
    }

    @Test
    public void testConstructor() {
        assertEquals(null, puzzle.image); // Image should not be loaded yet
    }

    @Test
    public void testRender() {
        GraphicsContext mockContext = mock(GraphicsContext.class);
        puzzle.render(mockContext);
        verify(mockContext).drawImage(eq(puzzle.image), eq(300.0), eq(300.0), eq(96.0), eq(144.0));
    }

    @Test
    public void testInteract_playerInRange_correctAnswer() {
        when(mockPlayer.getX()).thenReturn(300.0);
        when(mockPlayer.getY()).thenReturn(300.0);
        when(mockDialog.showAndWait()).thenReturn(Optional.of("Paris"));

        puzzle.interact();

        assertTrue(puzzle.isSolved());
        InOrder inOrder = inOrder(mockDialog);
        inOrder.verify(mockDialog).setTitle("Puzzle");
        inOrder.verify(mockDialog).setHeaderText("What is the capital of France?");
        inOrder.verify(mockDialog).showAndWait();
    }

    @Test
    public void testInteract_playerInRange_incorrectAnswer() {
        when(mockPlayer.getX()).thenReturn(300.0);
        when(mockPlayer.getY()).thenReturn(300.0);
        when(mockDialog.showAndWait()).thenReturn(Optional.of("London"));

        puzzle.interact();

        assertFalse(puzzle.isSolved());
        verify(mockDialog).showAndWait();
    }

    @Test
    public void testInteract_playerNotInRange() {
        when(mockPlayer.getX()).thenReturn(400.0);
        when(mockPlayer.getY()).thenReturn(400.0);

        puzzle.interact();

        assertFalse(puzzle.isSolved());
    }

    @Test
    public void testCheckPlayerInRange_withinRange() {
        when(mockPlayer.getX()).thenReturn(290.0);
        when(mockPlayer.getY()).thenReturn(290.0);

        puzzle.checkPlayerInRange(mockPlayer);

        assertTrue(puzzle.inRange);
    }

    @Test
    public void testCheckPlayerInRange_outsideRange() {
        when(mockPlayer.getX()).thenReturn(400.0);
        when(mockPlayer.getY()).thenReturn(400.0);

        puzzle.checkPlayerInRange(mockPlayer);

        assertFalse(puzzle.inRange);
    }

    @Test
    public void testIntersects_playerInsidePuzzle() {
        assertTrue(puzzle.intersects(310.0, 310.0));
    }

    @Test
    public void testIntersects_playerOutsidePuzzle() {
        assertFalse(puzzle.intersects(400.0, 400.0));
    }

    @Test
    public void testShowIncorrectMessage() {

        Alert alert = mock(Alert.class);
        puzzle.showIncorrectMessage();
        verify(alert).setContentText("YOU SUCK"); // Example verification
    }

    @Test
    public void testGetX() {
        Puzzle puzzle = new Puzzle(100.0, 200.0, "What is the capital of France?", "Paris", "/tech/makers/demo/assets/door.png");
        double expectedX = 100.0;

        assertEquals(expectedX, puzzle.getX(), 0.001); // Verify X-coordinate with a tolerance of 0.001
    }

    @Test
    public void testGetY() {
        Puzzle puzzle = new Puzzle(100.0, 200.0, "What is the capital of France?", "Paris", "/tech/makers/demo/assets/door.png");
        double expectedY = 200.0;
        assertEquals(expectedY, puzzle.getY(), 0.01);
    }

    @Test
    public void testGetQuestion() {
        Puzzle puzzle = new Puzzle(100.0, 200.0, "What is the capital of France?", "Paris", "/tech/makers/demo/assets/door.png");
        String expectedQuestion = "What is the capital of France?";

        assertEquals(expectedQuestion, puzzle.getQuestion());
    }

    @Test
    public void testGetAnswer() {
        Puzzle puzzle = new Puzzle(100.0, 200.0, "What is the capital of France?", "Paris", "/tech/makers/demo/assets/door.png");
        String expectedAnswer = "Paris";

        assertEquals(expectedAnswer, puzzle.getAnswer());
    }



}












