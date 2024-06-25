package tech.makers.demo.levelManagement;

import javafx.scene.control.Alert;
import tech.makers.demo.assets.Sound;

public class MacbookInteraction extends Interaction {
    private boolean hasMacbook;
    Sound sound = new Sound();

    public MacbookInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.hasMacbook = false;
    }

    @Override
    public void interact() {
        if (!hasMacbook) {
            hasMacbook = true;
            sound.setFile(21);
            sound.setVolume(-10.0f);
            sound.play();
            showAlert("You picked up the Golden MacBook.");
        } else {
            showAlert("You already have the Golden MacBook.");
        }
    }

    public boolean hasMacbook() {
        return hasMacbook;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
