package tech.makers.demo.assets;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import tech.makers.demo.levelManagement.LevelManager;
import tech.makers.demo.player.Player;

public class Door {
    private final double x;
    private final double y;
    public static boolean locked;
    public boolean inRange;
    public Image doorImage;
    public Sound sound = new Sound();

    public Door(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        this.locked = true;
        this.inRange = false;
        this.doorImage = new Image(getClass().getResource(imagePath).toExternalForm()); // Load the door image
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(doorImage, x, y, 96, 144); // Draw the door image at the specified position with size 96x144
    }

    public void interact(LevelManager levelManager) {
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

    public void showLockedMessage() {
        if (inRange) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Door Locked");
                sound.setFile(15);
                sound.setVolume(-10.0f); // Set volume as needed
                sound.play();
                alert.setHeaderText(null);
                alert.setContentText("The door is locked until the puzzle is completed.");
                alert.showAndWait();

            });
        }
    }
//
//    public void checkUnlock(List<Puzzle> Puzzles) {
//        if (Puzzles.isSolved()) {
//            locked = false;
//        }
//    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 100;
    }

    public boolean intersects(double playerX, double playerY) {
        double doorWidth = 96;
        double doorHeight = 144;
        double playerWidth = 48;
        double playerHeight = 48;

        boolean collision = playerX < x + doorWidth && playerX + playerWidth > x && playerY < y + doorHeight && playerY + playerHeight > y;
        System.out.println("Door intersects at " + playerX + ", " + playerY + ": " + collision);
        return collision;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isInRange() {
        return inRange;
    }

    public static boolean unlock(){
       return locked = false;
    }
    public static boolean lock(){
        return locked = true;
    }
    public boolean isOpen() {
        return !locked;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


}
