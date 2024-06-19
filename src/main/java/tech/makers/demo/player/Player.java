package tech.makers.demo.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;

import java.util.Random;

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
    // Sound stuff
    Sound sound = new Sound();
    private int stepCounter = 0;
    private Random random = new Random();
    private int lastSoundIndex = -1;
    private int[] footstepSounds = {3, 4, 5, 6};


    // Player constructor, sets the player's starting position
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

    // Method to move the player up
    public void moveUp(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x, y - 20, puzzle) && !checkDoorCollision(x, y - 20, door)) {
            y -= 20;
            direction = "up";
            // Play sound every other step
            stepCounter++;
            if (stepCounter % 4 == 0) {
                int soundIndex;
                do {
                    soundIndex = random.nextInt(footstepSounds.length);
                } while (soundIndex == lastSoundIndex);
                lastSoundIndex = soundIndex;
                sound.setFile(footstepSounds[soundIndex]);
                sound.setVolume(-27.0f);
                sound.play();
            }
        }

    }

    // Method to move the player down
    public void moveDown(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x, y + 20, puzzle) && !checkDoorCollision(x, y + 20, door)) {
            y += 20;
            direction = "down";
            stepCounter++;
            if (stepCounter % 4 == 0) {
                int soundIndex;
                do {
                    soundIndex = random.nextInt(footstepSounds.length);
                } while (soundIndex == lastSoundIndex);
                lastSoundIndex = soundIndex;
                sound.setFile(footstepSounds[soundIndex]);
                sound.setVolume(-27.0f);
                sound.play();
            }
        }
    }

    // Method to move the player left
    public void moveLeft(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x - 20, y, puzzle) && !checkDoorCollision(x - 20, y, door)) {
            x -= 20;
            direction = "left";
            stepCounter++;
            if (stepCounter % 4 == 0) {
                int soundIndex;
                do {
                    soundIndex = random.nextInt(footstepSounds.length);
                } while (soundIndex == lastSoundIndex);
                lastSoundIndex = soundIndex;
                sound.setFile(footstepSounds[soundIndex]);
                sound.setVolume(-27.0f);
                sound.play();
            }
        }
    }

    // Method to move the player right
    public void moveRight(Puzzle puzzle, Door door) {
        // Check for collisions with puzzle or door before moving
        if (!checkPuzzleCollision(x + 20, y, puzzle) && !checkDoorCollision(x + 20, y, door)) {
            x += 20;
            direction = "right";
            stepCounter++;
            if (stepCounter % 4 == 0) {
                int soundIndex;
                do {
                    soundIndex = random.nextInt(footstepSounds.length);
                } while (soundIndex == lastSoundIndex);
                lastSoundIndex = soundIndex;
                sound.setFile(footstepSounds[soundIndex]);
                sound.setVolume(-27.0f);
                sound.play();
            }
        }
    }

    public boolean checkPuzzleCollision(double newX, double newY, Puzzle puzzle) {
        return puzzle.intersects(newX, newY);
    }

    public boolean checkDoorCollision(double newX, double newY, Door door) {
        return door.intersects(newX, newY);
    }

    // Method to render the player on the screen
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

    // Getter for the player's Y-coordinate
    public double getY() {
        return y;
    }
}
