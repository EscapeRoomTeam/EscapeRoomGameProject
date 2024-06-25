package tech.makers.demo.models.levels;


import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.models.Player;
import tech.makers.demo.models.Puzzle;
import tech.makers.demo.models.assets.Computer;
import tech.makers.demo.models.assets.Door;
import tech.makers.demo.models.assets.Eddie;
import tech.makers.demo.models.assets.Safe;

import java.util.List;

public class Level {
    private Player player;
    private List<Puzzle> puzzles;
    private Door door;
    private Eddie helperCharacter;
    private Computer computer;
    private Safe safe;
    private boolean completed;

    public Level(Player player, List<Puzzle> puzzles, Door door, Eddie helperCharacter, Computer computer, Safe safe) {
        this.player = player;
        this.puzzles = puzzles;
        this.door = door;
        this.helperCharacter = helperCharacter;
        this.computer = computer;
        this.safe = safe;
        this.completed = false;
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
        if (computer != null) {
            computer.render(gc);
        }
        if (safe != null) {
            safe.render(gc);
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

        if (safe != null) {
            safe.checkPlayerInRange(player);
        }

        if (computer != null) {
            computer.checkPlayerInRange(player);
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

    public void handleInteraction() {
        if (helperCharacter != null && helperCharacter.isInRange(player.getX(), player.getY())) {
            helperCharacter.interact();
        }
        if (computer != null && computer.isInRange()) {
            computer.interact(player);
        }
        if (safe != null && safe.isInRange()) {
            safe.interact(player);
        }
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

    public Computer getComputer() {
        return computer;
    }

    public Safe getSafe() {
        return safe;
    }

    public void completeLevel() {
        this.completed = true;
    }

    public void unlockDoor() {
        Door.unlock();
    }
}