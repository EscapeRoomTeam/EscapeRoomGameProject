package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

public class CrowdInteraction extends Interaction {

    public CrowdInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
    }

    @Override
    public void interact() {
        showAlert();
    }
    @Override
    public void render(GraphicsContext gc) { // Override render method
        gc.drawImage(image, x, y, 250, 250); // Draw the image at (x, y) with size 96x144
    }
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("I'm excited to see what the students present!");
        alert.showAndWait();
    }
}