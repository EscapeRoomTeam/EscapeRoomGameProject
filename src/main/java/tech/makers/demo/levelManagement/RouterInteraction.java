package tech.makers.demo.levelManagement; // Package declaration for level management

import javafx.scene.canvas.GraphicsContext; // Import GraphicsContext for rendering
import javafx.scene.control.ChoiceDialog; // Import ChoiceDialog for displaying choices
import tech.makers.demo.assets.Sound;

import java.util.Arrays; // Import Arrays for array manipulation
import java.util.Optional; // Import Optional for handling optional values

public class RouterInteraction extends Interaction { // RouterInteraction class extending Interaction
    private ComputerInteraction computer; // Private ComputerInteraction object
    Sound sound = new Sound();

    public RouterInteraction(double x, double y, String imagePath, ComputerInteraction computer) { // Constructor for RouterInteraction
        super(x, y, imagePath); // Call to super class constructor
        this.computer = computer; // Initialize computerInteraction
    }

    @Override
    public void render(GraphicsContext gc) { // Override render method
        gc.drawImage(image, x, y, 128, 128); // Draw the image at (x, y) with size 128x128
    }

    @Override
    public void interact() { // Override interact method
        ChoiceDialog<String> dialog = new ChoiceDialog<>("on", Arrays.asList("on", "off")); // Create a new ChoiceDialog with options "on" and "off"
        dialog.setTitle("Router Control"); // Set dialog title
        sound.setFile(21);
        sound.setVolume(-10.0f); // Set volume as needed
        sound.play();
        dialog.setHeaderText("Would you like to turn the router on or off?"); // Set dialog header text
        Optional<String> result = dialog.showAndWait(); // Show dialog and wait for result
        result.ifPresent(choice -> { // If a choice is present
            if (choice.equals("on")) { // Check if the choice is "on"
                sound.setFile(20);
                sound.setVolume(-2.0f); // Set volume as needed
                sound.play();
                computer.setRouterOn(true); // Set router on in computerInteraction
                System.out.println("The router is now on. The computer is also on."); // Print confirmation message
            } else { // If the choice is not "on"
                computer.setRouterOn(false); // Set router off in computerInteraction
                System.out.println("The router is now off. The computer is also off."); // Print confirmation message
            }
        });
    }
}
