package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Test;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.constants.RecipeConstants;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for Recipe class.
 * Tests recipe creation, properties, and validation.
 */
public class RecipeTest {

    @Test
    public void testRecipe_Creation_AllPropertiesSet() {
        // Create recipe with all properties
        IngredientsList ingredients = new IngredientsList();
        IngredientsList outputs = new IngredientsList(
            new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))
        );

        Recipe recipe = new Recipe(
            RecipeConstants.RECIPE_CHOP_WOOD,
            RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "Chop trees to get wood",
            0,  // no ingredients needed
            1,  // 1 output
            ingredients,
            outputs,
            1.0,    // duration
            true    // enabled
        );

        assertEquals("Name should match", RecipeConstants.RECIPE_CHOP_WOOD, recipe.getName());
        assertEquals("Category should match", RecipeConstants.CATEGORY_BASIC_SILVICULTURE, recipe.getCategory());
        assertEquals("Description should match", "Chop trees to get wood", recipe.getDescription());
        assertEquals("Should have 0 ingredients", 0, recipe.getN_ingredients());
        assertEquals("Should have 1 output", 1, recipe.getN_outputs());
        assertEquals("Duration should be 1.0", 1.0, recipe.getDuration(), 0.001);
        assertTrue("Recipe should be enabled", recipe.isEnabled());
    }

    @Test
    public void testRecipe_WithIngredients_CorrectConfiguration() {
        // Create recipe that requires ingredients
        IngredientsList ingredients = new IngredientsList(
            new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 2)))
        );
        IngredientsList outputs = new IngredientsList(
            new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 3)))
        );

        Recipe recipe = new Recipe(
            RecipeConstants.RECIPE_CHARCOAL,
            RecipeConstants.CATEGORY_BASIC_KILN,
            "Convert wood to charcoal",
            1,  // 1 ingredient
            1,  // 1 output
            ingredients,
            outputs,
            2.0,    // takes 2 turns
            true
        );

        assertEquals("Should have 1 ingredient type", 1, recipe.getN_ingredients());
        assertEquals("Should require Wood", ItemConstants.WOOD, recipe.getIngredientslist().get(0).getItem_name());
        assertEquals("Should require 2 Wood", 2.0, recipe.getIngredientslist().get(0).getCount(), 0.001);

        assertEquals("Should produce 1 output type", 1, recipe.getN_outputs());
        assertEquals("Should produce Coal", ItemConstants.COAL, recipe.getOutputslist().get(0).getItem_name());
        assertEquals("Should produce 3 Coal", 3.0, recipe.getOutputslist().get(0).getCount(), 0.001);
    }

    @Test
    public void testRecipe_MultipleIngredients_AllStored() {
        // Create recipe with multiple ingredients
        IngredientsList ingredients = new IngredientsList();
        ingredients.add(new IngredientPair(ItemConstants.WOOD, 5));
        ingredients.add(new IngredientPair(ItemConstants.STONE, 3));

        IngredientsList outputs = new IngredientsList(
            new ArrayList<>(List.of(new IngredientPair("Tool", 1)))
        );

        Recipe recipe = new Recipe(
            "Craft Tool",
            "Crafting",
            "Create a tool",
            2,  // 2 ingredients
            1,  // 1 output
            ingredients,
            outputs,
            3.0,
            true
        );

        assertEquals("Should have 2 ingredient types", 2, recipe.getN_ingredients());
        assertEquals("First ingredient should be Wood", ItemConstants.WOOD, recipe.getIngredientslist().get(0).getItem_name());
        assertEquals("Second ingredient should be Stone", ItemConstants.STONE, recipe.getIngredientslist().get(1).getItem_name());
    }

    @Test
    public void testRecipe_DisabledRecipe_CorrectFlag() {
        // Create a disabled recipe
        Recipe recipe = new Recipe(
            "Test Recipe",
            "Test",
            "Disabled recipe",
            0, 0,
            new IngredientsList(),
            new IngredientsList(),
            1.0,
            false   // disabled
        );

        assertFalse("Recipe should be disabled", recipe.isEnabled());
    }

    @Test
    public void testRecipe_ToString_ContainsRecipeInfo() {
        // Test toString method
        IngredientsList ingredients = new IngredientsList();
        IngredientsList outputs = new IngredientsList(
            new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 10)))
        );

        Recipe recipe = new Recipe(
            RecipeConstants.RECIPE_MINE_STONE,
            RecipeConstants.CATEGORY_BASIC_MINING,
            "Mine stone from rocks",
            0, 1,
            ingredients,
            outputs,
            1.0,
            true
        );

        String result = recipe.toString();

        assertNotNull("toString should not return null", result);
        assertTrue("Should contain recipe name", result.contains(RecipeConstants.RECIPE_MINE_STONE));
        assertTrue("Should contain category", result.contains(RecipeConstants.CATEGORY_BASIC_MINING));
    }

    @Test
    public void testRecipe_EmptyConstructor_CreatesDefaultRecipe() {
        // Test empty constructor
        Recipe recipe = new Recipe(0, 0);

        assertEquals("Name should be empty", "", recipe.getName());
        assertEquals("Category should be empty", "", recipe.getCategory());
        assertEquals("Should have 0 ingredients", 0, recipe.getN_ingredients());
        assertEquals("Should have 0 outputs", 0, recipe.getN_outputs());
        assertEquals("Duration should be 0", 0.0, recipe.getDuration(), 0.001);
        assertFalse("Recipe should be disabled by default", recipe.isEnabled());
    }

    @Test
    public void testRecipe_GatheringRecipes_NoIngredients() {
        // Test gathering recipes (no ingredients needed)
        Recipe chopWood = new Recipe(
            RecipeConstants.RECIPE_CHOP_WOOD,
            RecipeConstants.CATEGORY_BASIC_SILVICULTURE,
            "--", 0, 1,
            new IngredientsList(),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))),
            1.0, true
        );

        Recipe mineStone = new Recipe(
            RecipeConstants.RECIPE_MINE_STONE,
            RecipeConstants.CATEGORY_BASIC_MINING,
            "--", 0, 1,
            new IngredientsList(),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 10)))),
            1.0, true
        );

        // Gathering recipes should have no ingredients
        assertEquals("Chop wood should need 0 ingredients", 0, chopWood.getN_ingredients());
        assertEquals("Mine stone should need 0 ingredients", 0, mineStone.getN_ingredients());

        // But should produce outputs
        assertTrue("Chop wood should produce output", chopWood.getN_outputs() > 0);
        assertTrue("Mine stone should produce output", mineStone.getN_outputs() > 0);
    }

    @Test
    public void testRecipe_ProcessingRecipes_RequireIngredients() {
        // Test processing recipes (require ingredients)
        Recipe charcoal = new Recipe(
            RecipeConstants.RECIPE_CHARCOAL,
            RecipeConstants.CATEGORY_BASIC_KILN,
            "--", 1, 1,
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 2)))),
            new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 3)))),
            1.0, true
        );

        // Processing recipes should have ingredients
        assertTrue("Charcoal recipe should need ingredients", charcoal.getN_ingredients() > 0);
        assertEquals("Should require Wood", ItemConstants.WOOD, charcoal.getIngredientslist().get(0).getItem_name());
    }
}
