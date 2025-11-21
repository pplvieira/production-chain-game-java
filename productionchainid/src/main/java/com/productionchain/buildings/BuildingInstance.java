package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.ResourceLoader;
import com.productionchain.mechanics.AdvancedStorage;
import com.productionchain.mechanics.RecipeHandler;
import com.productionchain.mechanics.RecipeSystem;
// import com.productionchain.mechanics.Storage;



@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "instanceType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ProductionBuildingInstance.class, name = "ProductionBuildingInstance")
})
public abstract class BuildingInstance {

    @JsonIgnore
    BuildingType type; // Reference to the immutable BuildingType
    String typeName;
    String owner; // Owner of the building
    int x, y; // Position on the map
    double condition;

    //Storage storage;
    AdvancedStorage storage;
    // RecipeSystem recipeSystem;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    Recipe activeRecipe;

    
    public BuildingInstance() {} // Default constructor required for Jackson

    public BuildingInstance(BuildingType type, String owner, int x, int y, double storageCap) {
        this.type = type;
        // System.out.println(">>> BuildingInstance(BuildingType type)");
        // System.out.println(type);
        this.typeName = type.getName();
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.condition = 100;
        this.storage = new AdvancedStorage(storageCap, new ArrayList<>());
        //this.recipeSystem = new RecipeSystem();
        this.activeRecipe = null;
    }


    @JsonCreator
    public BuildingInstance(String typeName, String owner, int x, int y, double storageCap, Recipe activeRecipe) throws IOException {
        System.out.println(">>> BuildingInstance(String typeName)");
        /// LOAD BUILDING TYPE REGISTRY
        BuildingTypeList<ProductionBuildingType> prodBuildingTypeList = new BuildingTypeList<>(ProductionBuildingType.class);
        prodBuildingTypeList.loadFromJson(ResourceLoader.getResourcePath("buildingtypes/ProductionBuildings.json"));
        
        if( prodBuildingTypeList.getBuildingByName(typeName) == null){
            System.out.println("[ERROR]: building '" + typeName + "' doesn't exist in registry!");
        }
        this.type = prodBuildingTypeList.getBuildingByName(typeName);

        System.out.println("&&& teste: " + prodBuildingTypeList);
        System.out.println("&&& teste: " + prodBuildingTypeList.getBuildingByName(typeName));

        this.typeName = type.getName();
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.condition = 100;
        this.storage = new AdvancedStorage(storageCap, new ArrayList<>());
        this.activeRecipe = activeRecipe;
    }


    @JsonSetter("typeName") // Called when JSON assigns this field
    public void settypeName(String typeName) throws IOException {
        this.typeName = typeName;
        this.resolveType(); // Reconstruct type from registry
    }

    public void resolveType() throws IOException {
        BuildingTypeList<ProductionBuildingType> prodBuildingTypeList = new BuildingTypeList<>(ProductionBuildingType.class);
        prodBuildingTypeList.loadFromJson(ResourceLoader.getResourcePath("buildingtypes/ProductionBuildings.json"));
        this.type = prodBuildingTypeList.getBuildingByName(this.typeName);
    }


        
    
    /** Returns ProductionBuildingInstance or the respective class */
    public String getInstanceType() { 
        //System.out.println("[TESTE]: " + this.getClass().getSimpleName() );
        //return this.type.getCategory();
        return this.getClass().getSimpleName();
    }

    // public String getBuildingClass(){
    //     System.out.println("[22222]:" + this.type.getCategory() );
    //     if(this.type.getCategory().equals("production")){
    //         System.out.println("Production building");
    //     }
    //     return this.type.getCategory();
    // }
    

    // GETTERS
    //@JsonIgnore
    public BuildingType getType() { return type; }
    //public BuildingType getType(BuildingTypeList ItemTypeRegistry) { return ItemTypeRegistry.getBuildingByName(this.typeName); }
    public String gettypeName() { return typeName; }
    public String getOwner() { return owner; }
    public int getXcoord() { return x; }
    public int getYcoord() { return y; }
    public double getCondition() { return condition; }
    public AdvancedStorage getStorage() { return storage; }


    // SETTERS
    //public void setType(BuildingType type)   { this.type = type; }
    public void setType(BuildingType type)   { this.type = type; this.typeName = type.getName(); }
    public void setOwner(String owner)  { this.owner = owner; }
    public void setXcoord(int x) { this.x = x; }
    public void setYcoord(int y) { this.y = y; }

    // CONDITION
    public void setCondition(double condition){ this.condition = condition;}
    public void addCondition(double conditionToAdd){ this.condition += condition;}

    // STORAGE (DO CASE BY CASE)
    public void addItem(String itemName, double amount, double durability){
        this.storage.addItemCheckCapacity(itemName, amount, durability);
    }
    public void removeItem(String itemName, double amount){
        this.storage.removeItem(itemName, amount);
    }




    /// RECIPES ====================================================
    public Recipe getActiveRecipe() { return activeRecipe; }

    public boolean canRunRecipe(Recipe recipe) { // call available recipes from this production building type
        return this.getType().canRunRecipe(recipe);
    }

    @JsonIgnore
    public List<Recipe> getAvailableRecipes() {  // call available recipes from this production building type
        return this.getType().getAvailableRecipes();
    }
    
    public void setActiveRecipe(Recipe recipe) {
        if(recipe == null) {this.activeRecipe = null;}
        else {
            if(canRunRecipe(recipe)){ this.activeRecipe = recipe; }
            else { System.out.println("Building cannot run recipe! (" + recipe.getName() + ")"); }
        }
    }


