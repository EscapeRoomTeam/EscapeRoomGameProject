package tech.makers.demo.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class TileManager {
    private List<Tile> tiles; // List to store all tiles
    private Image tileImage;  // Image object to hold the tile image

    public TileManager() {
        tiles = new ArrayList<>(); // Initialize the list of tiles
        // Load the tile image with a specified width and height of 48 pixels
        tileImage = new Image(getClass().getResource("/tiles/StoneTile.png").toExternalForm(), 48, 48, false, true);
        loadTiles(); // Load all tiles onto the canvas
    }

    private void loadTiles() {
        int tileSize = 48; // Each tile's width and height in pixels

        int canvasWidth = 768;  // Width of the canvas, adjusted to be divisible by tileSize
        int canvasHeight = 576; // Height of the canvas, adjusted to be divisible by tileSize

        // Calculate how many tiles fit along the width and height of the canvas
        int tilesX = canvasWidth / tileSize;
        int tilesY = canvasHeight / tileSize;

        // Iterate over each position in the grid defined by tilesX and tilesY
        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                // Add a new tile to the list at the calculated position
                tiles.add(new Tile(i * tileSize, j * tileSize, "floor", tileImage, tileSize, tileSize));
            }
        }
    }

    public void renderTiles(GraphicsContext gc) {
        // Iterate through each tile in the list and render it using the provided GraphicsContext
        for (Tile tile : tiles) {
            tile.render(gc); // Call the render method of the Tile class
        }
    }

    public Tile getTileAt(int x, int y) {
        // Iterate through each tile to find the one at the specified (x, y) coordinates
        for (Tile tile : tiles) {
            // Check if the current tile's position matches the specified coordinates
            if (tile.getX() == x && tile.getY() == y) {
                return tile; // Return the tile if coordinates match
            }
        }
        return null; // Return null if no tile is found at the specified coordinates
    }
}
