package tech.makers.demo.models.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import tech.makers.demo.models.Player;

public class Computer {
    private final double x;
    private final double y;
    private final Image image;
    private boolean hasNewOS = false;
    private boolean inRange;
    private boolean interacting;

    public Computer(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        this.inRange = false;
        this.interacting = false;
        this.hasNewOS = false;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, 96, 144); // Adjust size as needed
    }

    public void interact(Player player) {
        if (player.hasItemInInventory("USB")) {
            if (!hasNewOS) {
                installNewOS(player);
                hasNewOS = true;
            } else {
                showAlert("Computer", "The computer already has the new operating system installed.");
            }
        } else {
            showAlert("Computer", "You need a USB to install the new operating system.");
        }
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 70;
        System.out.println("Player in range of computer: " + inRange + " (distance: " + distance + ")");
    }

    private void installNewOS(Player player) {
        player.removeItemFromInventory("USB");
        hasNewOS = true;
        showAlert("Installation Complete", "The new operating system has been installed successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean intersects(double playerX, double playerY) {
        return playerX < x + 96 && playerX + 96 > x && playerY < y + 144 && playerY + 144 > y; // Adjust size as needed
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isInRange() {
        return inRange;
    }

    public boolean hasNewOS() {
        return hasNewOS;
    }
}