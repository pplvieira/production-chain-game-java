package com.productionchain.buildings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.mechanics.RecipeHandler;



//@JsonIgnoreProperties(ignoreUnknown = true) // REMOVED THIS ONE
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ProductionBuildingType.class, name = "ProductionBuildingType"),
    //@JsonSubTypes.Type(value = com.productionchain.buildingtypes.ProductionBuildingType.class, name = "Production22")
})
public abstract class BuildingType {
    
    String name;
    String BuildingClass; // MAKE THIS ENUM
    String description;
    String category;
    double baseEfficiency;
    double baseDegradationRate;
    List<IngredientPair> buildingCost;
    
    int n_upgrades;
    List<String> upgrades; // ALLOWED UPGRADES
    //List<UpgradeRecipesPair> upgradeRecipesPairs;

    RecipeHandler recipeHandler;



    //int baseNumOperations;

    public BuildingType() {} // Default constructor required for Jackson

    // empty constructor
    // public BuildingType(int n_upgrades){
    //     this.name = "";
    //     this.description = "";
    //     this.category = "";
        
    //     this.baseEfficiency = 1;
    //     this.baseDegradationRate = 1;
    //     this.buildingCost = new ArrayList<>();

    //     this.n_upgrades = n_upgrades;
    //     this.upgrades   = new ArrayList<>(n_upgrades);
    //     this.recipeHandler = new RecipeHandler();
    //     //this.upgradeRecipesPairs = (ArrayList<UpgradeRecipesPair>) Stream.generate(UpgradeRecipesPair::new).limit(n_upgrades).collect(Collectors.toList());
    //     //new ArrayList<>(n_upgrades);
    //     //this.baseNumOperations = 1;
    // }

    
    public BuildingType(int n_upgrades, String buildingClass){
        this.name = "";
        this.description = "";
        this.category = "";

        this.baseEfficiency = 0;
        this.baseDegradationRate = 0;
        this.buildingCost = new ArrayList<>();

        this.n_upgrades = n_upgrades;
        this.upgrades   = new ArrayList<>(n_upgrades);
        this.recipeHandler = new RecipeHandler();
        //this.upgradeRecipesPairs = (ArrayList<UpgradeRecipesPair>) Stream.generate(UpgradeRecipesPair::new).limit(n_upgrades).collect(Collectors.toList());
        //new ArrayList<>(n_upgrades);
        //this.baseNumOperations = 1;
    }

    @JsonCreator
    public BuildingType(@JsonProperty("name") String name, 
        @JsonProperty("description") String description,
        @JsonProperty("category") String category,
        @JsonProperty("baseEfficiency") double baseEfficiency,
        @JsonProperty("baseDegradationRate") double baseDegradationRate,
        @JsonProperty("buildingCost") List<IngredientPair> buildingCost,
        @JsonProperty("n_upgrades") int n_upgrades,
        @JsonProperty("upgrades") ArrayList<String> upgrades,
        @JsonProperty("upgrades") RecipeHandler recipeHandler){

        this.name = name;
        this.description = description;
        this.category = category;
        
        this.baseEfficiency = baseEfficiency;
        this.baseDegradationRate = baseDegradationRate;
        this.buildingCost = buildingCost;

        this.n_upgrades = n_upgrades;
        this.upgrades   = upgrades;

        this.recipeHandler = recipeHandler;
    }

    // WAS UNCOMMENTED
    public String getType() {
        System.out.println("[## @building type list] " + this.getClass().getSimpleName());
        return this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() { // MODIFIED !!!
        return category;
        //return category;
    }

    /* 
    public ArrayList<UpgradeRecipesPair> getUpgradeRecipesPairs() {
        return upgradeRecipesPairs;
    }

    public int getBaseNumOperations() {
        return baseNumOperations;
    }
    */

    public double getbaseEfficiency() {
        return baseEfficiency;
    }

    public double getbaseDegradationRate() {
        return baseDegradationRate;
    }

    public List<IngredientPair> getBuildingCost() {
        return buildingCost;
    }


    public int getN_upgrades() {
        return n_upgrades;
    }

    public List<String> getUpgrades() {
        return upgrades;
    }
    
    public RecipeHandler getRecipeHandler() {
        return recipeHandler;
    }


    // ABBREVIATORS
    public boolean canRunRecipe(Recipe recipe) { return recipeHandler.canRunRecipe(recipe); }

    @JsonIgnore
    public List<Recipe> getAvailableRecipes() { return recipeHandler.getAvailableRecipes(); }


    // THIS NOW SHOWS UP IN THE RECIPE HANDLER
    // // GETTERS AND SETTERS 

    // public int getBaseNumOperations() { return baseNumOperations;}
    // public List<String> getBaseRecipeCategories() {return baseRecipeCategories;}

    // public void setBaseNumOperations(int baseNumOperations) {this.baseNumOperations = baseNumOperations;}
    // public void setBaseRecipeCategories(ArrayList<String> baseRecipeCategories) {this.baseRecipeCategories = baseRecipeCategories;}


    // public boolean canRunRecipe(Recipe recipe) {
    //     return baseRecipeCategories.contains(recipe.getCategory());
    // }

    // public List<Recipe> getAvailableRecipes() {
    //     return RecipeRegistry.getRecipes().stream()
    //         .filter(recipe -> this.baseRecipeCategories.contains(recipe.getCategory()))
    //         .collect(Collectors.toList());
    //     // return baseRecipeCategories.stream()
    //     //     .flatMap(cat -> RecipeRegistry.getRecipesByCategory(category).stream()) // both were "category"
    //     //     .collect(Collectors.toList());
    // }



    @Override
    public String toString(){
        return "\nBuilding: " + this.name + " | "  + this.category + " | " + this.description + " | " + this.baseDegradationRate + " ~ " + this.baseEfficiency;
    }

}


