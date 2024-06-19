package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Player {
    private double x;
    private double y;
    private Image spriteSheet;
    private Image[] idleRightFrames;
    private Image[] idleUpFrames;
    private Image[] idleLeftFrames;
    private Image[] idleDownFrames;
    private String direction = "down";
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DURATION = 150; // Frame duration in milliseconds
    Sound sound = new Sound();

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.spriteSheet = new Image(getClass().getResource("/sprites/Bob_idle_anim.png").toString());

        // Print the dimensions of the loaded sprite sheet
        System.out.println("Sprite Sheet Width: " + spriteSheet.getWidth());
        System.out.println("Sprite Sheet Height: " + spriteSheet.getHeight());

        this.idleRightFrames = loadFrames(0, 0);
        this.idleUpFrames = loadFrames(6, 0);
        this.idleLeftFrames = loadFrames(12, 0);
        this.idleDownFrames = loadFrames(18, 0);
    }

    private Image[] loadFrames(int startFrame, int row) {
        Image[] frames = new Image[6];
        int spriteWidth = 16;
        int spriteHeight = 32;
        for (int i = 0; i < 6; i++) {
            int x = (startFrame + i) * spriteWidth;
            int y = row * spriteHeight;
            if (x + spriteWidth <= spriteSheet.getWidth() && y + spriteHeight <= spriteSheet.getHeight()) {
                frames[i] = new WritableImage(spriteSheet.getPixelReader(), x, y, spriteWidth, spriteHeight);
            } else {
                System.out.println("Error: Frame coordinates out of bounds. x=" + x + ", y=" + y);
            }
        }
        return frames;
    }

    public void moveUp(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x, y - 20, puzzle) && !checkDoorCollision(x, y - 20, door)) {
            y -= 20;
            direction = "up";
        }
    }

    public void moveDown(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x, y + 20, puzzle) && !checkDoorCollision(x, y + 20, door)) {
            y += 20;
            direction = "down";
        }
    }

    public void moveLeft(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x - 20, y, puzzle) && !checkDoorCollision(x - 20, y, door)) {
            x -= 20;
            direction = "left";
        }
    }

    public void moveRight(Puzzle puzzle, Door door) {
        if (!checkPuzzleCollision(x + 20, y, puzzle) && !checkDoorCollision(x + 20, y, door)) {
            x += 20;
            direction = "right";
        }
    }

    boolean checkPuzzleCollision(double newX, double newY, Puzzle puzzle) {
        return puzzle.intersects(newX, newY);
    }

    boolean checkDoorCollision(double newX, double newY, Door door) {
        return door.intersects(newX, newY);
    }

    public void render(GraphicsContext gc) {
        updateFrame();
        Image currentImage;
        switch (direction) {
            case "up":
                currentImage = idleUpFrames[currentFrame];
                break;
            case "down":
                currentImage = idleDownFrames[currentFrame];
                break;
            case "left":
                currentImage = idleLeftFrames[currentFrame];
                break;
            case "right":
                currentImage = idleRightFrames[currentFrame];
                break;
            default:
                currentImage = idleDownFrames[currentFrame];
        }
        gc.drawImage(currentImage, x, y, 25, 50);
    }

    private void updateFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % 6;
            lastFrameTime = currentTime;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