    public boolean hasAllIngredientsForRecipe(){ return hasAllIngredientsForRecipe(this.activeRecipe); }
    public boolean hasAllIngredientsForRecipe(Recipe recipe){
        boolean hasAllIngredients = true;
        // FIRST CHECK IF HAS ALL ITEMS 
        if (recipe.getIngredientslist() != null){
            for (IngredientPair ingredientPair : recipe.getIngredientslist().getIngredientslist()) {
                if(!this.getStorage().hasItem(ingredientPair.getItem_name(), ingredientPair.getCount())){hasAllIngredients = false;}
            }
        }
        return hasAllIngredients;
    }


    public boolean hasStorageSpaceForRecipe(){ return hasStorageSpaceForRecipe(this.activeRecipe); }
    public boolean hasStorageSpaceForRecipe(Recipe recipe){
        double neededStorageSpace = this.getStorage().getUsedCapacity();
        
        // FIRST CHECK IF HAS ALL SPACE FOR MORE
        for (IngredientPair ingredientPair : this.activeRecipe.getIngredientslist().getIngredientslist()) {
            neededStorageSpace -= ingredientPair.getCount();
        }
        for (IngredientPair outputPair : this.activeRecipe.getOutputslist().getIngredientslist()) {
            neededStorageSpace += outputPair.getCount();
        }
        return (this.getStorage().getCapacity() >= neededStorageSpace);
    }


    /** Perform recipe by converting ingredients into outputs. Checks if has all ingredients and space for outputs */
    public void performRecipe(){
        if(this.getActiveRecipe() != null){
            boolean hasAllIngredients = hasAllIngredientsForRecipe();
            boolean hasStorageSpace   = hasStorageSpaceForRecipe();

            // ACTUALLY REMOVES INGREDIENTS AND ADDS OUTPUTS
            if(hasAllIngredients){
                if (hasStorageSpace) {
                    for (IngredientPair ingredientPair : this.activeRecipe.getIngredientslist().getIngredientslist()) {
                        this.storage.removeItem(ingredientPair.getItem_name(), ingredientPair.getCount());
                    }
                    for (IngredientPair outputPair : this.activeRecipe.getOutputslist().getIngredientslist()) {
                        this.storage.addItem(outputPair.getItem_name(), outputPair.getCount(), 10);
                    }
                } else {System.out.println("[ERROR]: No storage space to perform recipe");}
            } else {System.out.println("[ERROR]: Missing some ingredients");}

        } else {
            System.out.println("No selected recipe!");
        }
        // DEACTIVATE RECIPE
        this.setActiveRecipe(null);
    }



    // UPDATE TURNS
    public abstract void updateNextTurn(); // Each subclass defines how to update itself per turn



    @Override
    public String toString() {
        return "\nBuilding Instance: " + this.typeName + " | Owner: " + this.owner +" ("+ this.x +","+ this.y + ") | " + this.condition + "%" ;
    }








    public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        ArrayList<IngredientPair> buildingCost = new ArrayList<>();
        buildingCost.add(new IngredientPair("Wood", 1));
        ProductionBuildingType buildingTest = new ProductionBuildingType("teste", "desc", "Hut", 1, 1, buildingCost, 0, new ArrayList<>(), new RecipeHandler());
        BuildingInstance instanceA = new ProductionBuildingInstance(buildingTest, "Pedro!", 0, 0, 10);
        
        BuildingInstance workshop;
        workshop = new ProductionBuildingInstance(new ProductionBuildingType(), "Player2", 15, 25, 5.0);
        workshop.addItem("Wood", 1, 5);

        workshop.storage.addItemCheckCapacity("Wood", 4, 5);
        System.out.println("workshop storage: " + workshop.storage);

        System.out.println("[8]" + instanceA);
        System.out.println("[8]" + ((ProductionBuildingInstance) instanceA).getInstanceType());

        // Save to file
        List<ProductionBuildingInstance> instances = List.of((ProductionBuildingInstance) instanceA, (ProductionBuildingInstance) workshop);
        mapper.writeValue(new File("teste_building_.json"), instances);
        System.out.println("Written to JSON!");

        // Load from file
        //List<BuildingInstance> loadedInstances = mapper.readValue(
        List<ProductionBuildingInstance> loadedInstances = mapper.readValue(
            new File("teste_building_.json"), 
            //new TypeReference<List<BuildingInstance>>() {}
            new TypeReference<List<ProductionBuildingInstance>>() {}
            //getBuildingsByCategory<List<ProductionBuildingInstance>>() {}
        );


        /// LOAD PRODUCTION BUILDING TYPES
        BuildingTypeList<ProductionBuildingType> productionList2 = new BuildingTypeList<>(ProductionBuildingType.class);
        productionList2.loadFromJson(ResourceLoader.getResourcePath("buildingtypes/ProductionBuildings.json"));
        System.out.println("Loaded production buildings: " + productionList2.getBuildings().size());


        System.out.println("[testes]:");
        System.out.println(loadedInstances.get(0).getType());
        System.out.println(((ProductionBuildingType) loadedInstances.get(0).getType()));
        System.out.println( loadedInstances.get(0).getOwner() );
        System.out.println( loadedInstances.get(0).getXcoord() );
        System.out.println( loadedInstances.get(0).getStorage() );
        System.out.println( loadedInstances.get(1).getStorage() );
        System.out.println( loadedInstances.get(0).getStorage().getUsedCapacity() );
        System.out.println( loadedInstances.get(1).getStorage().getUsedCapacity() );

        System.out.println("Loaded " + loadedInstances.size() + " buildings from JSON!");
    }

}
