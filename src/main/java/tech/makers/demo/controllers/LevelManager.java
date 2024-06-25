package tech.makers.demo.controllers;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.models.assets.Door;
import tech.makers.demo.models.assets.Eddie;
import tech.makers.demo.models.Player;
import tech.makers.demo.models.levels.Level1;
import tech.makers.demo.models.levels.Level2;

public class LevelManager {
    public Player.Level[] levels;
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
        levels = new Player.Level[] {
                new Level1(),
                new Level2()
        };
    }

    public Player.Level getCurrentLevel() {
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
        Player.Level currentLevel = getCurrentLevel();
        currentLevel.update();
        if (currentLevel.isCompleted()) {
            Door.unlock();
        }
    }

    public void completeLevel() {
        game.completeLevel();
    }

    public void setLevels(Player.Level[] levels) {
        this.levels = levels;
    }

    public Eddie getHelperCharacter() {
        return getCurrentLevel().getHelperCharacter();
    }
}