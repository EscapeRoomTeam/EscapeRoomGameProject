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
import tech.makers.demo.assets.Eddie;
import tech.makers.demo.assets.Obstacle;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.gui.HomeScreen;
import tech.makers.demo.gui.LevelCompletionScreen;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Level;
import tech.makers.demo.levelManagement.LevelManager;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.player.Player;
import tech.makers.demo.Tile.TileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EscapeRoomGame extends Application {
    private LevelManager levelManager;
    private TileManager tileManager;
    private Image[] moneyImages;
    private List<ImagePosition> objectPositions;
    private Sound sound = new Sound();
    private Random random = new Random();
    private Stage primaryStage;
    private AnimationTimer gameLoop;
    private Canvas canvas;
    private GraphicsContext gc;
    private List<Obstacle> obstacles;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        HomeScreen homeScreen = new HomeScreen(primaryStage, this);
        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(homeScreen.getScene());
        primaryStage.show();
    }

    public void startGame(Stage primaryStage) {
        canvas = new Canvas(768, 576);
        gc = canvas.getGraphicsContext2D();

        levelManager = new LevelManager(gc, this);

        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(200, 200, 100, 100, "sprites/Plant 1.png"));

        // Initialize the images before loading the level
        moneyImages = new Image[]{
                new Image(getClass().getResource("/sprites/money 1.png").toString()),
                new Image(getClass().getResource("/sprites/money 2.png").toString()),
                new Image(getClass().getResource("/sprites/money 3.png").toString())
        };

        loadLevel(1); // Start with level 1

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        setSceneControls(scene);

        primaryStage.setTitle("Escape Room Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        sound.setFile(0);
        sound.setVolume(-10.0f);
        sound.play();
        sound.loop();

        startGameLoop();
    }

    private void setSceneControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            Level currentLevel = levelManager.getCurrentLevel();
            Player player = currentLevel.getPlayer();
            List<Puzzle> puzzles = currentLevel.getPuzzles();
            Door door = currentLevel.getDoor();
            Eddie helperCharacter = currentLevel.getHelperCharacter();
            List<Interaction> interactions = currentLevel.getInteractions();

            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) player.moveUp(puzzles, door, obstacles);
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) player.moveDown(puzzles, door, obstacles);
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) player.moveLeft(puzzles, door, obstacles);
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) player.moveRight(puzzles, door, obstacles);
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.E) {
                if (door.isInRange() && !door.isLocked()) {
                    door.interact(levelManager);
                    currentLevel.isCompleted();
                } else {
                    for (Puzzle puzzle : puzzles) {
                        puzzle.interact();
                        }
                    }
                    for (Interaction interaction : interactions) {
                        if (interaction.isInRange()) {
                            interaction.interact();
                        }
                    }
                    if (helperCharacter.isInRange(player.getX(), player.getY())) {
                        helperCharacter.interact();
                    }
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
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                tileManager.renderTiles(gc);
                levelManager.render();
                levelManager.update();
                renderObjects(gc);
                renderObstacles(gc);
            }
        };
        gameLoop.start();
    }

    private List<ImagePosition> initializeObjectPositions(Canvas canvas, int levelNumber) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double tileSize = 48;
        List<ImagePosition> positions = new ArrayList<>();

        if (levelNumber == 1) {
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
        } else if (levelNumber == 2) {
            // Top row (ChairDown)
            for (int i = 0; i < canvasWidth; i += tileSize) {
                positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], i, 0));
            }

            // Bottom row (ChairUp)
            for (int i = 0; i < canvasWidth; i += tileSize) {
                positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], i, canvasHeight - tileSize));
            }

            // Left column (ChairRight)
            for (int i = 0; i < canvasHeight; i += tileSize) {
                positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], 0, i));
            }

            // Right column (ChairLeft)
            for (int i = 0; i < canvasHeight; i += tileSize) {
                positions.add(new ImagePosition(moneyImages[random.nextInt(moneyImages.length)], canvasWidth - tileSize, i));
            }
        }

        return positions;
    }

    private void renderObjects(GraphicsContext gc) {
        for (ImagePosition position : objectPositions) {
            gc.drawImage(position.image, position.x, position.y, 48, 48);
        }
    }

    private void renderObstacles(GraphicsContext gc) {
        for (Obstacle obstacle : obstacles) {
            obstacle.render(gc);
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

    public void completeLevel() {
        gameLoop.stop(); // Stop the game loop before showing the level completion screen
        showLevelCompletionScreen();
    }

    public void showLevelCompletionScreen() {
        LevelCompletionScreen levelCompletionScreen = new LevelCompletionScreen(primaryStage, this);
        levelCompletionScreen.show();
    }

    public void startNextLevel() {
        levelManager.loadNextLevel();
        loadLevel(levelManager.getCurrentLevelNumber());
        setupNextLevel(); // Initialize the game for the next level
        gameLoop.start();
    }

    public void loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                tileManager = new TileManager("/tiles/StoneTile.png");
                objectPositions = initializeObjectPositions(canvas, 1);
                break;
            case 2:
                tileManager = new TileManager("/tiles/RedTile.png");
                objectPositions = initializeObjectPositions(canvas, 2);
                break;
            // Add more cases for additional levels
        }
        Level currentLevel = levelManager.getCurrentLevel();
        Player player = new Player(currentLevel.getPlayer().getX(), currentLevel.getPlayer().getY(), canvas.getWidth(), canvas.getHeight());
        currentLevel.setPlayer(player);
    }

    public void setupNextLevel() {
        Level currentLevel = levelManager.getCurrentLevel();
        Player player = new Player(currentLevel.getPlayer().getX(), currentLevel.getPlayer().getY(), canvas.getWidth(), canvas.getHeight());
        currentLevel.setPlayer(player);
        List<Puzzle> puzzles = currentLevel.getPuzzles();
        Door door = currentLevel.getDoor();


        objectPositions = initializeObjectPositions(canvas, levelManager.getCurrentLevelNumber());

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        setSceneControls(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
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
