package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.levels.Level;

public class LevelManager {
    private Level[] levels;
    private int currentLevelIndex;
    private GraphicsContext gc;

    public LevelManager(GraphicsContext gc) {
        this.gc = gc;
        this.currentLevelIndex = 0;
        initializeLevels();
    }

    protected void initializeLevels() {
        // Create players, puzzles, and doors for each level
        Player player1 = new Player(100, 100);
        Puzzle puzzle1 = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door1 = new Door(600, 400);
        Level level1 = new Level(player1, puzzle1, door1);

        Player player2 = new Player(100, 100);
        Puzzle puzzle2 = new Puzzle(300, 300, "What is the capital of France?", "Paris");
        Door door2 = new Door(600, 400);
        Level level2 = new Level(player2, puzzle2, door2);

        levels = new Level[] { level1, level2 };
    }

    public Level getCurrentLevel() {
        return levels[currentLevelIndex];
    }

    public void loadNextLevel() {
        if (currentLevelIndex < levels.length - 1) {
            currentLevelIndex++;
        } else {
            // Handle the end of the game
            System.out.println("You have completed all levels!");
        }
    }

    public void render() {
        getCurrentLevel().render(gc);
    }

    public void update() {
        getCurrentLevel().update();
    }

    protected void setLevels(Level[] levels) {
        this.levels = levels;
    }

}
