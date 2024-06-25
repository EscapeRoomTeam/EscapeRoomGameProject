package tech.makers.demo.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tech.makers.demo.levelManagement.HelperCharacter;

public class Eddie implements HelperCharacter {
    private final double x;
    private final double y;
    private final Image idleSpriteSheet;
    private Image[] idleRightFrames;
    private Image[] idleUpFrames;
    private Image[] idleLeftFrames;
    private Image[] idleDownFrames;
    private String direction = "down";
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DURATION = 150; // Frame duration in milliseconds
    private final String message;

    public Eddie(double x, double y, String imagePath, String message) {
        this.x = x;
        this.y = y;
        this.idleSpriteSheet = new Image(getClass().getResource(imagePath).toExternalForm());
        this.message = message;

        // Load frames for the idle animation
        this.idleRightFrames = loadFrames(idleSpriteSheet, 0);
        this.idleUpFrames = loadFrames(idleSpriteSheet, 6);
        this.idleLeftFrames = loadFrames(idleSpriteSheet, 12);
        this.idleDownFrames = loadFrames(idleSpriteSheet, 18);
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

    @Override
    public void render(GraphicsContext gc) {
        Image currentImage;
        Image[] currentFrames;
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
        currentImage = currentFrames[currentFrame];
        gc.drawImage(currentImage, x, y, 25, 50);
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % 6;
            lastFrameTime = currentTime;
        }
    }

    public boolean isInRange(double playerX, double playerY) {
        double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));
        return distance < 70;
    }

    public void interact() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tip");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
