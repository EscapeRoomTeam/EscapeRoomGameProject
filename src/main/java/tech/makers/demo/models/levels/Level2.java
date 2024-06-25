package tech.makers.demo.models.levels;

import tech.makers.demo.models.assets.Computer;
import tech.makers.demo.models.assets.Door;
import tech.makers.demo.models.assets.Eddie;
import tech.makers.demo.models.assets.Safe;
import tech.makers.demo.models.Player;
import tech.makers.demo.models.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Level2 extends Player.Level {

    public Level2() {
        super(
                new Player(100, 100),
                createPuzzles(), // Pass the list of puzzles
                new Door(600, 400, "/sprites/Door 2.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "The code for the safe is 1234."),
                new Computer(300, 300, "/sprites/Computer.png"),
                new Safe(50, 50, "/sprites/Router.png", "1234")
        );

    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
//        puzzles.add(new Puzzle(300, 300, "What is the capital of France?", "Paris", null, "/sprites/Computer.png"));
//        puzzles.add(new Puzzle(400, 150, "Say Hi", "Hi", null,  "/sprites/Computer.png"));
        return puzzles;
    }
}