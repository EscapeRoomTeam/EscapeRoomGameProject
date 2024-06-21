package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.player.Player;
import tech.makers.demo.levelManagement.Puzzle;

import java.util.Arrays;

public class Level2 extends Level {

    public Level2() {
        super(
                new Player(100, 100),
                new Puzzle(
                        300,
                        300,
                        "What will this code output?\n\n" +
                                "import org.junit.Test;\n" +
                                "import static org.junit.Assert.*;\n\n" +
                                "public class TestExample {\n" +
                                "    @Test\n" +
                                "    public void addition() {\n" +
                                "        int sum = 2 + 3;\n" +
                                "        assertEquals(5, sum);\n" +
                                "    }\n" +
                                "}\n",
                        "Test passed",
                        Arrays.asList("Compilation error", "Runtime error", "Test failed", "Test passed"),
                        "/sprites/Computer 2.png"
                ),
                new Door(600, 400, "/sprites/Door 2.png")
        );
    }
}