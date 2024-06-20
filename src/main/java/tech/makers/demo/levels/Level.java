package tech.makers.demo.levels;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.assets.Door;
import tech.makers.demo.player.Player;
import tech.makers.demo.levels.Puzzle;

import java.util.List;

public class Level {
    private Player player;
    private List<Puzzle> puzzles;
    private Door door;
    private boolean completed;

    public Level(Player player, List<Puzzle> puzzles, Door door) {
        this.player = player;
        this.puzzles = puzzles;
        this.door = door;
        this.completed = false;
    }

    public void render(GraphicsContext gc) {
        player.render(gc);
        for (Puzzle puzzle : puzzles) {
            puzzle.render(gc);
        }
        door.render(gc);
    }

    public void update() {
        for (Puzzle puzzle : puzzles) {
            puzzle.checkPlayerInRange(player);
        }
        door.checkPlayerInRange(player);

        boolean allPuzzlesSolved = true;
        for (Puzzle puzzle : puzzles) {
            if (!puzzle.isSolved()) {
                allPuzzlesSolved = false;
                break;
            }
        }
        if (allPuzzlesSolved) {
            door.checkUnlock(); // Assuming the door unlocks when all puzzles are solved
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
}
