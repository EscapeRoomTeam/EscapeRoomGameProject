package tech.makers.demo.levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.application.Platform;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.player.Player;

import java.util.Optional;

public class Puzzle {
    double x; // X-coordinate of the puzzle's position
    double y; // Y-coordinate of the puzzle's position
    String question; // Question for the puzzle
    String answer; // Answer for the puzzle
    boolean solved; // Flag to indicate if the puzzle is solved
    boolean interacting; // Flag to indicate if the player is currently interacting with the puzzle
    boolean inRange; // Flag to indicate if the player is within range of the puzzle
    Image image; // Image representing the puzzle
    Sound sound = new Sound(); // Sound object for managing puzzle sounds

    // Constructor to initialize the position, question, and answer of the puzzle
    public Puzzle(double x, double y, String question, String answer) {
        this.x = x; // Set the initial X-coordinate
        this.y = y; // Set the initial Y-coordinate
        this.question = question; // Set the puzzle question
        this.answer = answer; // Set the puzzle answer
        this.solved = false; // Initialize the puzzle as unsolved
        this.interacting = false; // Initialize interacting flag as false
        this.inRange = false; // Initialize inRange flag as false
        this.image = new Image(getClass().getResource("/sprites/computer.png").toExternalForm()); // Load the puzzle image
    }

    // Method to render the puzzle on the screen
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, 48, 48); // Draw the puzzle image at the specified position with size 48x48
    }

    // Method that handles the interaction between player and puzzle
    public void interact() {
        if (inRange && !solved && !interacting) { // Check if the puzzle is in range, unsolved, and not currently interacting
            interacting = true; // Set interacting flag to true to prevent multiple interactions
            // Play sound effect
            sound.setFile(2);
            sound.play();
            Platform.runLater(() -> { // Run the following code on the JavaFX thread
                TextInputDialog dialog = new TextInputDialog(); // Create a new input dialog
                dialog.setTitle("Puzzle"); // Set the dialog title
                dialog.setHeaderText(question); // Set the dialog header text to the puzzle question
                dialog.setContentText("Answer:"); // Set the dialog content text

                Optional<String> result = dialog.showAndWait(); // Show the dialog and wait for the user's input
                result.ifPresent(answerText -> { // Process the user's input
                    if (answerText.equals(answer)) { // Check if the input matches the answer
                        solved = true; // Set the puzzle as solved
                        System.out.println("Puzzle solved!"); // Print a message indicating the puzzle is solved
                    } else {
                        showIncorrectMessage(); // Show an incorrect answer message
                    }
                });
                interacting = false; // Reset the interacting flag
            });
        }
    }

    // Method to show an incorrect answer message
    private void showIncorrectMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create a new information alert
        alert.setTitle("Incorrect!"); // Set the alert title
        alert.setHeaderText(null); // Set the alert header text to null
        alert.setContentText("YOU SUCK"); // Set the alert content text
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    // Method to check if the player is within range of the puzzle
    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)); // Calculate the distance between player and puzzle
        inRange = distance < 70; // Set the inRange flag if the distance is less than 70
    }

    // Getter method to check if the puzzle is solved
    public boolean isSolved() {
        return solved; // Return the solved status
    }

    // Method to check if the player intersects with the puzzle
    public boolean intersects(double playerX, double playerY) {
        // Return true if the player's position intersects with the puzzle's position
        return playerX < x + 48 && playerX + 48 > x && playerY < y + 48 && playerY + 48 > y;
    }

    // Getter method for the X-coordinate
    public double getX() {
        return x; // Return the X-coordinate
    }

    // Getter method for the Y-coordinate
    public double getY() {
        return y; // Return the Y-coordinate
    }

    // Getter method for the puzzle question
    public String getQuestion() {
        return question; // Return the puzzle question
    }

    // Getter method for the puzzle answer
    public String getAnswer() {
        return answer; // Return the puzzle answer
    }
}
