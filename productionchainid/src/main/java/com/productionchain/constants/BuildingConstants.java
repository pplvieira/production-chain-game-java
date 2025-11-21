package com.productionchain.constants;

/**
 * Constants for building types and categories used throughout the application.
 * Centralizing these constants prevents typos and makes refactoring easier.
 */
public final class BuildingConstants {

    // Prevent instantiation
    private BuildingConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    // Building Types
    public static final String BUILDING_HUT = "Hut";
    public static final String BUILDING_MINE = "Mine";
    public static final String BUILDING_KILN = "Kiln";

    // Building Categories
    public static final String CATEGORY_FIELD = "Field";
    public static final String CATEGORY_PRODUCTION = "production";
    public static final String CATEGORY_ANIMAL = "Animal";
}
