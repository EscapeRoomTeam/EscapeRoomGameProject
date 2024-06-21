package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.assets.Door;
import tech.makers.demo.player.Player;

public class Level {
    private Player player;
    private Puzzle puzzle;
    private Door door;
    private boolean completed;

    public Level(Player player, Puzzle puzzle, Door door) {
        this.player = player;
        this.puzzle = puzzle;
        this.door = door;
        this.completed = false;
    }

    public void render(GraphicsContext gc) {
        player.render(gc);
        puzzle.render(gc);
        door.render(gc);
    }

    public void update() {
        puzzle.checkPlayerInRange(player);
        door.checkPlayerInRange(player);
        door.checkUnlock(puzzle);
    }

    public boolean isCompleted() {
        return completed;
    }

    public Player getPlayer() {
        return player;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public Door getDoor() {
        return door;
    }

    public void completeLevel() {
        this.completed = true;
    }
}