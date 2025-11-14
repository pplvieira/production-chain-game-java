package com.productionchain.enginedatatypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RecipeRegistry {
    //private static final List<Recipe> recipes = new ArrayList<>();
    private static final List<Recipe> recipes = new ArrayList<>();


    public static void addRecipe(Recipe recipe){
        RecipeRegistry.recipes.add(recipe);
    }


    public static List<Recipe> getRecipes(){
        return recipes;
    }


    public static Recipe getRecipeByName(String name) {
        return recipes.stream()
        .filter(recipe -> recipe.getName().equals(name)) // Match name
        .findFirst() // Get the first match
        .orElse(null);
    }


    public static List<Recipe> getRecipesByCategory(String category) {
        return recipes.stream()
            .filter(recipe -> recipe.getCategory().equals(category))
            .collect(Collectors.toList());
    }




    // LOAD FROM JSON file
    public static void loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        recipes.clear(); // to avoid duplicates
        try {
            List<Recipe> read_recipes = mapper.readValue(new File(filePath), new TypeReference<List<Recipe>>() {});
            for (Recipe recipe : read_recipes) {
                //this.recipes.put(recipe.getName(), recipe);
                recipes.add(recipe);
            }
            System.out.println("RecipeRegistry successfully loaded from JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load RecipeRegistry from JSON.");
        }
    }

    // Write to JSON file
    public static void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), RecipeRegistry.recipes); //writerWithDefaultPrettyPrinter()
    }




    public static void main(String[] args) throws IOException {
        // USE THIS TO DEFINE ALL THE RECIPES!!

        // // READ
        // System.out.println(RecipeRegistry.recipes.size());
        // RecipeRegistry.loadFromJson("RecipeRegistry_test.json");
        // System.out.println(RecipeRegistry.recipes.get(0));
        // System.out.println(RecipeRegistry.recipes.size());
        
        RecipeRegistry.addRecipe(new Recipe("Chop wood", "Basic silviculture", "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair("Wood", 1)))), 1, true));
        RecipeRegistry.addRecipe(new Recipe("Mine stone", "Basic mining", "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair("Stone", 10)))), 1, true));

        System.out.println(RecipeRegistry.recipes.get(0));
        System.out.println(RecipeRegistry.recipes.size());

        RecipeRegistry.saveToJson("RecipeRegistry_test.json");


    }

}
