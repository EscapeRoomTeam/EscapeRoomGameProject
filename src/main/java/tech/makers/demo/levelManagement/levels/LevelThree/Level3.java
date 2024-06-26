package tech.makers.demo.levelManagement.levels.LevelThree;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.assets.Obstacle;
import tech.makers.demo.levelManagement.*;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.*;
import tech.makers.demo.player.Inventory;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;

public class Level3 extends Level {

    public Level3() {
        super(
                new Player(100, 150, new Inventory()),
                createPuzzles(),
                new Door(600, 400, "/sprites/door.png"),
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
        MacbookInteraction macbookInteraction = new MacbookInteraction(345, 440, "/sprites/less_pixelated_goldenmac.png");
        CableInteraction cableInteraction = new CableInteraction(700, 200, "/sprites/hdmi.png");
        HDMIInteraction hdmiInteraction = new HDMIInteraction(350, 50, "/sprites/tvscreen.png", macbookInteraction, cableInteraction);
        CrowdInteraction crowdInteraction = new CrowdInteraction(250, 150, "/sprites/crowded.gif");
        ReceptionInteraction receptionInteraction = new ReceptionInteraction (50, 50, "/sprites/reception.png");
        interactions.add(macbookInteraction);
        interactions.add(cableInteraction);
        interactions.add(hdmiInteraction);
        interactions.add(eddieInteraction);
        interactions.add(crowdInteraction);
        interactions.add(receptionInteraction);
        return interactions;
    }

    private static List<Obstacle> createObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(50, 450, 350, 50, "/sprites/deskswithcomps.png"));
        obstacles.add(new Obstacle(675, 75, 55, 90, "/sprites/BrownDesk.png"));
        obstacles.add(new Obstacle(660, 65, 75, 80, "/sprites/OfficeChairRight.png"));
        obstacles.add(new Obstacle(675, 165, 55, 90, "/sprites/BrownDesk.png"));
        obstacles.add(new Obstacle(660, 155, 75, 80, "/sprites/OfficeChairRight.png"));
        return obstacles;
    }


}
