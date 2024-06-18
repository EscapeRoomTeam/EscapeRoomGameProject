package tech.makers.demo;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

public class Door {
    private double x;
    private double y;
    private boolean locked;
    boolean inRange;
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

    public void interact(Puzzle puzzle) {
        if (locked) {
            showLockedMessage();
        } else {
            showGameCompleteMessage();
            // play sfx
            sound.setFile(1);
            sound.play();
        }
    }

    void showLockedMessage() {
        if (inRange) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Door Locked");
            alert.setHeaderText(null);
            alert.setContentText("The door is locked until puzzle is completed");
            alert.showAndWait();
        });
    }
    }



    private void showGameCompleteMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Door Unlocked");
            alert.setHeaderText(null);
            alert.setContentText("You have completed the Game!");
            alert.showAndWait();
        });
    }

    public void checkUnlock(Puzzle puzzle) {
        if (puzzle.isSolved()) {
            locked = false;
        }
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        if (distance < 100) {
            inRange = true;
        } else {
            inRange = false;
        }
    }

    public boolean intersects(Double playerX, double playerY) {
        return playerX < x + 100 && playerX + 50 > x && playerY < y + 100 && playerY + 50 > y;
    }

}
