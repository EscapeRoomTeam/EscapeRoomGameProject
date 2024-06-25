package tech.makers.demo.models.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import tech.makers.demo.models.Player;

import java.util.Optional;

public class Safe {
    private final double x;
    private final double y;
    private final Image image;
    private boolean isUnlocked = false;
    private final String correctCode;
    private boolean inRange;

    public Safe(double x, double y, String imagePath, String correctCode) {
        this.x = x;
        this.y = y;
        this.correctCode = correctCode;
        this.inRange = false;

        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, 96, 144); // Adjust size as needed
    }

    public void interact(Player player) {
        if (!isUnlocked) {
            String userInput = getUserInput("Safe", "Enter the code to unlock the safe:");
            if (correctCode.equals(userInput)) {
                isUnlocked = true;
                player.addItemToInventory("USB");
                showAlert("Safe", "The safe is now unlocked.");
            } else {
                showAlert("Safe", "Incorrect code. The safe remains locked.");
            }
        } else {
            showAlert("Safe", "The safe is already unlocked.");
        }
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        inRange = distance < 70;
        System.out.println("Player in range of safe: " + inRange + " (distance: " + distance + ")");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getUserInput(String title, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
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

    public boolean isUnlocked() {
        return isUnlocked;
    }
}