package tech.makers.demo.levelManagement;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.levelManagement.levels.Level1;
import tech.makers.demo.levelManagement.levels.Level2;

public class LevelManager {
    private Level[] levels;
    private int currentLevelIndex;
    private GraphicsContext gc;
    private EscapeRoomGame game;

    public LevelManager(GraphicsContext gc, EscapeRoomGame game) {
        this.gc = gc;
        this.game = game;
        this.currentLevelIndex = 0;
        initializeLevels();
    }

    protected void initializeLevels() {
        levels = new Level[] {
                new Level1(),
                new Level2()
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