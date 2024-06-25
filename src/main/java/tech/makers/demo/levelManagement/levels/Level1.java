package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;

import tech.makers.demo.levelManagement.ComputerInteraction;
import tech.makers.demo.levelManagement.EddieInteraction;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.levelManagement.RouterInteraction;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level1 extends Level {

    public Level1() {
        super(
                new Player(100, 100),
                createPuzzles(),
                new Door(600, 400, "/sprites/door.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "..."),
                createInteractions()  // Add interactions list
        );
    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
        puzzles.add(new Puzzle(200, 200, "What is the capital of France?", "Paris", Arrays.asList("Berlin", "Madrid", "Paris", "Rome"), "/sprites/Computer.png"));
        return puzzles;
    }

    private static EddieInteraction createEddieInteraction() {
        Door door = new Door(600, 400, "/sprites/door.png");
        ComputerInteraction computerInteraction = new ComputerInteraction(300, 300, "/sprites/Computer.png", door);
        return new EddieInteraction(700, 50, "/sprites/transparent.png", computerInteraction);
    }

    private static List<Interaction> createInteractions() {
        List<Interaction> interactions = new ArrayList<>();
        Door door = new Door(600, 400, "/sprites/door.png");
        ComputerInteraction computerInteraction = new ComputerInteraction(300, 300, "/sprites/Computer.png", door);
        EddieInteraction eddieInteraction = new EddieInteraction(700, 50, "/sprites/transparent.png", computerInteraction);
        interactions.add(computerInteraction);
        interactions.add(new RouterInteraction(100, 400, "/sprites/Router.png", computerInteraction));
        interactions.add(eddieInteraction);
        return interactions;
    }
}
