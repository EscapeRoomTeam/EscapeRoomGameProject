package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.assets.Obstacle;
import tech.makers.demo.player.Player;
import tech.makers.demo.levelManagement.Puzzle;

import java.util.List;

public class Level {
    private Player player;
    private List<Puzzle> puzzles;
    private Door door;
    private Eddie helperCharacter;
    private List<Interaction> interactions;  // List of interactions
    private List<Obstacle> obstacles;

    private boolean completed;

    public Level(Player player, List<Puzzle> puzzles, Door door, Eddie helperCharacter, List<Interaction> interactions, List<Obstacle> obstacles) {
        this.player = player;
        this.puzzles = puzzles;
        this.door = door;
        this.helperCharacter = helperCharacter;
        this.interactions = interactions;
        this.completed = false;
        this.obstacles = obstacles;
    }

    public void render(GraphicsContext gc) {
        // Render obstacles first
        for (Obstacle obstacle : obstacles) {
            obstacle.render(gc);
        }

        // Render interactions next
        for (Interaction interaction : interactions) {
            interaction.render(gc);
        }

        // Render puzzles
        for (Puzzle puzzle : puzzles) {
            puzzle.render(gc);
        }

        // Render the door
        door.render(gc);

        // Render the helper character if it exists
        if (helperCharacter != null) {
            helperCharacter.render(gc);
        }

        // Render the player last
        player.render(gc);
    }

    public void update() {
        for (Puzzle puzzle : puzzles) {
            puzzle.checkPlayerInRange(player);
        }
        door.checkPlayerInRange(player);

        if (helperCharacter != null) {
            helperCharacter.update();
        }

        for (Interaction interaction : interactions) {
            interaction.checkPlayerInRange(player);
        }
    }

    public boolean isCompleted() {
        boolean allPuzzlesSolved = true;
        for (Puzzle puzzle : puzzles) {
            if (!puzzle.isSolved()) {
                allPuzzlesSolved = false;
                break;
            }
        }
        return allPuzzlesSolved && door.isOpen();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public Door getDoor() {
        return door;
    }

    public Eddie getHelperCharacter() {
        return helperCharacter;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void completeLevel() {
        this.completed = true;
    }

    public List<Obstacle> getObstacles() {  // New getter for obstacles
        return obstacles;
    }
}