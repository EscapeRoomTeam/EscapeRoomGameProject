package tech.makers.demo.levels;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.assets.Door;
import tech.makers.demo.player.Player;

public class Level {
    private Player player;
    private Puzzle puzzle;
    private Door door;

    public Level(Player player, Puzzle puzzle, Door door) {
        this.player = player;
        this.puzzle = puzzle;
        this.door = door;
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

    public Player getPlayer() {
        return player;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public Door getDoor() {
        return door;
    }
}