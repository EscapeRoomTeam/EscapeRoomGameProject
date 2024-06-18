package tech.makers.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tech.makers.demo.levels.Level;

public class EscapeRoomGame extends Application {
    private LevelManager levelManager;

    // Main method, starts the game
    public static void main(String[] args) {
        launch(args);
    }

    // Starts the game window
    @Override
    public void start(Stage primaryStage) {
        // Create the canvas/window
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        levelManager = new LevelManager(gc);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            Level currentLevel = levelManager.getCurrentLevel();
            Player player = currentLevel.getPlayer();
            Puzzle puzzle = currentLevel.getPuzzle();
            Door door = currentLevel.getDoor();

            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) player.moveUp(puzzle, door);
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) player.moveDown(puzzle, door);
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) player.moveLeft(puzzle, door);
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) player.moveRight(puzzle, door);
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
                levelManager.render();
                levelManager.update();
            }
        }.start();
    }
}

