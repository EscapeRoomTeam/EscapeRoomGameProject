package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.player.Player;
import tech.makers.demo.levelManagement.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level2 extends Level {

    public Level2() {
        super(
                new Player(100, 100),
                createPuzzles(), // Pass the list of puzzles
                new Door(600, 400, "/sprites/Door 2.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "Remember to check your syntax!")
        );
    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
        puzzles.add(new Puzzle(300, 300, "What is the capital of France?", "Paris", Arrays.asList("Berlin", "Madrid", "Paris", "Rome"), "/sprites/Computer.png"));
        puzzles.add(new Puzzle(400, 150, "Say Hi", "Hi", Arrays.asList("Hello", "Hi", "Hey", "Hola"), "/sprites/Computer.png"));
        return puzzles;
    }
}