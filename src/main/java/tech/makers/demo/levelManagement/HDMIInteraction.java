package tech.makers.demo.levelManagement;

import javafx.scene.control.Alert;
import tech.makers.demo.assets.Door;

public class HDMIInteraction extends Interaction {
    private MacbookInteraction macbookInteraction;

    private CableInteraction cableInteraction;
    private boolean isPluggedIn;

    public HDMIInteraction(double x, double y, String imagePath, MacbookInteraction macbookInteraction, CableInteraction cableInteraction) {
        super(x, y, imagePath);
        this.macbookInteraction = macbookInteraction;
        this.cableInteraction = cableInteraction;
        this.isPluggedIn = false;
    }

    @Override
    public void interact() {
        if (macbookInteraction.hasMacbook() && cableInteraction.hasCable() && !isPluggedIn) {
            isPluggedIn = true;
            showAlert("You plugged the MacBook into the screen.");
            Door.unlock();
        } else if (!macbookInteraction.hasMacbook() || !cableInteraction.hasCable()) {
            showAlert("You need to get the MacBook and HDMI cable first.");
        } else {
            showAlert("The MacBook is already plugged in.");
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
