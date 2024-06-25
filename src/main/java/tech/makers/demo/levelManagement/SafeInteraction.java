package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class SafeInteraction extends Interaction {
    private EddieInteraction2 eddieInteraction;
    private ComputerInteraction2 computerInteraction2;
    private boolean isOpen;

    public SafeInteraction(double x, double y, String imagePath, EddieInteraction2 eddieInteraction, ComputerInteraction2 computerInteraction2) {
        super(x, y, imagePath);
        this.eddieInteraction = eddieInteraction;
        this.computerInteraction2 = computerInteraction2;
        this.isOpen = false;
    }

    @Override
    public void render(GraphicsContext gc) { // Override render method
        gc.drawImage(image, x, y, 48, 96); // Draw the image at (x, y) with size 96x144
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
                    computerInteraction2.setHasUSB(true); // Set the USB status in ComputerInteraction2
                    showAlert("Safe opened! You found the USB drive.");
                } else {
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
