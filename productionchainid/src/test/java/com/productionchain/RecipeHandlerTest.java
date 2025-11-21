package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.productionchain.mechanics.RecipeHandler;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.constants.RecipeConstants;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for RecipeHandler class.
 * Tests recipe availability and validation for buildings.
 */
public class RecipeHandlerTest {

    @Before
    public void setUp() {
        // Clear and setup recipe registry for testing
        RecipeRegistry.clear();

        // Add test recipes
        RecipeRegistry.addRecipe(new Recipe(
            RecipeConstants.RECIPE_CHOP_WOOD,
            RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "--", 0, 1,
            new IngredientsList(),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))),
            1.0, true
        ));

        RecipeRegistry.addRecipe(new Recipe(
            RecipeConstants.RECIPE_MINE_STONE,
            RecipeConstants.CATEGORY_BASIC_MINING,
            "--", 0, 1,
            new IngredientsList(),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 10)))),
            1.0, true
        ));

        RecipeRegistry.addRecipe(new Recipe(
            RecipeConstants.RECIPE_CHARCOAL,
            RecipeConstants.CATEGORY_BASIC_KILN,
            "--", 1, 1,
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 2)))),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 3)))),
            1.0, true
        ));
    }

    @Test
    public void testRecipeHandler_Creation_WithParameters() {
        // Test creating RecipeHandler with operations and categories
        RecipeHandler handler = new RecipeHandler(
            2,
            new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE))
        );

        assertEquals("Should have 2 operations", 2, handler.getBaseNumOperations());
        assertEquals("Should have 1 category", 1, handler.getBaseRecipeCategories().size());
        assertTrue("Should contain silviculture category",
                  handler.getBaseRecipeCategories().contains(RecipeConstants.CATEGORY_BASIC_SILVICULTURE));
    }

    @Test
    public void testRecipeHandler_EmptyConstructor_DefaultValues() {
        // Test empty constructor
        RecipeHandler handler = new RecipeHandler();

        assertNotNull("Operations should not be null", handler.getBaseNumOperations());
        assertNotNull("Categories should not be null", handler.getBaseRecipeCategories());
    }

    @Test
    public void testRecipeHandler_CanRunRecipe_ValidCategory_ReturnsTrue() {
        // Test that handler can run recipes in its categories
        RecipeHandler handler = new RecipeHandler(
            1,
            new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE))
        );

        Recipe chopWood = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_CHOP_WOOD);

        assertTrue("Should be able to run silviculture recipe",
                  handler.canRunRecipe(chopWood));
    }

    @Test
    public void testRecipeHandler_CanRunRecipe_InvalidCategory_ReturnsFalse() {
        // Test that handler cannot run recipes outside its categories
        RecipeHandler handler = new RecipeHandler(
            1,
            new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE))
        );

        Recipe mineStone = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_MINE_STONE);

        assertFalse("Should not be able to run mining recipe",
                   handler.canRunRecipe(mineStone));
    }

    @Test
    public void testRecipeHandler_MultipleCategories_CanRunAll() {
        // Test handler with multiple categories
        RecipeHandler handler = new RecipeHandler(
            2,
            new ArrayList<>(List.of(
                RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
                RecipeConstants.CATEGORY_BASIC_KILN
            ))
        );

        Recipe chopWood = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_CHOP_WOOD);
        Recipe charcoal = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_CHARCOAL);
        Recipe mineStone = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_MINE_STONE);

        assertTrue("Should run silviculture recipe", handler.canRunRecipe(chopWood));
        assertTrue("Should run kiln recipe", handler.canRunRecipe(charcoal));
        assertFalse("Should not run mining recipe", handler.canRunRecipe(mineStone));
    }

    @Test
    public void testRecipeHandler_GetAvailableRecipes_FiltersCorrectly() {
        // Test getting available recipes
        RecipeHandler handler = new RecipeHandler(
            1,
            new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_MINING))
        );

        List<Recipe> available = handler.getAvailableRecipes();

        assertEquals("Should have 1 available recipe", 1, available.size());
        assertEquals("Should be the mining recipe",
                    RecipeConstants.RECIPE_MINE_STONE,
                    available.get(0).getName());
    }

    @Test
    public void testRecipeHandler_GetAvailableRecipes_EmptyForNoMatchingCategories() {
        // Test that no recipes are available if no categories match
        RecipeHandler handler = new RecipeHandler(
            1,
            new ArrayList<>(List.of("NonExistentCategory"))
        );

        List<Recipe> available = handler.getAvailableRecipes();

        assertEquals("Should have no available recipes", 0, available.size());
    }

    @Test
    public void testRecipeHandler_SetBaseNumOperations_UpdatesValue() {
        // Test setting number of operations
        RecipeHandler handler = new RecipeHandler();

        handler.setBaseNumOperations(5);

        assertEquals("Operations should be updated to 5", 5, handler.getBaseNumOperations());
    }

    @Test
    public void testRecipeHandler_SetBaseRecipeCategories_UpdatesCategories() {
        // Test setting categories
        RecipeHandler handler = new RecipeHandler();

        ArrayList<String> newCategories = new ArrayList<>(
            List.of(RecipeConstants.CATEGORY_BASIC_MINING)
        );
        handler.setBaseRecipeCategories(newCategories);

        assertEquals("Should have 1 category", 1, handler.getBaseRecipeCategories().size());
        assertTrue("Should contain mining category",
                  handler.getBaseRecipeCategories().contains(RecipeConstants.CATEGORY_BASIC_MINING));
    }

    @Test
    public void testRecipeHandler_MultipleOperations_CorrectCount() {
        // Test handler with multiple operations
        RecipeHandler handler = new RecipeHandler(
            3,
            new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE))
        );

        assertEquals("Should have 3 operations", 3, handler.getBaseNumOperations());
        // In a full implementation, this would allow running 3 recipes simultaneously
    }
}
