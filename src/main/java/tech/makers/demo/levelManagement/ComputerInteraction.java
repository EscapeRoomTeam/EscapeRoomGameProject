package tech.makers.demo.levelManagement; // Package declaration for level management

import javafx.scene.canvas.GraphicsContext; // Import GraphicsContext for rendering
import javafx.scene.control.Alert; // Import Alert for displaying alerts
import javafx.scene.control.TextInputDialog; // Import TextInputDialog for input dialogs
import tech.makers.demo.assets.Door; // Import Door class for door interaction
import tech.makers.demo.assets.Sound;

import java.util.Optional; // Import Optional for handling optional values

public class ComputerInteraction extends Interaction { // ComputerInteraction class extending Interaction
    private boolean isRouterOn; // Private boolean to check if router is on
    private boolean hasPassword; // Private boolean to check if password has been entered
    private final String wifiPassword = "escape123"; // Final string for Wi-Fi password
    private Door door; // Private Door object
    Sound sound = new Sound();


    public ComputerInteraction(double x, double y, String imagePath, Door door) { // Constructor for ComputerInteraction
        super(x, y, imagePath); // Call to super class constructor
        this.door = door; // Initialize door
        this.isRouterOn = false; // Initialize isRouterOn to false
        this.hasPassword = false; // Initialize hasPassword to false
    }

    @Override
    public void render(GraphicsContext gc) { // Override render method
        gc.drawImage(image, x, y, 96, 144); // Draw the image at (x, y) with size 96x144
    }

    public void setRouterOn(boolean routerOn) { // Method to set router status
        isRouterOn = routerOn; // Set isRouterOn
    }

    public boolean isRouterOn() { // Method to check if router is on
        return isRouterOn; // Return the value of isRouterOn
    }

    public String getWifiPassword() { // Method to get Wi-Fi password
        return wifiPassword; // Return the Wi-Fi password
    }

    @Override
    public void interact() { // Override interact method
        if (!isRouterOn) { // Check if router is off
            sound.setFile(15);
            sound.setVolume(-10.0f); // Set volume as needed
            sound.play();
            showAlert("The WiFi is disconnected. Please connect to WiFi."); // Show alert to turn on the router
        } else if (!hasPassword) { // Check if password has not been entered
            sound.setFile(2);
            sound.setVolume(-25.0f);
            sound.play();
            TextInputDialog dialog = new TextInputDialog(); // Create a new TextInputDialog
            dialog.setTitle("Wi-Fi Password"); // Set dialog title
            dialog.setHeaderText("Enter the Wi-Fi password:"); // Set dialog header text
            Optional<String> result = dialog.showAndWait(); // Show dialog and wait for result
            result.ifPresent(password -> { // If password is present
                if (password.equals(wifiPassword)) { // Check if password is correct
                    hasPassword = true; // Set hasPassword to true
                    door.unlock(); // Unlock the door
                    sound.setFile(14);
                    sound.setVolume(-30.0f);
                    sound.play();

                    showAlert("Password correct! The door is now unlocked."); // Show success alert
                } else { // If password is incorrect
                    sound.setFile(15);
                    sound.setVolume(-10.0f); // Set volume as needed
                    sound.play();
                    showAlert("Incorrect password. Try again."); // Show error alert
                }
            });
        }
    }

    private void showAlert(String message) { // Private method to show alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create a new Alert of type INFORMATION
        alert.setTitle("Information"); // Set alert title
        alert.setHeaderText(null); // Set alert header text to null
        alert.setContentText(message); // Set alert content text
        alert.showAndWait(); // Show alert and wait
    }
}
