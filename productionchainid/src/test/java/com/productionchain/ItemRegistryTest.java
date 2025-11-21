package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.productionchain.enginedatatypes.ItemRegistry;
import com.productionchain.enginedatatypes.ItemType;
import com.productionchain.constants.ItemConstants;

/**
 * Unit tests for ItemRegistry.
 * Tests item registration, retrieval, and management.
 */
public class ItemRegistryTest {

    @Before
    public void setUp() {
        // Clear registry before each test
        ItemRegistry.clear();
    }

    @Test
    public void testAddItem() {
        // Create and add a test item
        ItemType testItem = new ItemType(ItemConstants.WOOD, ItemConstants.CATEGORY_RAW_MATERIAL,
                                         1.0, 10.0, 50.0, true, true, true);
        ItemRegistry.addItem(testItem);

        // Verify item was added
        ItemType retrieved = ItemRegistry.getItemByName(ItemConstants.WOOD);
        assertNotNull("Item should be retrievable after adding", retrieved);
        assertEquals("Item name should match", ItemConstants.WOOD, retrieved.getName());
        assertEquals("Item category should match", ItemConstants.CATEGORY_RAW_MATERIAL, retrieved.getCategory());
    }

    @Test
    public void testGetItemByName_NonExistent() {
        // Test retrieving a non-existent item
        ItemType item = ItemRegistry.getItemByName("NonExistentItem");
        assertNull("Non-existent item should return null", item);
    }

    @Test
    public void testAddMultipleItems() {
        // Add multiple items
        ItemRegistry.addItem(new ItemType(ItemConstants.WOOD, ItemConstants.CATEGORY_RAW_MATERIAL,
                                         1.0, 10.0, 50.0, true, true, true));
        ItemRegistry.addItem(new ItemType(ItemConstants.STONE, ItemConstants.CATEGORY_RAW_MATERIAL,
                                         1.0, 10.0, 50.0, true, true, true));
        ItemRegistry.addItem(new ItemType(ItemConstants.COAL, ItemConstants.CATEGORY_RAW_MATERIAL,
                                         1.0, 8.0, 100.0, true, true, true));

        // Verify all items were added
        assertNotNull("Wood should exist", ItemRegistry.getItemByName(ItemConstants.WOOD));
        assertNotNull("Stone should exist", ItemRegistry.getItemByName(ItemConstants.STONE));
        assertNotNull("Coal should exist", ItemRegistry.getItemByName(ItemConstants.COAL));
    }

    @Test
    public void testItemExists() {
        // Add an item
        ItemRegistry.addItem(new ItemType(ItemConstants.WOOD, ItemConstants.CATEGORY_RAW_MATERIAL,
                                         1.0, 10.0, 50.0, true, true, true));

        // Test existence check
        assertTrue("Wood should exist", ItemRegistry.itemExists(ItemConstants.WOOD));
        assertFalse("NonExistent should not exist", ItemRegistry.itemExists("NonExistent"));
    }
}
