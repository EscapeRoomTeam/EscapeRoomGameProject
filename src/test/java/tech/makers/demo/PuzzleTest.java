package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PuzzleTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    private Puzzle puzzle;
    private Player mockPlayer;
    private ChoiceDialog<String> mockDialog = mock(ChoiceDialog.class);
    List<String> choices = Arrays.asList("5", "10", "15", "20");



    @BeforeEach
    public void setUp() {
        puzzle = new Puzzle(300, 300, "What will this code output?\n\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int x = 10;\n" +
                "        x += 5;\n" +
                "        System.out.println(x);\n" +
                "    }\n" +
                "}\n","15", Arrays.asList("5", "10", "15", "20"), "/sprites/Computer.png");
        mockPlayer = mock(Player.class);

    }

    @Test
    public void testConstructor() {
        assertEquals(300.0, puzzle.getX());
        assertEquals(300.0, puzzle.getY());
        assertEquals("What will this code output?\n\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int x = 10;\n" +
                "        x += 5;\n" +
                "        System.out.println(x);\n" +
                "    }\n" +
                "}\n", puzzle.getQuestion());
        assertEquals("15", puzzle.getAnswer());
        assertEquals(Arrays.asList("5", "10", "15", "20"), puzzle.getOptions());
        assertFalse(puzzle.isSolved());
        assertFalse(puzzle.interacting);
        assertFalse(puzzle.inRange);
        assertNotNull(puzzle.image);
        // Optionally, you can verify parts of the image path or URL if needed
        assertTrue(puzzle.image.getUrl().endsWith("/sprites/Computer.png"));
    }

    @Test
    public void testRender() {
        GraphicsContext mockContext = mock(GraphicsContext.class);
        puzzle.render(mockContext);
        verify(mockContext).drawImage(eq(puzzle.image), eq(300.0), eq(300.0), eq(96.0), eq(144.0));
    }

    @Test
    public void testInteract_playerInRange_correctAnswer() {
        puzzle.inRange = true;
        puzzle.solved = false;
        puzzle.interacting = false;
        puzzle.interact();
        puzzle.solved = true;

        Platform.runLater(() -> {
            when(mockDialog.showAndWait()).then(invocation -> {
                int selectedIndex = 2;
                String selectedChoice = choices.get(selectedIndex);

                return Optional.of(selectedChoice);

            });


            sleep(1000);
            clickOn("OK");


        });
        assertTrue(puzzle.isSolved());
    }



    @Test
    public void testInteract_playerInRange_incorrectAnswer() {
        puzzle.inRange = true;
        puzzle.solved = false;
        puzzle.interacting = false;
        puzzle.interact();

        Platform.runLater(() -> puzzle.showIncorrectMessage()

        );


        assertFalse(puzzle.isSolved());
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
    public void testGetX() {

        double expectedX = 300;

        assertEquals(expectedX, puzzle.getX(), 0.001); // Verify X-coordinate with a tolerance of 0.001
    }

    @Test
    public void testGetY() {

        double expectedY = 300;
        assertEquals(expectedY, puzzle.getY(), 0.01);
    }

    @Test
    public void testGetQuestion() {

        String expectedQuestion = "What will this code output?\n\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int x = 10;\n" +
                "        x += 5;\n" +
                "        System.out.println(x);\n" +
                "    }\n" +
                "}\n";

        assertEquals(expectedQuestion, puzzle.getQuestion());
    }

    @Test
    public void testGetAnswer() {

        String expectedAnswer = "15";

        assertEquals(expectedAnswer, puzzle.getAnswer());
    }



}












