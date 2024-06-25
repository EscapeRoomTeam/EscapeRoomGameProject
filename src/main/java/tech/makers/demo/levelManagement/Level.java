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
    private List<Interaction> interactions;
    private List<Obstacle> obstacles; // List of interactions

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
        player.render(gc);
        for (Puzzle puzzle : puzzles) {
            puzzle.render(gc);
        }
        door.render(gc);
        if (helperCharacter != null) {
            helperCharacter.render(gc);
        }
        for (Interaction interaction : interactions) {
            interaction.render(gc);
        }
        for (Obstacle obstacle : obstacles) {  // Render obstacles
            obstacle.render(gc);
        }
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

        boolean allPuzzlesSolved = true;
        for (Puzzle puzzle : puzzles) {
            if (!puzzle.isSolved()) {
                allPuzzlesSolved = false;
                Door.lock();
                break;
            }
        }
        if (allPuzzlesSolved) {
            Door.unlock();
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

    public List<Obstacle> getObstacles() {  // New getter for obstacles
        return obstacles;
    }

    public void completeLevel() {
        this.completed = true;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
