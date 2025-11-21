package com.productionchain;

import static org.junit.Assert.*;
import org.junit.Test;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.constants.ItemConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for IngredientPair and IngredientsList classes.
 * Tests ingredient management for recipes.
 */
public class IngredientTest {

    @Test
    public void testIngredientPair_Creation_WithTwoParameters() {
        // Test creating IngredientPair with item name and count
        IngredientPair ingredient = new IngredientPair(ItemConstants.WOOD, 5);

        assertEquals("Item name should match", ItemConstants.WOOD, ingredient.getItem_name());
        assertEquals("Count should match", 5.0, ingredient.getCount(), 0.001);
        assertEquals("Probability should default to 1.0", 1.0, ingredient.getProbability(), 0.001);
    }

    @Test
    public void testIngredientPair_Creation_WithThreeParameters() {
        // Test creating IngredientPair with item name, count, and probability
        IngredientPair ingredient = new IngredientPair(ItemConstants.STONE, 10, 0.8);

        assertEquals("Item name should match", ItemConstants.STONE, ingredient.getItem_name());
        assertEquals("Count should match", 10.0, ingredient.getCount(), 0.001);
        assertEquals("Probability should match", 0.8, ingredient.getProbability(), 0.001);
    }

    @Test
    public void testIngredientPair_EmptyConstructor_DefaultValues() {
        // Test empty constructor sets defaults
        IngredientPair ingredient = new IngredientPair();

        assertEquals("Item name should be empty", "", ingredient.getItem_name());
        assertEquals("Count should be 1", 1.0, ingredient.getCount(), 0.001);
        assertEquals("Probability should be 1", 1.0, ingredient.getProbability(), 0.001);
    }

    @Test
    public void testIngredientsList_EmptyConstructor_CreatesEmptyList() {
        // Test empty constructor
        IngredientsList list = new IngredientsList();

        assertEquals("List should be empty", 0, list.size());
    }

    @Test
    public void testIngredientsList_WithCapacity_CreatesListWithCapacity() {
        // Test constructor with initial capacity
        IngredientsList list = new IngredientsList(5);

        assertEquals("List should be empty initially", 0, list.size());
    }

    @Test
    public void testIngredientsList_AddIngredient_IncreasesSize() {
        // Test adding ingredients
        IngredientsList list = new IngredientsList();
        IngredientPair wood = new IngredientPair(ItemConstants.WOOD, 3);

        list.add(wood);

        assertEquals("List should have 1 ingredient", 1, list.size());
        assertEquals("Should retrieve correct ingredient", ItemConstants.WOOD, list.get(0).getItem_name());
    }

    @Test
    public void testIngredientsList_AddMultiple_AllAdded() {
        // Test adding multiple ingredients
        IngredientsList list = new IngredientsList();
        list.add(new IngredientPair(ItemConstants.WOOD, 5));
        list.add(new IngredientPair(ItemConstants.STONE, 10));
        list.add(new IngredientPair(ItemConstants.COAL, 3));

        assertEquals("Should have 3 ingredients", 3, list.size());
        assertEquals("First ingredient should be Wood", ItemConstants.WOOD, list.get(0).getItem_name());
        assertEquals("Second ingredient should be Stone", ItemConstants.STONE, list.get(1).getItem_name());
        assertEquals("Third ingredient should be Coal", ItemConstants.COAL, list.get(2).getItem_name());
    }

    @Test
    public void testIngredientsList_Set_ReplacesIngredient() {
        // Test setting/replacing an ingredient
        IngredientsList list = new IngredientsList();
        list.add(new IngredientPair(ItemConstants.WOOD, 5));

        IngredientPair newIngredient = new IngredientPair(ItemConstants.STONE, 10);
        list.set(0, newIngredient);

        assertEquals("Should still have 1 ingredient", 1, list.size());
        assertEquals("Ingredient should be replaced", ItemConstants.STONE, list.get(0).getItem_name());
        assertEquals("Count should be updated", 10.0, list.get(0).getCount(), 0.001);
    }

    @Test
    public void testIngredientsList_GetIngredientslist_ReturnsCorrectList() {
        // Test getting the full list
        IngredientsList list = new IngredientsList();
        list.add(new IngredientPair(ItemConstants.WOOD, 5));
        list.add(new IngredientPair(ItemConstants.STONE, 10));

        List<IngredientPair> ingredients = list.getIngredientslist();

        assertNotNull("List should not be null", ingredients);
        assertEquals("Should have 2 ingredients", 2, ingredients.size());
    }

    @Test
    public void testIngredientsList_SetIngredientslist_ReplacesEntireList() {
        // Test setting the entire list
        IngredientsList list = new IngredientsList();
        list.add(new IngredientPair(ItemConstants.WOOD, 5));

        List<IngredientPair> newList = new ArrayList<>();
        newList.add(new IngredientPair(ItemConstants.STONE, 20));
        newList.add(new IngredientPair(ItemConstants.COAL, 15));

        list.setIngredientslist(newList);

        assertEquals("Should have 2 ingredients after replacement", 2, list.size());
        assertEquals("First should be Stone", ItemConstants.STONE, list.get(0).getItem_name());
        assertEquals("Second should be Coal", ItemConstants.COAL, list.get(1).getItem_name());
    }

    @Test
    public void testIngredientsList_ConstructorWithList_InitializesCorrectly() {
        // Test constructor that takes a list
        List<IngredientPair> ingredients = new ArrayList<>();
        ingredients.add(new IngredientPair(ItemConstants.WOOD, 5));
        ingredients.add(new IngredientPair(ItemConstants.STONE, 10));

        IngredientsList list = new IngredientsList(ingredients);

        assertEquals("Should have 2 ingredients", 2, list.size());
        assertEquals("First should be Wood", ItemConstants.WOOD, list.get(0).getItem_name());
    }
}
