package com.productionchain.constants;

/**
 * Constants for item names used throughout the application.
 * Centralizing these constants prevents typos and makes refactoring easier.
 */
public final class ItemConstants {

    // Prevent instantiation
    private ItemConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    // Raw Materials
    public static final String WOOD = "Wood";
    public static final String STONE = "Stone";
    public static final String COAL = "Coal";
    public static final String STICK = "Stick";

    // Item Categories
    public static final String CATEGORY_RAW_MATERIAL = "Raw Material";
    public static final String CATEGORY_PROCESSED = "Processed";
    public static final String CATEGORY_TOOL = "Tool";
}
