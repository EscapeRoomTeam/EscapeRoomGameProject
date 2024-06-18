package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import org.junit.Test;

import static javafx.beans.binding.Bindings.when;
import static org.junit.Assert.*;

public class PuzzleTest {

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


}
