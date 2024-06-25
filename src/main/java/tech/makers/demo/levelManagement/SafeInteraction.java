package tech.makers.demo.levelManagement;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import tech.makers.demo.assets.Sound;

import java.util.Optional;

public class SafeInteraction extends Interaction {
    private EddieInteraction2 eddieInteraction;
    private boolean isOpen;
    Sound sound = new Sound();

    public SafeInteraction(double x, double y, String imagePath, EddieInteraction2 eddieInteraction) {
        super(x, y, imagePath);
        this.eddieInteraction = eddieInteraction;
        this.isOpen = false;
    }

    @Override
    public void interact() {
        if (!isOpen) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Safe Code");
            dialog.setHeaderText("Enter the code for the safe:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(code -> {
                if (code.equals(eddieInteraction.getSafeCode())) {
                    isOpen = true;
                    sound.setFile(22);
                    sound.setVolume(-17.0f);
                    sound.play();
                    showAlert("Safe opened! You found the USB drive.");
                } else {
                    sound.setFile(15);
                    sound.setVolume(-10.0f);
                    sound.play();
                    showAlert("Incorrect code. Try again.");

                }
            });
        } else {
            showAlert("The safe is already open.");
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
