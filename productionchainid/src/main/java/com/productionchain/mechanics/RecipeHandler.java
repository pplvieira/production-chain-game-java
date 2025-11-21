package com.productionchain.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;

// IMPLEMENTED BY BUILDING TYPES
public class RecipeHandler {
    
    int baseNumOperations;
    ArrayList<String> baseRecipeCategories;


    public RecipeHandler(){
        this.baseNumOperations = 0;
        this.baseRecipeCategories = new ArrayList<>();
    }

    public RecipeHandler(int baseNumOperations){
        this.baseNumOperations = baseNumOperations;
        this.baseRecipeCategories = new ArrayList<>();
    }

    public RecipeHandler(
        @JsonProperty("baseNumOperations") int baseNumOperations,
        @JsonProperty("baseRecipeCategories") ArrayList<String> baseRecipeCategories){
        this.baseNumOperations    = baseNumOperations;
        this.baseRecipeCategories = baseRecipeCategories;
    }



    // READ JSON
    ///* 
    // public static List<ProductionBuildingType> loadProductionBuildingTypes(String filePath) throws IOException {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     RecipeHandler config = objectMapper.readValue(new File(filePath), RecipeHandler.class);
    //     return config.getBuildings(); // Assuming the StagedBuildingConfig class maps correctly
    // }


    // // WRITE JSON
    // public static void saveProductionBuildingTypes(ProductionBuildingConfig1 config, String filePath) throws IOException {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), config);
    // }


    // GETTERS AND SETTERS
    public int getBaseNumOperations() { return baseNumOperations;}
    public List<String> getBaseRecipeCategories() {return baseRecipeCategories;}

    public void setBaseNumOperations(int baseNumOperations) {this.baseNumOperations = baseNumOperations;}
    public void setBaseRecipeCategories(ArrayList<String> baseRecipeCategories) {this.baseRecipeCategories = baseRecipeCategories;}


    public boolean canRunRecipe(Recipe recipe) {
        return baseRecipeCategories.contains(recipe.getCategory());
    }

    @JsonIgnore
    public List<Recipe> getAvailableRecipes() {
        return RecipeRegistry.getRecipes().stream()
            .filter(recipe -> this.baseRecipeCategories.contains(recipe.getCategory()))
            .collect(Collectors.toList());
        // return baseRecipeCategories.stream()
        //     .flatMap(cat -> RecipeRegistry.getRecipesByCategory(category).stream()) // both were "category"
        //     .collect(Collectors.toList());
    }
}





// class ProductionBuildingConfig1 { //ORIGINAL
//     private List<ProductionBuildingType> buildings;

//     // CONSTRUCTOR
//     public ProductionBuildingConfig1(){ this.buildings = new ArrayList<>(); }
//     public ProductionBuildingConfig1(List<ProductionBuildingType> buildings){ this.buildings = buildings; }

//     // Getters and Setters
//     public List<ProductionBuildingType> getBuildings() { return buildings; }
//     public void setBuildings(List<ProductionBuildingType> buildings) { this.buildings = buildings; }
//     public void addBuilding(ProductionBuildingType building) { this.buildings.add(building); }
// }
