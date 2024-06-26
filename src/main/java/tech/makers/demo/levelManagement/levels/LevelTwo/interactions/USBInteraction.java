package tech.makers.demo.levelManagement.levels.LevelTwo.interactions;

import javafx.scene.control.Alert;
import tech.makers.demo.levelManagement.Interaction;

public class USBInteraction extends Interaction {
    private ComputerInteraction2 computerInteraction2;
    private boolean hasUSB;

    public USBInteraction(double x, double y, String imagePath, ComputerInteraction2 computerInteraction2) {
        super(x, y, imagePath);
        this.computerInteraction2 = computerInteraction2;
        this.hasUSB = false;
    }

    @Override
    public void interact() {
        if (!hasUSB) {
            hasUSB = true;
            computerInteraction2.setHasUSB(true);
            showAlert("You picked up the USB drive.");
        } else {
            showAlert("You already have the USB drive.");
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
