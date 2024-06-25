package tech.makers.demo.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.models.assets.*;

import tech.makers.demo.models.sound.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    public double x;
    public double y;
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

    // Inventory
    private Inventory inventory;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.idleSpriteSheet = new Image(getClass().getResource("/sprites/Bob_idle_anim.png").toString());
        this.runSpriteSheet = new Image(getClass().getResource("/sprites/Bob_run.png").toString());

        this.idleRightFrames = loadFrames(idleSpriteSheet, 0);
        this.idleUpFrames = loadFrames(idleSpriteSheet, 6);
        this.idleLeftFrames = loadFrames(idleSpriteSheet, 12);
        this.idleDownFrames = loadFrames(idleSpriteSheet, 18);
        this.runRightFrames = loadFrames(runSpriteSheet, 0);
        this.runUpFrames = loadFrames(runSpriteSheet, 6);
        this.runLeftFrames = loadFrames(runSpriteSheet, 12);
        this.runDownFrames = loadFrames(runSpriteSheet, 18);

        // Initialize inventory
        this.inventory = new Inventory();
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

    public void moveUp(List<Puzzle> puzzles, Door door) {
        double newY = y - 20;
        System.out.println("Trying to move up to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzles) && !checkDoorCollision(x, newY, door)) {
            y = newY;
            direction = "up";
            state = "run";
            playStepSound();
        }
    }

    public void moveDown(List<Puzzle> puzzles, Door door) {
        double newY = y + 20;
        System.out.println("Trying to move down to: " + x + ", " + newY);
        if (!checkPuzzleCollision(x, newY, puzzles) && !checkDoorCollision(x, newY, door)) {
            y = newY;
            direction = "down";
            state = "run";
            playStepSound();
        }
    }

    public void moveLeft(List<Puzzle> puzzles, Door door) {
        double newX = x - 20;
        System.out.println("Trying to move left to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzles) && !checkDoorCollision(newX, y, door)) {
            x = newX;
            direction = "left";
            state = "run";
            playStepSound();
        }
    }

    public void moveRight(List<Puzzle> puzzles, Door door) {
        double newX = x + 20;
        System.out.println("Trying to move right to: " + newX + ", " + y);
        if (!checkPuzzleCollision(newX, y, puzzles) && !checkDoorCollision(newX, y, door)) {
            x = newX;
            direction = "right";
            state = "run";
            playStepSound();
        }
    }

    private boolean checkPuzzleCollision(double newX, double newY, List<Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles) {
            boolean collision = puzzle.intersects(newX, newY);
            if (collision) {
                System.out.println("Collision with puzzle at: " + newX + ", " + newY);
                return true; // Return true immediately if collision detected
            }
        }
        return false; // Return false if no collision detected with any puzzle
    }

    private boolean checkDoorCollision(double newX, double newY, Door door) {
        boolean collision = door.intersects(newX, newY);
        if (collision) {
            System.out.println("Collision with door at: " + newX + ", " + newY);
        }
        return collision;
    }

    private void playStepSound() {
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

    public void interactWithObjects(List<Puzzle> puzzles, Door door, Safe safe, Computer computer, Eddie helperCharacter) {
        for (Puzzle puzzle : puzzles) {
            puzzle.interact();
        }

        door.interact(null); // Assuming you need to pass the level manager or some context

        if (safe.isInRange() && !safe.isUnlocked()) {
            safe.interact(this);
        }

        if (computer.isInRange() && !computer.hasNewOS()) {
            computer.interact(this);
        }

        if (helperCharacter.isInRange(x, y)) {
            helperCharacter.interact();
        }
    }

    public void render(GraphicsContext gc) {
        updateFrame();
        Image currentImage;
        Image[] currentFrames;
        if (state.equals("run")) {
            currentFrames = switch (direction) {
                case "up" -> runUpFrames;
                case "down" -> runDownFrames;
                case "left" -> runLeftFrames;
                case "right" -> runRightFrames;
                default -> runDownFrames;
            };
        } else {
            currentFrames = switch (direction) {
                case "up" -> idleUpFrames;
                case "down" -> idleDownFrames;
                case "left" -> idleLeftFrames;
                case "right" -> idleRightFrames;
                default -> idleDownFrames;
            };
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

    public void stopMoving() {
        this.state = "idle";
        this.currentFrame = 0; // Optionally, reset the animation frame to the first frame
    }

    // Inventory methods
    public void addItemToInventory(String item) {
        inventory.addItem(item);
    }

    public void removeItemFromInventory(String item) {
        inventory.removeItem(item);
    }

    public boolean hasItemInInventory(String item) {
        return inventory.hasItem(item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static class Inventory {
        private List<String> items;

        public Inventory() {
            this.items = new ArrayList<>();
        }

        public void addItem(String item) {
            items.add(item);
        }

        public boolean hasItem(String item) {
            return items.contains(item);
        }

        public List<String> getItems() {
            return new ArrayList<>(items); // Return a copy to avoid external modification
        }

        public void removeItem(String item) {
            items.remove(item);
        }
    }

    public static class Level {
        private Player player;
        private List<Puzzle> puzzles;
        private Door door;
        private Eddie helperCharacter;
        private Computer computer;
        private Safe safe;
        private boolean completed;

        public Level(Player player, List<Puzzle> puzzles, Door door, Eddie helperCharacter, Computer computer, Safe safe) {
            this.player = player;
            this.puzzles = puzzles;
            this.door = door;
            this.helperCharacter = helperCharacter;
            this.computer = computer;
            this.safe = safe;
            this.completed = false;
        }

        public void render(GraphicsContext gc) {
            player.render(gc);
            for (Puzzle puzzle : puzzles) {
                puzzle.render(gc);
            }
            door.render(gc);
            if (helperCharacter != null) {
                helperCharacter.render(gc);
            }
            if (computer != null) {
                computer.render(gc);
            }
            if (safe != null) {
                safe.render(gc);
            }
        }

        public void update() {
            for (Puzzle puzzle : puzzles) {
                puzzle.checkPlayerInRange(player);
            }
            door.checkPlayerInRange(player);
            if (helperCharacter != null) {
                helperCharacter.update();
            }

            if (safe != null) {
                safe.checkPlayerInRange(player);
            }

            if (computer != null) {
                computer.checkPlayerInRange(player);
            }

            boolean allPuzzlesSolved = true;
            for (Puzzle puzzle : puzzles) {
                if (!puzzle.isSolved()) {
                    allPuzzlesSolved = false;
                    Door.lock();
                    break;
                }
            }
            if (allPuzzlesSolved) {
                Door.unlock();
            }
        }

        public boolean isCompleted() {
            boolean allPuzzlesSolved = true;
            for (Puzzle puzzle : puzzles) {
                if (!puzzle.isSolved()) {
                    allPuzzlesSolved = false;
                    break;
                }
            }
            return allPuzzlesSolved && door.isOpen();
        }

        public void handleInteraction() {
            if (helperCharacter != null && helperCharacter.isInRange(player.getX(), player.getY())) {
                helperCharacter.interact();
            }
            if (computer != null && computer.isInRange()) {
                computer.interact(player);
            }
            if (safe != null && safe.isInRange()) {
                safe.interact(player);
            }
        }

        public Player getPlayer() {
            return player;
        }

        public List<Puzzle> getPuzzles() {
            return puzzles;
        }

        public Door getDoor() {
            return door;
        }

        public Eddie getHelperCharacter() {
            return helperCharacter;
        }

        public Computer getComputer() {
            return computer;
        }

        public Safe getSafe() {
            return safe;
        }

        public void completeLevel() {
            this.completed = true;
        }
    }
}