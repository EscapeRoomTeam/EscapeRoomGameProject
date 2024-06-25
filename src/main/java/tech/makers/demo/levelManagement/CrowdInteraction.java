package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class CrowdInteraction extends Interaction {

    private final double width;
    private final double height;

    public CrowdInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.width = 250;  // Define the width of the crowd image
        this.height = 150; // Define the height of the crowd image
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
                playerY < y + height && playerY + playerHeight > y;
    }
}