package tech.makers.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EscapeRoomGame extends Application {
    private Player player;
    private Puzzle puzzle;
    private Door door;
    private LevelManager levelManager;

    // Main method, starts the game
    public static void main(String[] args) {
        launch(args);
    }

    //Starts the game window
    @Override
    public void start(Stage primaryStage) {
        // Create the canvas/window
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create the player and puzzle
        player = new Player(100, 100);
        puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        door = new Door(600, 400);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) player.moveUp(puzzle, door);
            if (event.getCode() == KeyCode.W) player.moveUp(puzzle, door);
            if (event.getCode() == KeyCode.DOWN) player.moveDown(puzzle, door);
            if (event.getCode() == KeyCode.S) player.moveDown(puzzle, door);
            if (event.getCode() == KeyCode.LEFT) player.moveLeft(puzzle, door);
            if (event.getCode() == KeyCode.A) player.moveLeft(puzzle, door);
            if (event.getCode() == KeyCode.RIGHT) player.moveRight(puzzle, door);
            if (event.getCode() == KeyCode.D) player.moveRight(puzzle, door);
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.E) {
                puzzle.interact();
                door.interact(puzzle, levelManager);
            }
        });

        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                player.render(gc);
                puzzle.render(gc);
                door.render(gc);
                puzzle.checkPlayerInRange(player);
                door.checkPlayerInRange(player);
                door.checkUnlock(puzzle);
            }
        }.start();
    }

    private void checkInteraction() {
        if (puzzle.intersects(player.getX(), player.getY())) {
            puzzle.checkPlayerInRange(player);
        } else if (door.intersects(player.getX(), player.getY())) {
            if (door.inRange) {
                if (puzzle.isSolved()) {
                    door.checkPlayerInRange(player);
                } else {
                    door.showLockedMessage();
                }
            }
        }
    }

}



