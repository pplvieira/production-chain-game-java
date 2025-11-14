package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productionchain.datatypesold.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.mechanics.RecipeHandler;

public class AnimalBuildingType extends BuildingType{
    
    double optimalHousingSpace;
    double maximumHousingSpace;
    ArrayList<String> allowedAnimals;



    public AnimalBuildingType(){
        super();
        System.out.println("[EMPTY CONSTRUCTOR]");
    }

    public AnimalBuildingType(double optimalHousingSpace){
        super();
        this.optimalHousingSpace = optimalHousingSpace;
        this.maximumHousingSpace = optimalHousingSpace;
        this.allowedAnimals = new ArrayList<>();
    }

    public AnimalBuildingType(
        @JsonProperty("name") String name, 
        @JsonProperty("description") String description,
        @JsonProperty("category") String category,
        @JsonProperty("baseEfficiency") double baseEfficiency,
        @JsonProperty("baseDegradationRate") double baseDegradationRate,
        @JsonProperty("buildingCost") ArrayList<IngredientPair> buildingCost, /// NOT SURE SE DEVIA POR O <>
        @JsonProperty("n_upgrades") int n_upgrades,
        @JsonProperty("upgrades") ArrayList<String> upgrades,
        @JsonProperty("upgrades") RecipeHandler recipeHandler,
        @JsonProperty("optimalHousingSpace") double optimalHousingSpace,
        @JsonProperty("maximumHousingSpace") double maximumHousingSpace,
        @JsonProperty("allowedAnimals") ArrayList<String> allowedAnimals){
        super(name, description, category, baseEfficiency, baseDegradationRate, buildingCost, n_upgrades, upgrades, recipeHandler);
        this.optimalHousingSpace = optimalHousingSpace;
        this.maximumHousingSpace = maximumHousingSpace;
        this.allowedAnimals = allowedAnimals;
    }


    // GETTERS 
    public double getOptimalHousingSpace() { return optimalHousingSpace;}
    public double getMaximumHousingSpace() { return maximumHousingSpace;}
    public ArrayList<String> getAllowedAnimals() {return allowedAnimals;}
    
    // SETTERS
    public void setOptimalHousingSpace(double optimalHousingSpace) { this.optimalHousingSpace = optimalHousingSpace;}
    public void setMaximumHousingSpace(double maximumHousingSpace) { this.maximumHousingSpace = maximumHousingSpace;}
    public void setAllowedAnimals(ArrayList<String> allowedAnimals) {this.allowedAnimals = allowedAnimals;}






    @Override
    public String toString(){
        return "Animal Building: " + this.name + " : "  + this.category + " : " + this.description + " | " + this.baseDegradationRate + " ~ " + this.baseEfficiency +
            " || " + this.optimalHousingSpace + "/" + this.maximumHousingSpace + " | " + allowedAnimals;
    }




    public static void main(String[] args) throws IOException {
        

        // CONSTRUCT EXAMPLE BUILDING
        ArrayList<IngredientPair> buildingCost = new ArrayList<>();
        buildingCost.add(new IngredientPair("Wood", 1));
        AnimalBuildingType buildingTest = new AnimalBuildingType("Galinheiro", "-", "Galinheiro", 1, 1, buildingCost, 0, new ArrayList<>(), new RecipeHandler(), 10, 25, new ArrayList<>(List.of("Cow")));

        // BUILDING LIST FORMALIZATION
        BuildingTypeList<AnimalBuildingType> AnimalBuildingRegistry = new BuildingTypeList<>(AnimalBuildingType.class);
        AnimalBuildingRegistry.setBuildings(List.of(buildingTest));
        System.out.println(AnimalBuildingRegistry);


        String FILENAME = "C:\\Users\\Pedro\\Desktop\\Programas\\Production-Chain-Game\\productionchainid\\src\\main\\resources\\AnimalBuildings.json";
        AnimalBuildingRegistry.saveToJson(FILENAME);
    }
}






