package tech.makers.demo.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levelManagement.Interaction;
import tech.makers.demo.levelManagement.Puzzle;
import tech.makers.demo.assets.Door;
import tech.makers.demo.assets.Sound;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.CrowdInteraction;

import java.util.List;
import java.util.Random;

public class Player {
    public double x;
    public double y;
    private final double width = 25;  // Define the player's width
    private final double height = 50; // Define the player's height
    private Inventory inventory;
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
    // Sound stuff
    Sound sound = new Sound();
    private int stepCounter = 0;
    private Random random = new Random();
    private int lastSoundIndex = -1;
    private int[] footstepSounds = {3, 4, 5, 6};

    public Player(double x, double y, Inventory inventory) {
        this.x = x;
        this.y = y;
        this.inventory = inventory;
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

    // Method to move the player up
    public void moveUp(List<Puzzle> puzzles, Door door, List<Interaction> interactions) {
        double newY = y - 20;
        System.out.println("Trying to move up to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzles) && !checkDoorCollision(x, newY, door) && !checkInteractionCollision(x, newY, interactions)) {
            y = newY;
            direction = "up";
            state = "run";
            playFootstepSound();
        }
    }

    public void moveDown(List<Puzzle> puzzles, Door door, List<Interaction> interactions) {
        double newY = y + 20;
        System.out.println("Trying to move down to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzles) && !checkDoorCollision(x, newY, door) && !checkInteractionCollision(x, newY, interactions)) {
            y = newY;
            direction = "down";
            state = "run";
            playFootstepSound();
        }
    }

    // Method to move the player left
    public void moveLeft(List<Puzzle> puzzles, Door door, List<Interaction> interactions) {
        double newX = x - 20;
        System.out.println("Trying to move left to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzles) && !checkDoorCollision(newX, y, door) && !checkInteractionCollision(newX, y, interactions)) {
            x = newX;
            direction = "left";
            state = "run";
            playFootstepSound();
        }
    }

    // Method to move the player right
    public void moveRight(List<Puzzle> puzzles, Door door, List<Interaction> interactions) {
        double newX = x + 20;
        System.out.println("Trying to move right to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzles) && !checkDoorCollision(newX, y, door) && !checkInteractionCollision(newX, y, interactions)) {
            x = newX;
            direction = "right";
            state = "run";
            playFootstepSound();
        }
    }

    public boolean checkPuzzleCollision(double newX, double newY, List<Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles) {
            boolean collision = puzzle.intersects(newX, newY);
            if (collision) {
                System.out.println("Collision with puzzle at: " + newX + ", " + newY);
                return true; // Return true immediately if collision detected
            }
        }
        return false; // Return false if no collision detected with any puzzle
    }

    public boolean checkDoorCollision(double newX, double newY, Door door) {
        boolean collision = door.intersects(newX, newY);
        if (collision) {
            System.out.println("Collision with door at: " + newX + ", " + newY);
        }
        return collision;
    }

    private boolean checkInteractionCollision(double newX, double newY, List<Interaction> interactions) {
        for (Interaction interaction : interactions) {
            if (interaction instanceof CrowdInteraction) {
                CrowdInteraction crowd = (CrowdInteraction) interaction;
                if (crowd.collidesWith(newX, newY, width, height)) {
                    System.out.println("Collision with crowd at: " + newX + ", " + newY);
                    return true;
                }
            }
        }
        return false;
    }

    // Method to render the player on the screen
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
        gc.drawImage(currentImage, x, y, width, height);
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

    public boolean isInRange(Door door) {
        double distance = Math.sqrt(Math.pow(door.getX() - x, 2) + Math.pow(door.getY() - y, 2));
        return distance < 100;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void stopMoving() {
        this.state = "idle";
    }

    // Inventory-related methods
    public void addItemToInventory(String item) {
        inventory.addItem(item);
    }

    public void removeItemFromInventory(String item) {
        inventory.removeItem(item);
    }

    public boolean hasItemInInventory(String item) {
        return inventory.hasItem(item);
    }

    public List<String> getInventoryItems() {
        return inventory.getItems();
    }

    private void playFootstepSound() {
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