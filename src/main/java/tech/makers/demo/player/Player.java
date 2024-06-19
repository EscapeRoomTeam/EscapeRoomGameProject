package tech.makers.demo.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;

public class Player {
    private double x;
    private double y;
    private Image idleSpriteSheet;
    private Image runSpriteSheet;
    private Image[] idleRightFrames;
    private Image[] idleUpFrames;
    private Image[] idleLeftFrames;
    private Image[] idleDownFrames;
    private Image[] runRightFrames;
    private Image[] runUpFrames;
    private Image[] runLeftFrames;
    private Image[] runDownFrames;
    private String direction = "down";
    private String state = "idle";
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DURATION = 150; // Frame duration in milliseconds
    Sound sound = new Sound();

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.idleSpriteSheet = new Image(getClass().getResource("/sprites/Bob_idle_anim.png").toString());
        this.runSpriteSheet = new Image(getClass().getResource("/sprites/Bob_run.png").toString());

        // Print the dimensions of the loaded sprite sheets
        System.out.println("Idle Sprite Sheet Width: " + idleSpriteSheet.getWidth());
        System.out.println("Idle Sprite Sheet Height: " + idleSpriteSheet.getHeight());
        System.out.println("Run Sprite Sheet Width: " + runSpriteSheet.getWidth());
        System.out.println("Run Sprite Sheet Height: " + runSpriteSheet.getHeight());

        this.idleRightFrames = loadFrames(idleSpriteSheet, 0);
        this.idleUpFrames = loadFrames(idleSpriteSheet, 6);
        this.idleLeftFrames = loadFrames(idleSpriteSheet, 12);
        this.idleDownFrames = loadFrames(idleSpriteSheet, 18);
        this.runRightFrames = loadFrames(runSpriteSheet, 0);
        this.runUpFrames = loadFrames(runSpriteSheet, 6);
        this.runLeftFrames = loadFrames(runSpriteSheet, 12);
        this.runDownFrames = loadFrames(runSpriteSheet, 18);
    }

    private Image[] loadFrames(Image spriteSheet, int startFrame) {
        Image[] frames = new Image[6];
        int spriteWidth = 16;
        int spriteHeight = 32;
        for (int i = 0; i < 6; i++) {
            int x = (startFrame + i) * spriteWidth;
            int y = 0; // Since the image is a single row
            if (x + spriteWidth <= spriteSheet.getWidth() && y + spriteHeight <= spriteSheet.getHeight()) {
                frames[i] = new WritableImage(spriteSheet.getPixelReader(), x, y, spriteWidth, spriteHeight);
            } else {
                System.out.println("Error: Frame coordinates out of bounds. x=" + x + ", y=" + y);
            }
        }
        return frames;
    }

    public void moveUp(Puzzle puzzle, Door door) {
        double newY = y - 20;
        System.out.println("Trying to move up to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzle) && !checkDoorCollision(x, newY, door)) {
            y = newY;
            direction = "up";
            state = "run";
        }
    }

    public void moveDown(Puzzle puzzle, Door door) {
        double newY = y + 20;
        System.out.println("Trying to move down to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzle) && !checkDoorCollision(x, newY, door)) {
            y = newY;
            direction = "down";
            state = "run";
        }
    }

    public void moveLeft(Puzzle puzzle, Door door) {
        double newX = x - 20;
        System.out.println("Trying to move left to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzle) && !checkDoorCollision(newX, y, door)) {
            x = newX;
            direction = "left";
            state = "run";
        }
    }

    public void moveRight(Puzzle puzzle, Door door) {
        double newX = x + 20;
        System.out.println("Trying to move right to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzle) && !checkDoorCollision(newX, y, door)) {
            x = newX;
            direction = "right";
            state = "run";
        }
    }

    public boolean checkPuzzleCollision(double newX, double newY, Puzzle puzzle) {
        boolean collision = puzzle.intersects(newX, newY);
        if (collision) {
            System.out.println("Collision with puzzle at: " + newX + ", " + newY);
        }
        return collision;
    }

    public boolean checkDoorCollision(double newX, double newY, Door door) {
        boolean collision = door.intersects(newX, newY);
        if (collision) {
            System.out.println("Collision with door at: " + newX + ", " + newY);
        }
        return collision;
    }

    public void render(GraphicsContext gc) {
        updateFrame();
        Image currentImage;
        Image[] currentFrames;
        if (state.equals("run")) {
            switch (direction) {
                case "up":
                    currentFrames = runUpFrames;
                    break;
                case "down":
                    currentFrames = runDownFrames;
                    break;
                case "left":
                    currentFrames = runLeftFrames;
                    break;
                case "right":
                    currentFrames = runRightFrames;
                    break;
                default:
                    currentFrames = runDownFrames;
            }
        } else {
            switch (direction) {
                case "up":
                    currentFrames = idleUpFrames;
                    break;
                case "down":
                    currentFrames = idleDownFrames;
                    break;
                case "left":
                    currentFrames = idleLeftFrames;
                    break;
                case "right":
                    currentFrames = idleRightFrames;
                    break;
                default:
                    currentFrames = idleDownFrames;
            }
        }
        currentImage = currentFrames[currentFrame];
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

    public void setState(String state) {
        this.state = state;
    }

    public void stopMoving() {
        this.state = "idle";
    }
}


