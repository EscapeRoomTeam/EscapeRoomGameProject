package tech.makers.demo.assets;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import tech.makers.demo.levels.LevelManager;
import tech.makers.demo.player.Player;
import tech.makers.demo.levels.Puzzle;

public class Door {
    private final double x;
    private final double y;
    private boolean locked;
    private boolean inRange;
    private final Image doorImage;
    Sound sound = new Sound();

    public Door(double x, double y) {
        this.x = x;
        this.y = y;
        this.locked = true;
        this.inRange = false;
        this.doorImage = new Image(getClass().getResource("/sprites/door.png").toExternalForm()); // Load the door image
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(doorImage, x, y, 96, 144); // Draw the door image at the specified position with size 96x144
    }

    public void interact(Puzzle puzzle, LevelManager levelManager) {
        if (locked) {
            showLockedMessage();
        } else if (inRange) {
            levelManager.completeLevel(); // Directly call the completeLevel method in LevelManager
            // Play sound effect
            sound.setFile(1);
            sound.setVolume(-20.0f);
            sound.play();
        }
    }

    private void showLockedMessage() {
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

    public void checkUnlock(Puzzle puzzle) {
        if (puzzle.isSolved()) {
            locked = false;
        }
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 100;
    }

    public boolean intersects(double playerX, double playerY) {
        return playerX < x + 100 && playerX + 50 > x && playerY < y + 100;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isInRange() {
        return inRange;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}