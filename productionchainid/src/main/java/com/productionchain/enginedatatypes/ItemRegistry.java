package com.productionchain.enginedatatypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ItemRegistry {
    private static final Map<String, ItemType> itemTypes = new HashMap<>();



    // GETTERS
    public static ItemType getItemType(String name) {
        return itemTypes.get(name);
    }

    /** Alias for getItemType() - retrieves item by name */
    public static ItemType getItemByName(String name) {
        return getItemType(name);
    }

    public static Map<String, ItemType> getItemTypes() {
        return itemTypes;
    }

    public static boolean isValidItem(String name) {
        return itemTypes.containsKey(name);
    }

    /** Alias for isValidItem() - checks if item exists */
    public static boolean itemExists(String name) {
        return isValidItem(name);
    }

    /** Returns a list of all the item names of the chosen category  */
    public ArrayList<String> getItemsByCategory(String category){
        ArrayList<String> itemNames = new ArrayList<>();
        
        for (Map.Entry<String, ItemType> entry : itemTypes.entrySet()) {
            if (entry.getValue().getCategory().equals(category)) {
                itemNames.add(entry.getKey());
            }
        }
        return itemNames;
    }


    // SETTERS
    public static void addItem(ItemType itemType){
        ItemRegistry.itemTypes.put(itemType.getName(), itemType);
    }

    /** Clears all items from the registry - useful for testing */
    public static void clear() {
        itemTypes.clear();
    }



    // LOAD FROM JSON file
    public static void loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, ItemType> items = mapper.readValue(new File(filePath), new TypeReference<Map<String, ItemType>>() {});
            itemTypes.clear();  // Clear existing items before loading
            itemTypes.putAll(items);
            System.out.println("ItemRegistry successfully loaded from JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load ItemRegistry from JSON.");
        }
    }

    // Write to JSON file
    public static void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), ItemRegistry.itemTypes); //writerWithDefaultPrettyPrinter()
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nItem registry: ( )\n");
        itemTypes.forEach((item, itemType) -> {
            result.append("  ").append(item).append("| ");
            result.append(itemType.getCategory());
            result.append(String.format(" - [%.3f kgs, %.3f m^2, %.0f turns] ", itemType.getWeight(), itemType.getStorageSpace(), itemType.getHowLongToGoBad()));
            result.append("\n");
        });
        return result.toString();
    }



    public static void main(String[] args) throws IOException {
        // USE THIS TO DEFINE ALL THE ITEMS!!
        //ItemRegistry itemRegistry = new ItemRegistry();
        
        ItemRegistry.addItem(new ItemType("Wood", "Raw Material", 1.0, 10.0, 50.0, true, true, true));
        ItemRegistry.addItem(new ItemType("Coal", "Raw Material", 1.0, 8.0, 100.0, true, true, true));

        //ItemRegistry.itemTypes.put("Wood", new ItemType("Wood", "Raw Material", 1.0, 10.0, 50.0, true, true, true));

        //System.out.println(ItemRegistry);

        ItemRegistry.saveToJson("ItemRegistry_test.json");

        ItemRegistry.loadFromJson("ItemRegistry_test.json");

    }

    // public static class loadFromJson {

    //     public loadFromJson() {
    //     }
    // }
}
