
package tech.makers.demo.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Obstacle {
    private double x;
    private double y;
    private double width;
    private double height;
    private Image image;
    private String imagePath; // To store the image path for debugging

    public Obstacle(double x, double y, double width, double height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath; // Store the image path for debugging
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            System.out.println("Loading image from path: " + imagePath); // Debug statement
            this.image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Image not found at path: " + imagePath);
            e.printStackTrace();
            this.image = null;
        }
    }

    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }

    public boolean intersects(double playerX, double playerY, double playerWidth, double playerHeight) {
        return playerX < x + width &&
                playerX + playerWidth > x &&
                playerY < y + height &&
                playerY + playerHeight > y;
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
