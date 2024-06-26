package tech.makers.demo.levelManagement.levels.LevelOne;

import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;

import tech.makers.demo.levelManagement.levels.LevelOne.interactions.ComputerInteraction;
import tech.makers.demo.assets.Obstacle;

import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.levelManagement.levels.LevelOne.interactions.EddieInteraction;
import tech.makers.demo.levelManagement.levels.LevelTwo.interactions.RouterInteraction;
import tech.makers.demo.player.Inventory;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends Level {

    public Level1() {
        super(
                new Player(100, 100, new Inventory()),
                createPuzzles(),
                new Door(600, 400, "/sprites/door.png"),
                new Eddie(700, 50, "/sprites/Eddie_idle_anim.png", "..."),
                createInteractions(),
                createObstacles()  // Add obstacles list
        );
    }

    private static List<Puzzle> createPuzzles() {
        List<Puzzle> puzzles = new ArrayList<>();
        return puzzles;
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

    private static List<Obstacle> createObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(200, 200, 40, 40, "/sprites/Toybox.gif"));
        obstacles.add(new Obstacle(500, 100, 40, 40, "/sprites/Spider.gif"));
        obstacles.add(new Obstacle(600, 70, 40, 20, "/sprites/cat.gif"));
        obstacles.add(new Obstacle(650, 50, 40, 40, "/sprites/coffee.gif"));
        return obstacles;
    }
}
