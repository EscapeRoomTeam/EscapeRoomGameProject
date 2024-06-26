package tech.makers.demo.levelManagement.levels.LevelTwo.interactions;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.Interaction;

public class ComputerInteraction2 extends Interaction {
    private boolean hasUSB;
    private boolean osReinstalled;
    private Door door;
    Sound sound = new Sound();

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
            sound.setFile(15);
            sound.setVolume(-10.0f);
            sound.play();
            showAlert("You need to find the USB drive to reinstall the operating system.");
        } else if (!osReinstalled) {
            osReinstalled = true;
            sound.setFile(2);
            sound.setVolume(-25.0f);
            sound.play();
            showAlert("Operating system reinstalled! You can now do the coding challenge.");
            Door.unlock();
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
