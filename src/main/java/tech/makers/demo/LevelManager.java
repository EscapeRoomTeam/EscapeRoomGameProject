package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import tech.makers.demo.levels.Level;
import tech.makers.demo.Player;
import tech.makers.demo.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<Level> levels;
    private int currentLevelIndex;
    private GraphicsContext gc;

    public LevelManager(GraphicsContext gc) {
        this.gc = gc;
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        initializeLevels();
    }

    private void initializeLevels() {
        Player player = new Player(100, 100);
        Puzzle puzzle1 = new Puzzle(300, 300, "What is 2 + 2?", "4");
        Door door1 = new Door(600, 400);
        levels.add(new Level(player, puzzle1, door1));

        Puzzle puzzle2 = new Puzzle(200, 200, "What is 5 - 3?", "2");
        Door door2 = new Door(500, 300);
        levels.add(new Level(new Player(100, 100), puzzle2, door2));
    }

    public void loadNextLevel() {
        currentLevelIndex++;
        if (currentLevelIndex >= levels.size()) {
            currentLevelIndex = 0; // Loop back to the first level or handle game completion
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void render() {
        getCurrentLevel().render(gc);
    }

    public void update() {
        getCurrentLevel().update();
    }
}