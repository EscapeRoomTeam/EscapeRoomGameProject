package tech.makers.demo.levelManagement; // Package declaration for level management

import javafx.scene.canvas.GraphicsContext; // Import GraphicsContext for rendering
import javafx.scene.image.Image; // Import Image class for handling images
import tech.makers.demo.player.Player; // Import Player class for player interaction

public abstract class Interaction { // Abstract class Interaction
    protected double x; // Protected variable for x coordinate
    protected double y; // Protected variable for y coordinate
    protected Image image; // Protected variable for the image
    protected boolean inRange; // Protected boolean to check if in range

    public Interaction(double x, double y, String imagePath) { // Constructor for Interaction class
        this.x = x; // Initialize x coordinate
        this.y = y; // Initialize y coordinate
        this.image = new Image(getClass().getResource(imagePath).toExternalForm()); // Load image from path
        this.inRange = false; // Initialize inRange to false
    }

    public void render(GraphicsContext gc) { // Method to render the image
        gc.drawImage(image, x, y, 48, 48); // Draw the image at (x, y) with size 48x48
    }

    public void checkPlayerInRange(Player player) { // Method to check if player is in range
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)); // Calculate distance between player and interaction
        inRange = distance < 70; // Set inRange to true if distance is less than 70
    }

    public abstract void interact(); // Abstract method interact to be implemented by subclasses

    public boolean isInRange() { // Method to check if interaction is in range
        return inRange; // Return the value of inRange
    }

    // Getter method for x coordinate
    public double getX() {
        return x; // Return the x coordinate
    }

    // Getter method for y coordinate
    public double getY() {
        return y; // Return the y coordinate
    }
}
