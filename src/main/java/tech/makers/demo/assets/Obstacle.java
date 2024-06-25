package tech.makers.demo.assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Objects;

public class Obstacle {
    private double x;
    private double y;
    private double width;
    private double height;
    private Image image;

    public Obstacle(double x, double y, double width, double height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
        gc.setStroke(Color.BLUE);  // Set outline color
        gc.strokeRect(x, y, width, height);
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