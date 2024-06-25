package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levelManagement.levels.Level1;
import tech.makers.demo.levelManagement.levels.Level2;
import tech.makers.demo.levelManagement.levels.Level3;

public class LevelManager {
    public Level[] levels;
    private int currentLevelIndex;
    private final GraphicsContext gc;
    private final EscapeRoomGame game;

    public LevelManager(GraphicsContext gc, EscapeRoomGame game) {
        this.gc = gc;
        this.game = game;
        this.currentLevelIndex = 0;
        initializeLevels();
    }

    protected void initializeLevels() {
        levels = new Level[] {
                new Level1(),
                new Level2(),
                new Level3()
        };
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
            loadLevel(currentLevelIndex);
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
            Door.unlock();
        }
    }

    public void completeLevel() {
        game.completeLevel();
        Door.lock();
    }

    public void setLevels(Level[] levels) {
        this.levels = levels;
    }

    public void loadLevel(int levelIndex) {
        Door.lock(); // Lock the door at the start of the level
        Level currentLevel = levels[levelIndex];
        // Perform additional setup for the level if needed
    }
}
