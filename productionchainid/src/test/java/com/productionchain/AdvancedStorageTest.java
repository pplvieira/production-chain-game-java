package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.productionchain.mechanics.AdvancedStorage;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for AdvancedStorage class.
 * Tests FIFO operations, capacity management, and spoilage mechanics.
 */
public class AdvancedStorageTest {

    private AdvancedStorage storage;

    @Before
    public void setUp() {
        // Create storage that can hold Wood and Stone with 100 capacity
        ArrayList<String> allowedItems = new ArrayList<>(Arrays.asList(ItemConstants.WOOD, ItemConstants.STONE));
        storage = new AdvancedStorage(100.0, allowedItems);
    }

    @Test
    public void testAddItem_ValidItem_ItemAdded() {
        // Test adding a valid item
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);

        assertTrue("Storage should contain Wood", storage.hasItem(ItemConstants.WOOD, 10));
        assertEquals("Should have correct quantity", 10.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testAddItem_ExceedsCapacity_NotAdded() {
        // Test adding item that exceeds capacity
        storage.addItemCheckCapacity(ItemConstants.WOOD, 150, 1.0);

        // Item should not be added due to capacity constraint
        assertEquals("Should have no items", 0.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testAddItem_DisallowedItem_NotAdded() {
        // Test adding an item not in the allowed list
        storage.addItemCheckCapacity(ItemConstants.COAL, 10, 1.0);

        // Item should not be added
        assertEquals("Should not add disallowed item", 0.0, storage.getItemQuantity(ItemConstants.COAL), 0.001);
    }

    @Test
    public void testRemoveItem_SufficientQuantity_ItemRemoved() {
        // Setup: Add items first
        storage.addItemCheckCapacity(ItemConstants.WOOD, 50, 10.0);

        // Test removing items
        boolean result = storage.removeItem(ItemConstants.WOOD, 20);

        assertTrue("Should successfully remove item", result);
        assertEquals("Should have correct remaining quantity", 30.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testRemoveItem_InsufficientQuantity_ReturnsFalse() {
        // Setup: Add fewer items than we try to remove
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);

        // Test removing more items than available
        boolean result = storage.removeItem(ItemConstants.WOOD, 20);

        assertFalse("Should fail to remove more than available", result);
        assertEquals("Should still have original quantity", 10.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testFIFO_OldestBatchRemovedFirst() {
        // Add multiple batches with different durabilities
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);  // Older batch
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 10.0); // Newer batch

        // Remove items - should remove from oldest batch first (FIFO)
        storage.removeItem(ItemConstants.WOOD, 5);

        // Total should be 15 (5 from old batch + 10 from new batch)
        assertEquals("Should have 15 Wood remaining", 15.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testGetItemQuantity_NonExistentItem_ReturnsZero() {
        // Test getting quantity of item not in storage
        double quantity = storage.getItemQuantity(ItemConstants.COAL);

        assertEquals("Should return 0 for non-existent item", 0.0, quantity, 0.001);
    }

    @Test
    public void testHasItem_ItemExists_ReturnsTrue() {
        // Setup: Add item
        storage.addItemCheckCapacity(ItemConstants.WOOD, 5, 3.0);

        // Test
        assertTrue("Should return true for existing item with sufficient quantity", storage.hasItem(ItemConstants.WOOD, 5));
        assertTrue("Should return true for existing item with less than available", storage.hasItem(ItemConstants.WOOD, 3));
        assertFalse("Should return false for more than available", storage.hasItem(ItemConstants.WOOD, 10));
        assertFalse("Should return false for non-existent item", storage.hasItem(ItemConstants.COAL, 1));
    }

    @Test
    public void testGetUsedCapacity_MultipleItems_CorrectTotal() {
        // Add multiple items (quantity determines capacity used, not durability)
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 2.0);  // 10 capacity used
        storage.addItemCheckCapacity(ItemConstants.STONE, 15, 3.0); // 15 capacity used

        // Total used: 25
        assertEquals("Should calculate correct used capacity", 25.0, storage.getUsedCapacity(), 0.001);
    }

    @Test
    public void testGetAvailableCapacity_CorrectCalculation() {
        // Storage has 100 capacity
        storage.addItemCheckCapacity(ItemConstants.WOOD, 30, 3.0); // 30 used

        // Available should be 70
        assertEquals("Should have 70 capacity available", 70.0, storage.getAvailableCapacity(), 0.001);
    }

    @Test
    public void testDegradeItems_ExpiredItems_RemovedFromStorage() {
        // Add items with durability of 5 turns
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);

        // Simulate passage of time (5 turns) - items should spoil after durability reaches 0
        for (int i = 0; i < 5; i++) {
            storage.degradeItems();
        }

        // Items should be removed after durability expires
        assertEquals("Spoiled items should be removed", 0.0, storage.getItemQuantity(ItemConstants.WOOD), 0.001);
    }

    @Test
    public void testToString_ContainsStorageInfo() {
        // Add some items
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);

        String result = storage.toString();

        assertNotNull("toString should not return null", result);
        assertTrue("Should contain storage info", result.length() > 0);
    }
}
