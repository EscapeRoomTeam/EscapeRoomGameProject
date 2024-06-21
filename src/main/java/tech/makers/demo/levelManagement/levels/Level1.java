package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.player.Player;
import tech.makers.demo.levelManagement.Puzzle;

import java.util.Arrays;

public class Level1 extends Level {

    public Level1() {
        super(
                new Player(100, 100),
                new Puzzle(
                        300,
                        300,
                        "What will this code output?\n\n" +
                                "public class Main {\n" +
                                "    public static void main(String[] args) {\n" +
                                "        int x = 10;\n" +
                                "        x += 5;\n" +
                                "        System.out.println(x);\n" +
                                "    }\n" +
                                "}\n",
                        "15",
                        Arrays.asList("5", "10", "15", "20"),
                        "/sprites/Computer.png"
                ),
                new Door(600, 400, "/sprites/door.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "Remember to check your syntax!")
        );
    }
}