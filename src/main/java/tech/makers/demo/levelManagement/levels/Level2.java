package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.levelManagement.*;
import tech.makers.demo.player.Inventory;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Level2 extends Level {

    public Level2() {
        super(
                new Player(100, 100, new Inventory()), // Creating a new Inventory),
                createPuzzles(),
                new Door(600, 400, "/sprites/Door.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "..."),
                createInteractions()
        );
    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
        // Add any initial puzzles here if needed
        return puzzles;
    }

    private static List<Interaction> createInteractions() {
        List<Interaction> interactions = new ArrayList<>();
        Door door = new Door(600, 400, "/sprites/door.png");
        ComputerInteraction2 computerInteraction2 = new ComputerInteraction2(300, 300, "/sprites/Computer.png", door);
        EddieInteraction2 eddieInteraction = new EddieInteraction2(700, 50, "/sprites/transparent.png");
        SafeInteraction safeInteraction = new SafeInteraction(100, 400, "/sprites/safe22.gif", eddieInteraction, computerInteraction2);

        interactions.add(computerInteraction2);
        interactions.add(safeInteraction);
        interactions.add(eddieInteraction);

        return interactions;
    }
}
