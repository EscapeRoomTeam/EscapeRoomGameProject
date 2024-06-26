package tech.makers.demo.levelManagement.levels.LevelThree.interactions;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.levelManagement.Interaction;

public class CableInteraction extends Interaction {

    private boolean hasCable;

    public CableInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.hasCable = false;
    }

    @Override
    public void interact() {
        if (!hasCable) {
            hasCable = true;
            showAlert("You picked up the HDMI cable.");
        } else {
            showAlert("You already picked up the HDMI cable.");
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!hasCable) {
            gc.drawImage(image, x, y, 15, 15); // Draw the image only if the cable has not been picked up
        }
    }

    public boolean hasCable() {
        return hasCable;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}