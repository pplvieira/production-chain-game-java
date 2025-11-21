package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Test;
import com.productionchain.enginedatatypes.ItemType;
import com.productionchain.constants.ItemConstants;

/**
 * Unit tests for ItemType class.
 * Tests item properties and behavior.
 */
public class ItemTypeTest {

    @Test
    public void testItemType_Creation_AllPropertiesSet() {
        // Test creating an ItemType with all properties
        ItemType item = new ItemType(
            ItemConstants.WOOD,
            ItemConstants.CATEGORY_RAW_MATERIAL,
            1.0,    // storage space
            10.0,   // weight
            50.0,   // how long to go bad
            true,   // is durable
            true,   // is storable
            true    // is transportable
        );

        assertEquals("Name should match", ItemConstants.WOOD, item.getName());
        assertEquals("Category should match", ItemConstants.CATEGORY_RAW_MATERIAL, item.getCategory());
        assertEquals("Storage space should match", 1.0, item.getStorageSpace(), 0.001);
        assertEquals("Weight should match", 10.0, item.getWeight(), 0.001);
        assertEquals("Durability should match", 50.0, item.getHowLongToGoBad(), 0.001);
        assertTrue("Should be durable", item.getIsDurable());
        assertTrue("Should be storable", item.getIsStorable());
        assertTrue("Should be transportable", item.getIsTransportable());
    }

    @Test
    public void testItemType_NonDurableItem_CorrectFlags() {
        // Test creating a non-durable item (e.g., food)
        ItemType item = new ItemType(
            "Bread",
            "Food",
            0.5,    // storage space
            2.0,    // weight
            5.0,    // spoils quickly
            false,  // not durable
            true,   // is storable
            true    // is transportable
        );

        assertFalse("Should not be durable", item.getIsDurable());
        assertEquals("Should spoil in 5 turns", 5.0, item.getHowLongToGoBad(), 0.001);
    }

    @Test
    public void testItemType_NonStorableItem_CorrectFlag() {
        // Test creating a non-storable item
        ItemType item = new ItemType(
            "LiveAnimal",
            "Animal",
            10.0,
            50.0,
            100.0,
            true,
            false,  // cannot be stored
            true
        );

        assertFalse("Should not be storable", item.getIsStorable());
        assertTrue("Should be transportable", item.getIsTransportable());
    }

    @Test
    public void testItemType_ToString_ContainsItemInfo() {
        // Test toString method
        ItemType item = new ItemType(
            ItemConstants.STONE,
            ItemConstants.CATEGORY_RAW_MATERIAL,
            1.5,
            15.0,
            100.0,
            true,
            true,
            true
        );

        String result = item.toString();

        assertNotNull("toString should not return null", result);
        assertTrue("Should contain item info", result.length() > 0);
    }

    @Test
    public void testItemType_RawMaterials_StandardProperties() {
        // Test that raw materials have expected properties
        ItemType wood = new ItemType(
            ItemConstants.WOOD,
            ItemConstants.CATEGORY_RAW_MATERIAL,
            1.0, 10.0, 50.0, true, true, true
        );

        ItemType stone = new ItemType(
            ItemConstants.STONE,
            ItemConstants.CATEGORY_RAW_MATERIAL,
            1.0, 10.0, 50.0, true, true, true
        );

        ItemType coal = new ItemType(
            ItemConstants.COAL,
            ItemConstants.CATEGORY_RAW_MATERIAL,
            1.0, 8.0, 100.0, true, true, true
        );

        // All raw materials should be durable, storable, and transportable
        assertTrue("Wood should be durable", wood.getIsDurable());
        assertTrue("Stone should be storable", stone.getIsStorable());
        assertTrue("Coal should be transportable", coal.getIsTransportable());

        // Coal should last longer than wood
        assertTrue("Coal should last longer than Wood",
                   coal.getHowLongToGoBad() > wood.getHowLongToGoBad());
    }

    @Test
    public void testItemType_DifferentCategories_DistinctProperties() {
        // Test items from different categories have different properties
        ItemType rawMaterial = new ItemType(ItemConstants.WOOD, ItemConstants.CATEGORY_RAW_MATERIAL,
                                           1.0, 10.0, 50.0, true, true, true);

        ItemType tool = new ItemType("Axe", ItemConstants.CATEGORY_TOOL,
                                     2.0, 5.0, 1000.0, true, true, true);

        assertNotEquals("Different categories", rawMaterial.getCategory(), tool.getCategory());
        assertTrue("Tool should last much longer", tool.getHowLongToGoBad() > rawMaterial.getHowLongToGoBad());
    }
}
