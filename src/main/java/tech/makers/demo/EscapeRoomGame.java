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

    private Player player;
    private Puzzle puzzle;
    private Door door;
    Sound sound = new Sound();
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
        // Starts music
        playMusic(0);

        // Create the player and puzzle
        player = new Player(100, 100);
        puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        door = new Door(600, 400);

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
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(int i) {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

}



