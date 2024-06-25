package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.assets.Door;

public class ComputerInteraction2 extends Interaction {
    private boolean hasUSB;
    private boolean osReinstalled;
    private Door door;

    public ComputerInteraction2(double x, double y, String imagePath, Door door) {
        super(x, y, imagePath);
        this.door = door;
        this.hasUSB = false;
        this.osReinstalled = false;
    }

    @Override
    public void render(GraphicsContext gc) { // Override render method
        gc.drawImage(image, x, y, 96, 144); // Draw the image at (x, y) with size 96x144
    }

    public void setHasUSB(boolean hasUSB) {
        this.hasUSB = hasUSB;
    }

    @Override
    public void interact() {
        if (!hasUSB) {
            showAlert("You need to find the USB drive to reinstall the operating system.");
        } else if (!osReinstalled) {
            osReinstalled = true;
            showAlert("Operating system reinstalled! You can pass into the final room!");
            Door.unlock(); // Unlock the door when the OS is reinstalled
        } else {
            showAlert("You have already reinstalled the operating system and completed the challenge.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
