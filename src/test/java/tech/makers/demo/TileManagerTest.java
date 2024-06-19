package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.Tile.Tile;
import tech.makers.demo.Tile.TileManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TileManagerTest {
    private TileManager tileManager;
    private GraphicsContext mockGc;

    @BeforeAll // starting the Test App before all tests
    public static void initJfx() {
        TestApp.launchApp();
    }

    @BeforeEach
    public void setUp() {
        mockGc = mock(GraphicsContext.class);
        tileManager = new TileManager();
    }

    @Test
    public void testConstructor() {
        // Ensure the tiles list is initialized and tileImage is loaded
        assertNotNull(tileManager.getTileAt(0, 0));
        assertNotNull(tileManager.getTileAt(48, 48));
        assertNull(tileManager.getTileAt(-1, -1));
    }

    @Test
    public void testLoadTiles() {
        // Validate the number of tiles loaded based on the canvas size and tile size
        int tileSize = 48;
        int canvasWidth = 768;
        int canvasHeight = 576;
        int expectedTilesCount = (canvasWidth / tileSize) * (canvasHeight / tileSize);

        int actualTilesCount = 0;
        for (int i = 0; i < canvasWidth; i += tileSize) {
            for (int j = 0; j < canvasHeight; j += tileSize) {
                assertNotNull(tileManager.getTileAt(i, j));
                actualTilesCount++;
            }
        }

        assertEquals(expectedTilesCount, actualTilesCount);
    }

    @Test
    public void testRenderTiles() {
        tileManager.renderTiles(mockGc);
        verify(mockGc, atLeastOnce()).drawImage(any(Image.class), anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    public void testGetTileAt() {
        Tile tile = tileManager.getTileAt(0, 0);
        assertNotNull(tile);
        assertEquals(0, tile.getX());
        assertEquals(0, tile.getY());

        Tile nonExistentTile = tileManager.getTileAt(9999, 9999);
        assertNull(nonExistentTile);
    }
}

