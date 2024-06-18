package tech.makers.demo.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tech.makers.demo.objects.Door;
import tech.makers.demo.puzzles.Puzzle;

public class Player {
    private double x;
    private double y;

    //Player constructor, sets the player's starting position
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //These next 4 methods move the player up, down, left, and right
    public void moveUp(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x, y -20, puzzle) && !checkDoorCollision(x, y -20, door)) {
            y -= 20;
        }
    }

    public void moveDown(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x, y + 20, puzzle) && !checkDoorCollision(x, y + 20, door)) {
            y += 20;
        }
    }

    public void moveLeft(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x - 20, y, puzzle) && !checkDoorCollision(x - 20, y, door)) {
            x -= 20;
        }
    }

    public void moveRight(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x + 20, y, puzzle) && !checkDoorCollision(x + 20, y, door)) {
            x += 20;
        }
    }

    public boolean checkPuzzleCollision(double newX, double newY, Puzzle puzzle) {
        if (puzzle.intersects(newX, newY)) {
            return true;
        }
        return false;
    }

    public boolean checkDoorCollision(double newX, double newY, Door door) {
        if (door.intersects(newX, newY)) {
            return true;
        }
        return false;
    }







    //This method renders the player on the screen
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, 50, 50);
    }

    //These next 2 methods return the player's current position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}




