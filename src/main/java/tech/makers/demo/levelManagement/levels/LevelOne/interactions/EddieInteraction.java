package tech.makers.demo.levelManagement.levels.LevelOne.interactions; // Package declaration for level management

import javafx.scene.canvas.GraphicsContext; // Import GraphicsContext for rendering
import javafx.scene.control.Alert; // Import Alert for displaying alerts
import javafx.scene.image.Image; // Import Image class for handling images
import javafx.scene.image.WritableImage; // Import WritableImage for image manipulation
import tech.makers.demo.levelManagement.Interaction;

public class EddieInteraction extends Interaction { // EddieInteraction class extending Interaction
    public ComputerInteraction computerInteraction; // Private ComputerInteraction object
    private final String defaultMessage = "Hi there! You need to turn on the router to start your PC!."; // Final default message
    public Image idleSpriteSheet; // Private Image for the sprite sheet
    public Image[] idleRightFrames; // Array of Images for idle right frames
    public Image[] idleUpFrames; // Array of Images for idle up frames
    public Image[] idleLeftFrames; // Array of Images for idle left frames
    public Image[] idleDownFrames; // Array of Images for idle down frames
    public String direction = "down"; // String to keep track of direction
    public int currentFrame = 0; // Integer to keep track of current frame
    private long lastFrameTime = 0; // Long to keep track of last frame time
    public static final int FRAME_DURATION = 150; // Frame duration in milliseconds

    public EddieInteraction(double x, double y, String imagePath, ComputerInteraction computerInteraction) { // Constructor for EddieInteraction
        super(x, y, imagePath); // Call to super class constructor
        this.computerInteraction = computerInteraction; // Initialize computerInteraction

        // Load frames for the idle animation
        this.idleSpriteSheet = new Image(getClass().getResource(imagePath).toExternalForm()); // Load sprite sheet
        this.idleRightFrames = loadFrames(idleSpriteSheet, 0); // Load idle right frames
        this.idleUpFrames = loadFrames(idleSpriteSheet, 6); // Load idle up frames
        this.idleLeftFrames = loadFrames(idleSpriteSheet, 12); // Load idle left frames
        this.idleDownFrames = loadFrames(idleSpriteSheet, 18); // Load idle down frames
    }

    private Image[] loadFrames(Image spriteSheet, int startFrame) { // Method to load frames from sprite sheet
        Image[] frames = new Image[6]; // Create array for frames
        int spriteWidth = 16; // Width of each sprite
        int spriteHeight = 32; // Height of each sprite
        for (int i = 0; i < 6; i++) { // Loop to load each frame
            int x = (startFrame + i) * spriteWidth; // Calculate x position of the frame
            int y = 0; // y position is 0 since the image is a single row
            frames[i] = new WritableImage(spriteSheet.getPixelReader(), x, y, spriteWidth, spriteHeight); // Create new WritableImage for each frame
        }
        return frames; // Return the array of frames
    }

    @Override
    public void render(GraphicsContext gc) { // Override render method
        Image[] currentFrames; // Array to hold current frames
        switch (direction) { // Switch case to select frames based on direction
            case "up":
                currentFrames = idleUpFrames;
                break;
            case "down":
                currentFrames = idleDownFrames;
                break;
            case "left":
                currentFrames = idleLeftFrames;
                break;
            case "right":
                currentFrames = idleRightFrames;
                break;
            default:
                currentFrames = idleDownFrames;
        }
        Image currentImage = currentFrames[currentFrame]; // Get current frame image
        gc.drawImage(currentImage, getX(), getY(), 25, 50); // Draw the image at (x, y) with size 25x50
    }

    public void update() { // Method to update the frame
        long currentTime = System.currentTimeMillis(); // Get current time
        if (currentTime - lastFrameTime >= FRAME_DURATION) { // Check if it's time to update frame
            currentFrame = (currentFrame + 1) % 6; // Update to next frame
            lastFrameTime = currentTime; // Update last frame time
        }
    }

    @Override
    public void interact() { // Override interact method
        if (computerInteraction.isRouterOn()) { // Check if router is on
            showAlert("Eddie: Here's the Wi-Fi password you need: " + computerInteraction.getWifiPassword()); // Show Wi-Fi password alert
        } else { // If router is off
            showAlert(defaultMessage); // Show default message alert
        }
    }

    private void showAlert(String message) { // Private method to show alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create a new Alert of type INFORMATION
        alert.setTitle("Tip"); // Set alert title
        alert.setHeaderText(null); // Set alert header text to null
        alert.setContentText(message); // Set alert content text
        alert.showAndWait(); // Show alert and wait
    }
}
