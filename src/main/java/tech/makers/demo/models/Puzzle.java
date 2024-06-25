package tech.makers.demo.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.application.Platform;
import tech.makers.demo.models.sound.Sound;

import java.util.List;
import java.util.Optional;

public class Puzzle {
    double x; // X-coordinate of the puzzle's position
    double y; // Y-coordinate of the puzzle's position
    String question; // Question for the puzzle
    String answer; // Answer for the puzzle
    List<String> options; // Multiple-choice options
    public boolean solved; // Flag to indicate if the puzzle is solved
    public boolean interacting; // Flag to indicate if the player is currently interacting with the puzzle
    public boolean inRange; // Flag to indicate if the player is within range of the puzzle
    public Image image; // Image representing the puzzle
    Sound sound = new Sound(); // Sound object for managing puzzle sounds
    private boolean solvedSoundPlayed = false; // Flag to indicate if the solved sound has been played

    // Constructor to initialize the position, question, answer, options, and image of the puzzle
    public Puzzle(double x, double y, String question, String answer, List<String> options, String imagePath) {
        this.x = x; // Set the initial X-coordinate
        this.y = y; // Set the initial Y-coordinate
        this.question = question; // Set the puzzle question
        this.answer = answer; // Set the puzzle answer
        this.options = options; // Set the multiple-choice options
        this.solved = false; // Initialize the puzzle as unsolved
        this.interacting = false; // Initialize interacting flag as false
        this.inRange = false; // Initialize inRange flag as false
        this.image = new Image(getClass().getResource(imagePath).toExternalForm()); // Load the puzzle image
    }

    // Method to render the puzzle on the screen
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, 96, 144); // Draw the puzzle image at the specified position with size 48x48
    }

    // Method that handles the interaction between player and puzzle
    public void interact() {
        if (inRange && !solved && !interacting) { // Check if the puzzle is in range, unsolved, and not currently interacting
            interacting = true; // Set interacting flag to true to prevent multiple interactions
            // Play sound effect
            sound.setFile(2);
            sound.setVolume(-25.0f);
            sound.play();
            Platform.runLater(() -> { // Run the following code on the JavaFX thread
                ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options); // Create a new choice dialog
                dialog.setTitle("Puzzle"); // Set the dialog title
                dialog.setHeaderText(question); // Set the dialog header text to the puzzle question
                dialog.setContentText("Choose your answer:"); // Set the dialog content text

                Optional<String> result = dialog.showAndWait(); // Show the dialog and wait for the user's input
                result.ifPresent(answerText -> { // Process the user's input
                    if (answerText.equals(answer) && !solvedSoundPlayed) { // Check if the input matches the answer
                        solved = true; // Set the puzzle as solved
                        System.out.println("Puzzle solved!"); // Print a message indicating the puzzle is solved
                        sound.setFile(14);
                        sound.setVolume(-30.0f); // Set volume as needed
                        sound.play();
                        solvedSoundPlayed = true;
                    } else {
                        showIncorrectMessage(); // Show an incorrect answer message
                    }
                });
                interacting = false; // Reset the interacting flag
            });
        }
    }

    // Method to show an incorrect answer message
    public void showIncorrectMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create a new information alert
        alert.setTitle("Incorrect!"); // Set the alert title
        sound.setFile(15);
        sound.setVolume(-10.0f); // Set volume as needed
        sound.play();
        alert.setHeaderText(null); // Set the alert header text to null
        alert.setContentText("YOU SUCK"); // Set the alert content text
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    // Method to check if the player is within range of the puzzle
    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2)); // Calculate the distance between player and puzzle
        inRange = distance < 70; // Set the inRange flag if the distance is less than 70
    }

    // Method to check if the puzzle is solved
    public boolean isSolved() {
        return solved;
    }

    // Method to check if the player intersects with the puzzle
    public boolean intersects(double playerX, double playerY) {
        boolean collision = playerX < x + 48 && playerX + 48 > x && playerY < y + 48 && playerY + 48 > y;
        System.out.println("Puzzle intersects at " + playerX + ", " + playerY + ": " + collision);
        return collision;
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

    // Getter method for the multiple-choice options
    public List<String> getOptions() {
        return options; // Return the multiple-choice options
    }
}