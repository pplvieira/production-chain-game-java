package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Test;
import com.productionchain.constants.ItemConstants;
import com.productionchain.constants.RecipeConstants;
import com.productionchain.constants.BuildingConstants;

/**
 * Unit tests for constants classes.
 * Verifies that constants are properly defined and accessible.
 */
public class ConstantsTest {

    @Test
    public void testItemConstants() {
        // Verify item constants are defined
        assertNotNull("WOOD constant should exist", ItemConstants.WOOD);
        assertNotNull("STONE constant should exist", ItemConstants.STONE);
        assertNotNull("COAL constant should exist", ItemConstants.COAL);
        assertNotNull("STICK constant should exist", ItemConstants.STICK);

        // Verify they have expected values
        assertEquals("WOOD should equal 'Wood'", "Wood", ItemConstants.WOOD);
        assertEquals("STONE should equal 'Stone'", "Stone", ItemConstants.STONE);
        assertEquals("COAL should equal 'Coal'", "Coal", ItemConstants.COAL);
    }

    @Test
    public void testRecipeConstants() {
        // Verify recipe constants are defined
        assertNotNull("RECIPE_CHOP_WOOD should exist", RecipeConstants.RECIPE_CHOP_WOOD);
        assertNotNull("CATEGORY_BASIC_SILVICULTURE should exist", RecipeConstants.CATEGORY_BASIC_SILVICULTURE);
        assertNotNull("CATEGORY_BASIC_MINING should exist", RecipeConstants.CATEGORY_BASIC_MINING);

        // Verify they have expected values
        assertEquals("RECIPE_CHOP_WOOD should equal 'Chop wood'", "Chop wood", RecipeConstants.RECIPE_CHOP_WOOD);
        assertEquals("CATEGORY_BASIC_SILVICULTURE should equal 'Basic silviculture'", "Basic silviculture", RecipeConstants.CATEGORY_BASIC_SILVICULTURE);
    }

    @Test
    public void testBuildingConstants() {
        // Verify building constants are defined
        assertNotNull("BUILDING_HUT should exist", BuildingConstants.BUILDING_HUT);
        assertNotNull("BUILDING_MINE should exist", BuildingConstants.BUILDING_MINE);
        assertNotNull("BUILDING_KILN should exist", BuildingConstants.BUILDING_KILN);

        // Verify they have expected values
        assertEquals("BUILDING_HUT should equal 'Hut'", "Hut", BuildingConstants.BUILDING_HUT);
        assertEquals("BUILDING_MINE should equal 'Mine'", "Mine", BuildingConstants.BUILDING_MINE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testItemConstantsCannotBeInstantiated() throws Exception {
        // Verify constants class cannot be instantiated
        // First make the constructor accessible, then try to call it
        java.lang.reflect.Constructor<ItemConstants> constructor = ItemConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
