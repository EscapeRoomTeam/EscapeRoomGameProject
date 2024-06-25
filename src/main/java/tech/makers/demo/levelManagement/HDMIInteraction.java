package tech.makers.demo.levelManagement;

import javafx.scene.control.Alert;

public class HDMIInteraction extends Interaction {
    private MacbookInteraction macbookInteraction;
    private boolean isPluggedIn;

    public HDMIInteraction(double x, double y, String imagePath, MacbookInteraction macbookInteraction) {
        super(x, y, imagePath);
        this.macbookInteraction = macbookInteraction;
        this.isPluggedIn = false;
    }

    @Override
    public void interact() {
        if (macbookInteraction.hasMacbook() && !isPluggedIn) {
            isPluggedIn = true;
            showAlert("You plugged the MacBook into the screen.");
        } else if (!macbookInteraction.hasMacbook()) {
            showAlert("You need to get the MacBook first.");
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
