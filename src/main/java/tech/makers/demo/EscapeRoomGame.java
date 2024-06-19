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
import tech.makers.demo.Tile.TileManager;

public class EscapeRoomGame extends Application {
    private LevelManager levelManager; // Manages the different levels of the game
    private TileManager tileManager;   // Manages the tiles on the game board

    private Player player;  // The player character in the game
    private Puzzle puzzle;  // Puzzle object in the game
    private Door door;      // Door object in the game
    Sound sound = new Sound(); // Sound object for managing game sounds

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Set canvas size to match the full tile layout (768 width, 576 height)
        Canvas canvas = new Canvas(768, 576);
        GraphicsContext gc = canvas.getGraphicsContext2D(); // Get the graphics context for drawing

        levelManager = new LevelManager(gc); // Initialize the level manager with the graphics context
        tileManager = new TileManager();     // Initialize the tile manager

        player = new Player(100, 100);       // Initialize the player at position (100, 100)
        puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4"); // Initialize the puzzle with a question and answer
        door = new Door(600, 400);           // Initialize the door at position (600, 400)

        StackPane root = new StackPane();    // Create the root layout pane
        root.getChildren().add(canvas);      // Add the canvas to the root pane

        Scene scene = new Scene(root);       // Create the scene with the root pane
        // Set key press event handlers
        scene.setOnKeyPressed(event -> {
            Level currentLevel = levelManager.getCurrentLevel(); // Get the current level
            Player player = currentLevel.getPlayer();            // Get the player from the current level
            Puzzle puzzle = currentLevel.getPuzzle();            // Get the puzzle from the current level
            Door door = currentLevel.getDoor();                  // Get the door from the current level

            // Handle player movement and interactions based on key press
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) player.moveUp(puzzle, door);
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) player.moveDown(puzzle, door);
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) player.moveLeft(puzzle, door);
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) player.moveRight(puzzle, door);
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.E) {
                puzzle.interact();                          // Interact with the puzzle
                door.interact(puzzle, levelManager);       // Interact with the door
            }
        });

        primaryStage.setTitle("Escape Room Game"); // Set the window title
        primaryStage.setScene(scene);              // Set the scene to the stage
        primaryStage.show();                       // Show the stage

        playMusic(0); // Start playing background music

        // Start the game loop using an AnimationTimer
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas
                tileManager.renderTiles(gc); // Render the tiles on the canvas
                levelManager.render();       // Render the current level
                levelManager.update();       // Update the current level
            }
        }.start();
    }

    private void checkInteraction() {
        // Check if the player is interacting with the puzzle or door
        if (puzzle.intersects(player.getX(), player.getY())) {
            puzzle.checkPlayerInRange(player); // Check if the player is in range of the puzzle
        } else if (door.intersects(player.getX(), player.getY())) {
            if (door.inRange) {
                if (puzzle.isSolved()) {
                    door.checkPlayerInRange(player); // Check if the player is in range of the door and the puzzle is solved
                } else {
                    door.showLockedMessage(); // Show a locked message if the puzzle is not solved
                }
            }
        }
    }

    public void playMusic(int i) {
        System.out.println("Playing music track: " + i); // Print the track number being played
        sound.setFile(i); // Set the sound file to play
        sound.play();     // Play the sound
        sound.loop();     // Loop the sound
    }

    public void stopMusic(int i) {
        sound.stop(); // Stop the currently playing sound
    }

    public void playSE(int i) {
        sound.setFile(i); // Set the sound effect file to play
        sound.play();     // Play the sound effect
    }
}