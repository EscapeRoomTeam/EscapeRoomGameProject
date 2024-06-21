package tech.makers.demo.levels;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.assets.Door;
import tech.makers.demo.player.Player;

public class LevelManager {
    public Level[] levels;
    public int currentLevelIndex;
    private GraphicsContext gc;
    private EscapeRoomGame game;

    public LevelManager(GraphicsContext gc, EscapeRoomGame game) {
        this.gc = gc;
        this.game = game;
        this.currentLevelIndex = 0;
        initializeLevels();
    }

    protected void initializeLevels() {
        // Create players, puzzles, and doors for each level
        Player player1 = new Player(100, 100);
        Puzzle puzzle1 = new Puzzle(300, 300, "What is 2 + 2?", "4", "/sprites/Computer.png");
        Door door1 = new Door(600, 400, "/sprites/door.png");
        Level level1 = new Level(player1, puzzle1, door1);

        Player player2 = new Player(100, 100);
        Puzzle puzzle2 = new Puzzle(300, 300, "What is the capital of France?", "Paris", "/sprites/Computer 2.png");
        Door door2 = new Door(600, 400, "/sprites/Door 2.png");
        Level level2 = new Level(player2, puzzle2, door2);

        levels = new Level[]{level1, level2};
    }

    public Level getCurrentLevel() {
        return levels[currentLevelIndex];
    }

    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1; // Assuming level numbers start from 1
    }

    public void loadNextLevel() {
        if (currentLevelIndex < levels.length - 1) {
            currentLevelIndex++;
            game.setupNextLevel(); // Ensure the game setup for the next level
        } else {
            // Handle the end of the game
            System.out.println("You have completed all levels!");
        }
    }

    public void render() {
        getCurrentLevel().render(gc);
    }

    public void update() {
        Level currentLevel = getCurrentLevel();
        currentLevel.update();
        if (currentLevel.isCompleted()) {
            game.completeLevel();
        }
    }

    public void completeLevel() {
        game.completeLevel();
    }

    public void setLevels(Level[] levels) {
        this.levels = levels;
    }
}
