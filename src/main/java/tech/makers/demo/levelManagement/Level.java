package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.player.Player;
import tech.makers.demo.levelManagement.Puzzle;

public class Level {
    private Player player;
    private Puzzle puzzle;
    private Door door;
    private Eddie helperCharacter;

    private boolean completed;

    public Level(Player player, Puzzle puzzle, Door door, Eddie helperCharacter) {
        this.player = player;
        this.puzzle = puzzle;
        this.door = door;
        this.helperCharacter = helperCharacter;

        this.completed = false;
    }

    public void render(GraphicsContext gc) {
        player.render(gc);
        puzzle.render(gc);
        door.render(gc);
        if (helperCharacter != null) {
            helperCharacter.render(gc);
        }
    }

    public void update() {
        puzzle.checkPlayerInRange(player);
        door.checkPlayerInRange(player);
        door.checkUnlock(puzzle);
        if (helperCharacter != null) {
            helperCharacter.update();
        }
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

    public Eddie getHelperCharacter() {
        return helperCharacter;
    }

    public void completeLevel() {
        this.completed = true;
    }
}