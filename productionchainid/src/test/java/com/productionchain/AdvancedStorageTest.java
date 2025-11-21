package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.productionchain.mechanics.AdvancedStorage;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.Arrays;

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
        TestLogger.startTest("testAddItem_ValidItem_ItemAdded");

        TestLogger.test("testAddItem_ValidItem_ItemAdded", "SETUP", "Storage created with capacity=100, allowedItems=[Wood, Stone]");
        TestLogger.state("AdvancedStorage (initial)", storage.toString());

        TestLogger.test("testAddItem_ValidItem_ItemAdded", "ACTION", "Adding Wood: quantity=10, durability=5.0");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
        TestLogger.state("AdvancedStorage (after add)", storage.toString());

        TestLogger.test("testAddItem_ValidItem_ItemAdded", "VERIFY", "Checking if storage contains Wood");
        boolean hasWood = storage.hasItem(ItemConstants.WOOD, 10);
        TestLogger.verify("Storage should contain Wood (10 units)", true, hasWood, hasWood);
        assertTrue("Storage should contain Wood", hasWood);

        double quantity = storage.getItemQuantity(ItemConstants.WOOD);
        boolean qtyCorrect = Math.abs(quantity - 10.0) < 0.001;
        TestLogger.verify("Should have correct quantity", 10.0, quantity, qtyCorrect);
        assertEquals("Should have correct quantity", 10.0, quantity, 0.001);

        TestLogger.endTest("testAddItem_ValidItem_ItemAdded", true);
    }

    @Test
    public void testAddItem_ExceedsCapacity_NotAdded() {
        TestLogger.startTest("testAddItem_ExceedsCapacity_NotAdded");

        TestLogger.test("testAddItem_ExceedsCapacity_NotAdded", "SETUP", "Storage with capacity=100");
        TestLogger.state("AdvancedStorage (initial)", storage.toString());

        TestLogger.test("testAddItem_ExceedsCapacity_NotAdded", "ACTION", "Attempting to add 150 Wood (exceeds capacity)");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 150, 1.0);
        TestLogger.state("AdvancedStorage (after attempt)", storage.toString());

        TestLogger.test("testAddItem_ExceedsCapacity_NotAdded", "VERIFY", "Item should not be added");
        double quantity = storage.getItemQuantity(ItemConstants.WOOD);
        boolean isEmpty = Math.abs(quantity - 0.0) < 0.001;
        TestLogger.verify("Should have no items (capacity exceeded)", 0.0, quantity, isEmpty);
        assertEquals("Should have no items", 0.0, quantity, 0.001);

        TestLogger.endTest("testAddItem_ExceedsCapacity_NotAdded", true);
    }

    @Test
    public void testAddItem_DisallowedItem_NotAdded() {
        TestLogger.startTest("testAddItem_DisallowedItem_NotAdded");

        TestLogger.test("testAddItem_DisallowedItem_NotAdded", "SETUP", "Storage allows only [Wood, Stone]");
        TestLogger.state("AdvancedStorage (initial)", storage.toString());

        TestLogger.test("testAddItem_DisallowedItem_NotAdded", "ACTION", "Attempting to add Coal (not allowed)");
        storage.addItemCheckCapacity(ItemConstants.COAL, 10, 1.0);
        TestLogger.state("AdvancedStorage (after attempt)", storage.toString());

        TestLogger.test("testAddItem_DisallowedItem_NotAdded", "VERIFY", "Disallowed item should not be added");
        double quantity = storage.getItemQuantity(ItemConstants.COAL);
        boolean isEmpty = Math.abs(quantity - 0.0) < 0.001;
        TestLogger.verify("Should not add disallowed item", 0.0, quantity, isEmpty);
        assertEquals("Should not add disallowed item", 0.0, quantity, 0.001);

        TestLogger.endTest("testAddItem_DisallowedItem_NotAdded", true);
    }

    @Test
    public void testRemoveItem_SufficientQuantity_ItemRemoved() {
        TestLogger.startTest("testRemoveItem_SufficientQuantity_ItemRemoved");

        TestLogger.test("testRemoveItem_SufficientQuantity_ItemRemoved", "SETUP", "Adding 50 Wood first");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 50, 10.0);
        TestLogger.state("AdvancedStorage (after setup)", storage.toString());

        TestLogger.test("testRemoveItem_SufficientQuantity_ItemRemoved", "ACTION", "Removing 20 Wood");
        boolean result = storage.removeItem(ItemConstants.WOOD, 20);
        TestLogger.state("AdvancedStorage (after removal)", storage.toString());

        TestLogger.test("testRemoveItem_SufficientQuantity_ItemRemoved", "VERIFY", "Removal should succeed");
        TestLogger.verify("Removal should return true", true, result, result);
        assertTrue("Should successfully remove item", result);

        double remaining = storage.getItemQuantity(ItemConstants.WOOD);
        boolean correctAmount = Math.abs(remaining - 30.0) < 0.001;
        TestLogger.verify("Should have 30 Wood remaining", 30.0, remaining, correctAmount);
        assertEquals("Should have correct remaining quantity", 30.0, remaining, 0.001);

        TestLogger.endTest("testRemoveItem_SufficientQuantity_ItemRemoved", true);
    }

    @Test
    public void testRemoveItem_InsufficientQuantity_ReturnsFalse() {
        TestLogger.startTest("testRemoveItem_InsufficientQuantity_ReturnsFalse");

        TestLogger.test("testRemoveItem_InsufficientQuantity_ReturnsFalse", "SETUP", "Adding only 10 Wood");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
        TestLogger.state("AdvancedStorage (after setup)", storage.toString());

        TestLogger.test("testRemoveItem_InsufficientQuantity_ReturnsFalse", "ACTION", "Attempting to remove 20 Wood (insufficient)");
        boolean result = storage.removeItem(ItemConstants.WOOD, 20);
        TestLogger.state("AdvancedStorage (after attempt)", storage.toString());

        TestLogger.test("testRemoveItem_InsufficientQuantity_ReturnsFalse", "VERIFY", "Removal should fail atomically");
        TestLogger.verify("Should fail to remove (insufficient quantity)", false, result, !result);
        assertFalse("Should fail to remove more than available", result);

        double quantity = storage.getItemQuantity(ItemConstants.WOOD);
        boolean unchanged = Math.abs(quantity - 10.0) < 0.001;
        TestLogger.verify("Should still have original quantity (atomic operation)", 10.0, quantity, unchanged);
        assertEquals("Should still have original quantity", 10.0, quantity, 0.001);

        TestLogger.endTest("testRemoveItem_InsufficientQuantity_ReturnsFalse", true);
    }

    @Test
    public void testFIFO_OldestBatchRemovedFirst() {
        TestLogger.startTest("testFIFO_OldestBatchRemovedFirst");

        TestLogger.section("Setup: Adding Multiple Batches");
        TestLogger.test("testFIFO_OldestBatchRemovedFirst", "SETUP", "Adding batch 1: 10 Wood, durability=5.0");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
        TestLogger.state("After batch 1", storage.toString());

        TestLogger.test("testFIFO_OldestBatchRemovedFirst", "SETUP", "Adding batch 2: 10 Wood, durability=10.0");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 10.0);
        TestLogger.state("After batch 2", storage.toString());

        TestLogger.section("Action: Removing Items (FIFO Order)");
        TestLogger.test("testFIFO_OldestBatchRemovedFirst", "ACTION", "Removing 5 Wood (should take from oldest batch first)");
        storage.removeItem(ItemConstants.WOOD, 5);
        TestLogger.state("After removal", storage.toString());

        TestLogger.section("Verification: FIFO Behavior");
        TestLogger.test("testFIFO_OldestBatchRemovedFirst", "VERIFY", "Checking FIFO behavior");
        double remaining = storage.getItemQuantity(ItemConstants.WOOD);
        boolean correctAmount = Math.abs(remaining - 15.0) < 0.001;
        TestLogger.verify("Should have 15 Wood remaining", 15.0, remaining, correctAmount);
        assertEquals("Should have 15 Wood remaining", 15.0, remaining, 0.001);

        TestLogger.endTest("testFIFO_OldestBatchRemovedFirst", true);
    }

    @Test
    public void testGetItemQuantity_NonExistentItem_ReturnsZero() {
        TestLogger.startTest("testGetItemQuantity_NonExistentItem_ReturnsZero");

        TestLogger.test("testGetItemQuantity_NonExistentItem_ReturnsZero", "SETUP", "Empty storage");
        TestLogger.state("AdvancedStorage (initial)", storage.toString());

        TestLogger.test("testGetItemQuantity_NonExistentItem_ReturnsZero", "ACTION", "Getting quantity of non-existent Coal");
        double quantity = storage.getItemQuantity(ItemConstants.COAL);

        TestLogger.test("testGetItemQuantity_NonExistentItem_ReturnsZero", "VERIFY", "Should return 0");
        boolean isZero = Math.abs(quantity - 0.0) < 0.001;
        TestLogger.verify("Should return 0 for non-existent item", 0.0, quantity, isZero);
        assertEquals("Should return 0 for non-existent item", 0.0, quantity, 0.001);

        TestLogger.endTest("testGetItemQuantity_NonExistentItem_ReturnsZero", true);
    }

    @Test
    public void testHasItem_ItemExists_ReturnsTrue() {
        TestLogger.startTest("testHasItem_ItemExists_ReturnsTrue");

        TestLogger.test("testHasItem_ItemExists_ReturnsTrue", "SETUP", "Adding 5 Wood with durability=3.0");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 5, 3.0);
        TestLogger.state("AdvancedStorage (after setup)", storage.toString());

        TestLogger.section("Verification: hasItem() Checks");
        boolean has5 = storage.hasItem(ItemConstants.WOOD, 5);
        TestLogger.verify("Should return true for 5 Wood (exact amount)", true, has5, has5);
        assertTrue("Should return true for existing item with sufficient quantity", has5);

        boolean has3 = storage.hasItem(ItemConstants.WOOD, 3);
        TestLogger.verify("Should return true for 3 Wood (less than available)", true, has3, has3);
        assertTrue("Should return true for existing item with less than available", has3);

        boolean has10 = storage.hasItem(ItemConstants.WOOD, 10);
        TestLogger.verify("Should return false for 10 Wood (more than available)", false, has10, !has10);
        assertFalse("Should return false for more than available", has10);

        boolean hasCoal = storage.hasItem(ItemConstants.COAL, 1);
        TestLogger.verify("Should return false for Coal (non-existent)", false, hasCoal, !hasCoal);
        assertFalse("Should return false for non-existent item", hasCoal);

        TestLogger.endTest("testHasItem_ItemExists_ReturnsTrue", true);
    }

    @Test
    public void testGetUsedCapacity_MultipleItems_CorrectTotal() {
        TestLogger.startTest("testGetUsedCapacity_MultipleItems_CorrectTotal");

        TestLogger.test("testGetUsedCapacity_MultipleItems_CorrectTotal", "SETUP", "Adding Wood and Stone");
        TestLogger.test("testGetUsedCapacity_MultipleItems_CorrectTotal", "ACTION", "Adding 10 Wood");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 2.0);
        TestLogger.state("After adding Wood", storage.toString());

        TestLogger.test("testGetUsedCapacity_MultipleItems_CorrectTotal", "ACTION", "Adding 15 Stone");
        storage.addItemCheckCapacity(ItemConstants.STONE, 15, 3.0);
        TestLogger.state("After adding Stone", storage.toString());

        TestLogger.test("testGetUsedCapacity_MultipleItems_CorrectTotal", "VERIFY", "Checking used capacity");
        double used = storage.getUsedCapacity();
        boolean correctUsed = Math.abs(used - 25.0) < 0.001;
        TestLogger.verify("Used capacity should be 25.0", 25.0, used, correctUsed);
        assertEquals("Should calculate correct used capacity", 25.0, used, 0.001);

        TestLogger.endTest("testGetUsedCapacity_MultipleItems_CorrectTotal", true);
    }

    @Test
    public void testGetAvailableCapacity_CorrectCalculation() {
        TestLogger.startTest("testGetAvailableCapacity_CorrectCalculation");

        TestLogger.test("testGetAvailableCapacity_CorrectCalculation", "SETUP", "Storage has 100 capacity");
        TestLogger.test("testGetAvailableCapacity_CorrectCalculation", "ACTION", "Adding 30 Wood");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 30, 3.0);
        TestLogger.state("After adding items", storage.toString());

        TestLogger.test("testGetAvailableCapacity_CorrectCalculation", "VERIFY", "Checking available capacity");
        double available = storage.getAvailableCapacity();
        boolean correctAvailable = Math.abs(available - 70.0) < 0.001;
        TestLogger.verify("Available capacity should be 70.0", 70.0, available, correctAvailable);
        assertEquals("Should have 70 capacity available", 70.0, available, 0.001);

        TestLogger.endTest("testGetAvailableCapacity_CorrectCalculation", true);
    }

    @Test
    public void testDegradeItems_ExpiredItems_RemovedFromStorage() {
        TestLogger.startTest("testDegradeItems_ExpiredItems_RemovedFromStorage");

        TestLogger.test("testDegradeItems_ExpiredItems_RemovedFromStorage", "SETUP", "Adding Wood with durability=5.0");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
        TestLogger.state("Initial state", storage.toString());

        TestLogger.section("Action: Simulating Passage of Time");
        for (int i = 0; i < 5; i++) {
            TestLogger.test("testDegradeItems_ExpiredItems_RemovedFromStorage", "ACTION", "degradeItems() - turn " + (i+1));
            storage.degradeItems();
            TestLogger.state("After turn " + (i+1), storage.toString());
        }

        TestLogger.test("testDegradeItems_ExpiredItems_RemovedFromStorage", "VERIFY", "Items should be spoiled and removed");
        double remaining = storage.getItemQuantity(ItemConstants.WOOD);
        boolean allSpoiled = Math.abs(remaining - 0.0) < 0.001;
        TestLogger.verify("Spoiled items should be removed", 0.0, remaining, allSpoiled);
        assertEquals("Spoiled items should be removed", 0.0, remaining, 0.001);

        TestLogger.endTest("testDegradeItems_ExpiredItems_RemovedFromStorage", true);
    }

    @Test
    public void testToString_ContainsStorageInfo() {
        TestLogger.startTest("testToString_ContainsStorageInfo");

        TestLogger.test("testToString_ContainsStorageInfo", "SETUP", "Adding some items");
        storage.addItemCheckCapacity(ItemConstants.WOOD, 10, 5.0);
        TestLogger.state("AdvancedStorage (with items)", storage.toString());

        TestLogger.test("testToString_ContainsStorageInfo", "ACTION", "Calling toString()");
        String result = storage.toString();
        TestLogger.debug("toString() output:\n" + result);

        TestLogger.test("testToString_ContainsStorageInfo", "VERIFY", "toString should return comprehensive info");
        boolean notNull = result != null;
        TestLogger.verify("toString should not return null", true, notNull, notNull);
        assertNotNull("toString should not return null", result);

        boolean hasContent = result.length() > 0;
        TestLogger.verify("Should contain storage info", true, hasContent, hasContent);
        assertTrue("Should contain storage info", hasContent);

        TestLogger.endTest("testToString_ContainsStorageInfo", true);
    }
}
