package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


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



    @Test
    public void testRender_notSolved_isRed() {
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        GraphicsContext mockContext = mock(GraphicsContext.class); // Mock the GraphicsContext

        puzzle.render(mockContext);

        // Verify that setFill was called with Color.RED
        verify(mockContext).setFill(Color.RED);
    }


    @Test
    public void testRender_solved_isGreen() {
        Puzzle puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        puzzle.solved = true;
        GraphicsContext mockContext = mock(GraphicsContext.class); // Mock the GraphicsContext
        puzzle.render(mockContext);
        //Verify the colour is Green
        verify(mockContext).setFill(Color.GREEN);
    }









}
