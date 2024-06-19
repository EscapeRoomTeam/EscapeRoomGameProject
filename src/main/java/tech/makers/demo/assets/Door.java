package tech.makers.demo.assets;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.levels.LevelManager;
import tech.makers.demo.player.Player;
import tech.makers.demo.levels.Puzzle;

public class Door {
    private double x;
    private double y;
    public boolean locked;
    public boolean inRange;
    Sound sound = new Sound();

    public Door(double x, double y) {
        this.x = x;
        this.y = y;
        this.locked = true;
        this.inRange = false;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(locked ? javafx.scene.paint.Color.RED : javafx.scene.paint.Color.GREEN);
        gc.fillRect(x, y, 100, 100);
    }

    public void interact(Puzzle puzzle, LevelManager levelManager) {
        if (locked) {
            showLockedMessage();
        } else {
            showGameCompleteMessage(levelManager);
            // play sfx
            sound.setFile(1);
            sound.play();
        }
    }

    public void showLockedMessage() {
        if (inRange) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Door Locked");
                alert.setHeaderText(null);
                alert.setContentText("The door is locked until the puzzle is completed.");
                alert.showAndWait();
            });
        }
    }

    public void showGameCompleteMessage(LevelManager levelManager) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Door Unlocked");
            alert.setHeaderText(null);
            alert.setContentText("You have completed the level!");
            alert.showAndWait();
            levelManager.loadNextLevel();
        });
    }

    public void checkUnlock(Puzzle puzzle) {
        if (puzzle.isSolved()) {
            locked = false;
        }
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 100;
    }

    public boolean intersects(Double playerX, double playerY) {
        return playerX < x + 100 && playerX + 50 > x && playerY < y + 100;
    }

    public boolean isInRange() {
        return inRange;
    }
}
