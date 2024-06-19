package tech.makers.demo.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
    private double x, y; // Position of the tile on the canvas
    private String type; // Type of the tile (e.g., floor, door)
    private Image image; // Image associated with the tile
    private double width, height; // Width and height of the tile

    // Constructor to initialize the tile with its properties
    public Tile(double x, double y, String type, Image image, double width, double height) {
        this.x = x; // X-coordinate of the tile
        this.y = y; // Y-coordinate of the tile
        this.type = type; // Type of the tile
        this.image = image; // Image of the tile
        this.width = width; // Width of the tile
        this.height = height; // Height of the tile
    }

    // Method to render the tile on the canvas using GraphicsContext
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height); // Draw the tile image at the specified position with the specified size
    }

    // Method to define interactions based on the type of the tile
    public void interact() {
        // Check the type of the tile and perform corresponding actions
        if (type.equals("door")) {
            System.out.println("Door interacted"); // Print a message when a door tile is interacted with
        }
    }

    // Getters to retrieve the properties of the tile
    public double getX() {
        return x; // Return the X-coordinate of the tile
    }

    public double getY() {
        return y; // Return the Y-coordinate of the tile
    }

    public String getType() {
        return type; // Return the type of the tile
    }

    public Image getImage() {
        return image; // Return the image of the tile
    }
}
