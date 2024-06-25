package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

public class CrowdInteraction extends Interaction {

    private final double width;
    private final double height;
    private final double collisionHeight;

    public CrowdInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.width = 250;  // Define the width of the crowd image
        this.height = 250; // Define the height of the crowd image
        this.collisionHeight = 150; // Define the reduced height for collision
    }

    @Override
    public void interact() {
        showAlert();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height); // Draw the image at (x, y) with size 250x250
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("I'm excited to see what the students present!");
        alert.showAndWait();
    }

    public boolean collidesWith(double playerX, double playerY, double playerWidth, double playerHeight) {
        return playerX < x + width && playerX + playerWidth > x &&
                playerY < y + collisionHeight && playerY + playerHeight > y;
    }
}