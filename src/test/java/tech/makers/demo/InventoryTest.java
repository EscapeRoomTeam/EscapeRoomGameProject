package tech.makers.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.demo.player.Inventory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
    }

    @Test
    public void testAddItem() {
        inventory.addItem("Item1");
        assertTrue(inventory.hasItem("Item1"));
    }

    @Test
    public void testHasItem() {
        inventory.addItem("Item1");
        assertTrue(inventory.hasItem("Item1"));
        assertFalse(inventory.hasItem("Item2"));
    }

    @Test
    public void testGetItems() {
        inventory.addItem("Item1");
        inventory.addItem("Item2");

        List<String> items = inventory.getItems();

        assertEquals(2, items.size());
        assertTrue(items.contains("Item1"));
        assertTrue(items.contains("Item2"));
    }

    @Test
    public void testRemoveItem() {
        inventory.addItem("Item1");
        inventory.addItem("Item2");

        assertTrue(inventory.hasItem("Item1"));
        inventory.removeItem("Item1");
        assertFalse(inventory.hasItem("Item1"));
    }

    @Test
    public void testGetItemsReturnsCopy() {
        List<String> originalItems = inventory.getItems();
        assertNotNull(originalItems);

        // Modify the original list obtained from getItems()
        originalItems.add("Item1");

        // Original inventory should remain unchanged
        assertTrue(inventory.getItems().isEmpty());
    }
}

