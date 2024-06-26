package tech.makers.demo.levelManagement.levels;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.assets.Obstacle;
import tech.makers.demo.levelManagement.*;
import tech.makers.demo.player.Inventory;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.ComputerInteraction;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Level3 extends Level {

    public Level3() {
        super(
                new Player(100, 100, new Inventory()),
                createPuzzles(),
                new Door(600, 400, "/sprites/Door.png"),
                new Eddie(450, 50, "/sprites/Eddie_idle_anim.png", "..."),
                createInteractions(),
                createObstacles()
        );
    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
        // Add any initial puzzles here if needed
        return puzzles;
    }

    private static EddieInteraction3 createEddieInteraction() {
        return new EddieInteraction3(450, 50, "/sprites/Eddie_idle_anim.png");
    }

    private static List<Interaction> createInteractions() {
        List<Interaction> interactions = new ArrayList<>();
        EddieInteraction3 eddieInteraction = new EddieInteraction3(450, 50, "/sprites/transparent.png");
        MacbookInteraction macbookInteraction = new MacbookInteraction(300, 300, "/sprites/Router.png");
        CableInteraction cableInteraction = new CableInteraction(100, 200, "/sprites/hdmi.png");
        HDMIInteraction hdmiInteraction = new HDMIInteraction(350, 50, "/sprites/tvscreen.png", macbookInteraction, cableInteraction);
        CrowdInteraction crowdInteraction = new CrowdInteraction(250, 150, "/sprites/crowded.gif");
        interactions.add(macbookInteraction);
        interactions.add(cableInteraction);
        interactions.add(hdmiInteraction);
        interactions.add(eddieInteraction);
        interactions.add(crowdInteraction);
        return interactions;
    }

    private static List<Obstacle> createObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(200, 200, 40, 40, "/sprites/Cuckoo.gif"));
        obstacles.add(new Obstacle(500, 100, 40, 40, "/sprites/Fishtank.gif"));
        obstacles.add(new Obstacle(600, 70, 40, 20, "/sprites/BookShelf.gif"));
        obstacles.add(new Obstacle(650, 50, 40, 40, "/sprites/Checker.gif"));
        return obstacles;
    }
}
