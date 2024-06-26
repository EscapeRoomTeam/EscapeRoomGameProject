package tech.makers.demo.levelManagement.levels.LevelThree.interactions;

import javafx.scene.control.Alert;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.levelManagement.Interaction;

public class HDMIInteraction extends Interaction {
    private MacbookInteraction macbookInteraction;
    Sound sound = new Sound();

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
            sound.setFile(20);
            sound.setVolume(-2.0f); // Set volume as needed
            sound.play();
            showAlert("You plugged the MacBook into the screen.");
            Door.unlock();
        } else if (!macbookInteraction.hasMacbook() || !cableInteraction.hasCable()) {
            sound.setFile(15);
            sound.setVolume(-10.0f);
            sound.play();
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
