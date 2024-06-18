package tech.makers.demo;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.control.TextInputDialog;
import javafx.application.Platform;
import java.util.Optional;

public class Puzzle {
    private double x;
    private double y;
    private String question;
    private String answer;
    boolean solved;
    private boolean interacting;
    boolean inRange;
    Sound sound = new Sound();

    //Constructor to initialize the position, question, and answer of the puzzle
    public Puzzle(double x, double y, String question, String answer) {
        this.x = x;
        this.y = y;
        this.question = question;
        this.answer = answer;
        this.solved = false;
        this.interacting = false;
        this.inRange = false;
    }

    //Method to render the puzzle on the screen
    public void render(GraphicsContext gc) {
        gc.setFill(solved ? Color.GREEN : Color.RED);
        gc.fillRect(x, y, 50, 50);
    }

    //method that handles the interaction between player and puzzle
    public void interact() {
        if (inRange && !solved && !interacting) {  //checks if puzzle is not solved and player is not interacting
            interacting = true; //sets flag to true to stop puzzle spamming
            // Plays sfx
            sound.setFile(2);
            sound.play();
            Platform.runLater(() -> { //run the following code on the JavaFX thread
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Puzzle");
                dialog.setHeaderText(question);
                dialog.setContentText("Answer:");

                Optional<String> result = dialog.showAndWait(); //show the dialog and wait for the user's answer
                result.ifPresent(answerText -> {
                    if (answerText.equals(answer)) {
                        solved = true;
                        System.out.println("Puzzle solved!");
                    } else {
                        showIncorrectMessage();


                    }
                });
                interacting = false;
            });
        }
    }

    private void showIncorrectMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect!");
        alert.setHeaderText(null);
        alert.setContentText("YOU SUCK");
        alert.showAndWait();
    }

    public void checkPlayerInRange(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        if (distance < 70) {
            inRange = true;
        } else {
            inRange = false;

        }
    }

    //Getter method to check if the puzzle is solved
    public boolean isSolved() {
        return solved;
    }

    //method to check if the player intersects with the puzzle
    public boolean intersects(double playerX, double playerY) {
        return playerX < x + 50 && playerX + 50 > x && playerY < y + 50 && playerY + 50 > y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }


}



