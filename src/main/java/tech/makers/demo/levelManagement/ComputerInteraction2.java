package tech.makers.demo.levelManagement; // Package declaration for level management

import javafx.scene.canvas.GraphicsContext; // Import GraphicsContext for rendering
import javafx.scene.control.Alert; // Import Alert for displaying alerts
import javafx.scene.control.TextInputDialog; // Import TextInputDialog for input dialogs
import tech.makers.demo.assets.Door; // Import Door class for door interaction

import java.util.Optional; // Import Optional for handling optional values
import javafx.scene.control.Alert;

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

    public void setHasUSB(boolean hasUSB) {
        this.hasUSB = hasUSB;
    }

    @Override
    public void interact() {
        if (!hasUSB) {
            showAlert("You need to find the USB drive to reinstall the operating system.");
        } else if (!osReinstalled) {
            osReinstalled = true;
            showAlert("Operating system reinstalled! You can now do the coding challenge.");
            Door.unlock(); // Unlock the door
            showAlert("Password correct! The door is now unlocked.");
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
