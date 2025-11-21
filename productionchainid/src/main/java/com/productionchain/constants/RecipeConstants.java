package com.productionchain.constants;

/**
 * Constants for recipe names and categories used throughout the application.
 * Centralizing these constants prevents typos and makes refactoring easier.
 */
public final class RecipeConstants {

    // Prevent instantiation
    private RecipeConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    // Recipe Categories
    public static final String CATEGORY_BASIC_SILVICULTURE = "Basic silviculture";
    public static final String CATEGORY_BASIC_MINING = "Basic mining";
    public static final String CATEGORY_BASIC_KILN = "Basic kiln";

    // Recipe Names
    public static final String RECIPE_CHOP_WOOD = "Chop wood";
    public static final String RECIPE_MINE_STONE = "Mine stone";
    public static final String RECIPE_MINE_COAL = "Mine coal";
    public static final String RECIPE_CHARCOAL = "Charcoal";
}
