package tech.makers.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.gui.HomeScreen;
import tech.makers.demo.levels.Level;
import tech.makers.demo.Tile.TileManager;
import tech.makers.demo.levels.LevelManager;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EscapeRoomGame extends Application {
    private LevelManager levelManager;
    private TileManager tileManager;
    private Player player;
    private Puzzle puzzle;
    private Door door;
    private Image[] moneyImages;
    private List<ImagePosition> moneyPositions;
    Sound sound = new Sound();
    private Random random = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HomeScreen homeScreen = new HomeScreen(primaryStage, this);
        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(homeScreen.getScene());
        primaryStage.show();
    }

    public void startGame(Stage primaryStage) {
        Canvas canvas = new Canvas(768, 576);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        levelManager = new LevelManager(gc);
        tileManager = new TileManager();

        player = new Player(100, 100);
        puzzle = new Puzzle(300, 300, "What is 2 + 2?", "4");
        door = new Door(600, 400);

        moneyImages = new Image[] {
                new Image(getClass().getResource("/sprites/money 1.png").toString()),
                new Image(getClass().getResource("/sprites/money 2.png").toString()),
                new Image(getClass().getResource("/sprites/money 3.png").toString())
        };

        moneyPositions = initializeMoneyPositions(canvas);

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

        scene.setOnKeyReleased(event -> {
            Level currentLevel = levelManager.getCurrentLevel();
            Player player = currentLevel.getPlayer();

            // Reset player state to idle when movement keys are released
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W ||
                    event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S ||
                    event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A ||
                    event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                player.stopMoving();
            }
        });

        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        sound.setFile(0);
        sound.setVolume(-10.0f);
        sound.play();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                tileManager.renderTiles(gc);
                levelManager.render();
                levelManager.update();
                renderMoney(gc);
            }
        }.start();
    }

    private List<ImagePosition> initializeMoneyPositions(Canvas canvas) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double tileSize = 48;
        List<ImagePosition> positions = new ArrayList<>();

        // Top and bottom edges
        for (int i = 0; i < canvasWidth; i += tileSize) {
            // Top edge
            positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], i, 0));
            // Bottom edge
            positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], i, canvasHeight - tileSize));
        }

        // Left and right edges
        for (int i = 0; i < canvasHeight; i += tileSize) {
            // Left edge
            positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], 0, i));
            // Right edge
            positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], canvasWidth - tileSize, i));
        }

        return positions;
    }

    private void renderMoney(GraphicsContext gc) {
        for (ImagePosition position : moneyPositions) {
            gc.drawImage(position.image, position.x, position.y, 48, 48);
        }
    }

    public void playMusic(int i) {
        System.out.println("Playing music track: " + i);
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

    private static class ImagePosition {
        Image image;
        double x;
        double y;

        ImagePosition(Image image, double x, double y) {
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }
}
