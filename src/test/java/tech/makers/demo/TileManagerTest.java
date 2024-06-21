package tech.makers.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.GraphicsContext;  // Might need to be mocked
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.Tile.Tile;
import tech.makers.demo.Tile.TileManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TileManagerTest extends ApplicationTest {

    private TileManager manager;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        String tileImagePath = "/tiles/StoneTile.png"; // Assuming image path
         manager = new TileManager(tileImagePath);
    }
    @Test
    public void testTileManager_loadTiles_CreatesTiles() {
        // Call loadTiles to populate the list
        manager.loadTiles();
        // Verify that tiles has elements after loading
        assertFalse(manager.tiles.isEmpty());
    }
    @Test
    public void testTileManager_renderTiles_CallsTileRender() {
        // Create a mock GraphicsContext
        GraphicsContext mockGraphicsContext = mock(GraphicsContext.class);
        manager.renderTiles(mockGraphicsContext);
        verify(mockGraphicsContext).drawImage(manager.tileImage, 0, 0, 48, 48);

    }
    @Test
    public void testTileManager_getTileAt_ValidCoordinates() {
        // Test getting a tile in the center (assuming tile size is 48)
        int centerX = 192;
        int centerY = 288;
        Tile centerTile = manager.getTileAt(centerX, centerY);
        assertNotNull(centerTile); // Verify a tile is returned

        // Additional assertions (optional):
        // assertEquals(centerX, centerTile.getX());
        // assertEquals(centerY, centerTile.getY());
    }

    @Test
    public void testTileManager_getTileAt_InvalidCoordinates() {
        // Test getting a tile outside the canvas (negative coordinates)
        assertNull(manager.getTileAt(-10, -20));

        // Test getting a tile outside the canvas (positive coordinates exceeding canvas size)
        int canvasWidth = 768;  // Assuming canvasWidth from TileManager
        int canvasHeight = 576;  // Assuming canvasHeight from TileManager
        int tileSize = 48;       // Assuming tile size from TileManager
        assertNull(manager.getTileAt(canvasWidth + tileSize, canvasHeight + tileSize));
    }



}



