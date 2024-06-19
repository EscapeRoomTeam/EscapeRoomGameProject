package tech.makers.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.Tile.Tile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TileTest {

    private Tile tile;
    private GraphicsContext mockGc;
    private Image mockImage;

    @BeforeEach
    public void setUp() { // Create a new instance of the Tile class before each test
        mockGc = mock(GraphicsContext.class);
        mockImage = mock(Image.class);
        tile = new Tile(100.0, 150.0, "floor", mockImage, 50.0, 50.0);
    }

    @Test // Test the constructor
    public void testConstructor() {
        assertEquals(100.0, tile.getX());
        assertEquals(150.0, tile.getY());
        assertEquals("floor", tile.getType());
        assertEquals(mockImage, tile.getImage());
    }

    @Test // Test the render method
    public void testRender() {
        tile.render(mockGc);
        verify(mockGc, times(1)).drawImage(mockImage, 100.0, 150.0, 50.0, 50.0);
    }

    @Test
    public void testInteract_withDoor() {
        Tile doorTile = new Tile(200.0, 250.0, "door", mockImage, 50.0, 50.0);
        doorTile.interact();
        // As there is no return value or state change, we can just manually check the output
    }

    @Test
    public void testInteract_withNonDoor() {
        tile.interact();  // Interacting with a "floor" tile should do nothing
        // As there is no return value or state change, we can just manually check the output
    }

    @Test
    public void testGetX() {
        assertEquals(100.0, tile.getX());
    }

    @Test
    public void testGetY() {
        assertEquals(150.0, tile.getY());
    }

    @Test
    public void testGetType() {
        assertEquals("floor", tile.getType());
    }

    @Test
    public void testGetImage() {
        assertEquals(mockImage, tile.getImage());
    }
}

