package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.constants.RecipeConstants;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for RecipeRegistry.
 * Tests recipe registration, retrieval, and category filtering.
 */
public class RecipeRegistryTest {

    @Before
    public void setUp() {
        // Clear registry before each test
        RecipeRegistry.clear();
    }

    @Test
    public void testAddRecipe() {
        // Create and add a test recipe
        Recipe chopWood = new Recipe(
            RecipeConstants.RECIPE_CHOP_WOOD,
            RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "Chop trees for wood",
            0, 1,
            new IngredientsList(),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))),
            1.0,
            true
        );
        RecipeRegistry.addRecipe(chopWood);

        // Verify recipe was added
        Recipe retrieved = RecipeRegistry.getRecipeByName(RecipeConstants.RECIPE_CHOP_WOOD);
        assertNotNull("Recipe should be retrievable after adding", retrieved);
        assertEquals("Recipe name should match", RecipeConstants.RECIPE_CHOP_WOOD, retrieved.getName());
        assertEquals("Recipe category should match", RecipeConstants.CATEGORY_BASIC_SILVICULTURE, retrieved.getCategory());
    }

    @Test
    public void testGetRecipesByCategory() {
        // Add recipes in different categories
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_CHOP_WOOD, RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))), 1.0, true));
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_MINE_STONE, RecipeConstants.CATEGORY_BASIC_MINING,
            "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 10)))), 1.0, true));
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_MINE_COAL, RecipeConstants.CATEGORY_BASIC_MINING,
            "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 5)))), 1.0, true));

        // Test category filtering
        List<Recipe> silvicultureRecipes = RecipeRegistry.getRecipesByCategory(RecipeConstants.CATEGORY_BASIC_SILVICULTURE);
        assertEquals("Should have 1 silviculture recipe", 1, silvicultureRecipes.size());

        List<Recipe> miningRecipes = RecipeRegistry.getRecipesByCategory(RecipeConstants.CATEGORY_BASIC_MINING);
        assertEquals("Should have 2 mining recipes", 2, miningRecipes.size());
    }

    @Test
    public void testGetRecipeByName_NonExistent() {
        // Test retrieving a non-existent recipe
        Recipe recipe = RecipeRegistry.getRecipeByName("NonExistentRecipe");
        assertNull("Non-existent recipe should return null", recipe);
    }

    @Test
    public void testRecipeExists() {
        // Add a recipe
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_CHOP_WOOD, RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))), 1.0, true));

        // Test existence check
        assertTrue("Chop wood recipe should exist", RecipeRegistry.recipeExists(RecipeConstants.RECIPE_CHOP_WOOD));
        assertFalse("NonExistent recipe should not exist", RecipeRegistry.recipeExists("NonExistent"));
    }
}
