package tech.makers.demo.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;

public class Player {
    private double x; // X-coordinate of the player's position
    private double y; // Y-coordinate of the player's position
    private Image spriteSheet; // Sprite sheet containing the player's images
    private Image bobUp; // Image for the player facing up
    private Image bobDown; // Image for the player facing down
    private Image bobLeft; // Image for the player facing left
    private Image bobRight; // Image for the player facing right
    private String direction = "down"; // Default direction the player is facing
    Sound sound = new Sound(); // Sound object for managing player sounds

    // Player constructor, sets the player's starting position
    public Player(double x, double y) {
        this.x = x; // Set the initial X-coordinate
        this.y = y; // Set the initial Y-coordinate
        this.spriteSheet = new Image(getClass().getResource("/sprites/Bob_idle_16x16.png").toString()); // Load the sprite sheet image
        // Extract sub-images for each direction from the sprite sheet
        this.bobRight = new WritableImage(spriteSheet.getPixelReader(), 0, 0, 16, 32); // Image for the player facing right
        this.bobUp = new WritableImage(spriteSheet.getPixelReader(), 16, 0, 16, 32); // Image for the player facing up
        this.bobLeft = new WritableImage(spriteSheet.getPixelReader(), 32, 0, 16, 32); // Image for the player facing left
        this.bobDown = new WritableImage(spriteSheet.getPixelReader(), 48, 0, 16, 32); // Image for the player facing down
    }

    // Method to move the player up
    public void moveUp(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x, y - 20, puzzle) && !checkDoorCollision(x, y - 20, door)) {
            y -= 20; // Move the player up by 20 units
            direction = "up"; // Set the direction to up
            // sound.setFile(1);
            // sound.play();
        }
    }

    // Method to move the player down
    public void moveDown(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x, y + 20, puzzle) && !checkDoorCollision(x, y + 20, door)) {
            y += 20; // Move the player down by 20 units
            direction = "down"; // Set the direction to down
        }
    }

    // Method to move the player left
    public void moveLeft(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x - 20, y, puzzle) && !checkDoorCollision(x - 20, y, door)) {
            x -= 20; // Move the player left by 20 units
            direction = "left"; // Set the direction to left
        }
    }

    // Method to move the player right
    public void moveRight(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x + 20, y, puzzle) && !checkDoorCollision(x + 20, y, door)) {
            x += 20; // Move the player right by 20 units
            direction = "right"; // Set the direction to right
        }
    }

    // Method to check for collision with a puzzle
    public boolean checkPuzzleCollision(double newX, double newY, Puzzle puzzle) {
        return puzzle.intersects(newX, newY); // Return true if the player intersects with the puzzle at the new position
    }

    // Method to check for collision with a door
    public boolean checkDoorCollision(double newX, double newY, Door door) {
        return door.intersects(newX, newY); // Return true if the player intersects with the door at the new position
    }

    // Method to render the player on the screen
    public void render(GraphicsContext gc) {
        Image currentImage; // Image to be used for rendering
        // Select the correct image based on the player's direction
        switch (direction) {
            case "up":
                currentImage = bobUp; // Image for facing up
                break;
            case "down":
                currentImage = bobDown; // Image for facing down
                break;
            case "left":
                currentImage = bobLeft; // Image for facing left
                break;
            case "right":
                currentImage = bobRight; // Image for facing right
                break;
            default:
                currentImage = bobDown; // Default image
        }
        gc.drawImage(currentImage, x, y, 50, 50); // Draw the image at the player's position with a size of 50x50 pixels
    }

    // Getter for the player's X-coordinate
    public double getX() {
        return x; // Return the current X-coordinate of the player
    }

    // Getter for the player's Y-coordinate
    public double getY() {
        return y; // Return the current Y-coordinate of the player
    }
}
