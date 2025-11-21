package com.productionchain.datatypesold;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @deprecated This class is part of the legacy datatypesold package and should not be used.
 * Use com.productionchain.buildings.BuildingType instead.
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
class Building {

    String name;
    String BuildingClass;
    String category;
    int n_upgrades;
    ArrayList<UpgradeRecipesPair> upgradeRecipesPairs;
    int baseNumOperations;
    double efficiencyMultiplier;


    // empty constructor
    public Building(int n_upgrades){
        this.name = "";
        this.BuildingClass = null;
        this.category = "";
        this.n_upgrades = n_upgrades;
        this.upgradeRecipesPairs = (ArrayList<UpgradeRecipesPair>) Stream.generate(UpgradeRecipesPair::new).limit(n_upgrades).collect(Collectors.toList());
        //new ArrayList<>(n_upgrades);
        this.baseNumOperations = 1;
        this.efficiencyMultiplier = 1;
    }


    // empty constructor
    public Building(int n_upgrades, String buildingClass){
        this.name = "";
        this.BuildingClass = buildingClass;
        this.category = "";
        this.n_upgrades = n_upgrades;
        this.upgradeRecipesPairs = (ArrayList<UpgradeRecipesPair>) Stream.generate(UpgradeRecipesPair::new).limit(n_upgrades).collect(Collectors.toList());
        //new ArrayList<>(n_upgrades);
        this.baseNumOperations = 1;
        this.efficiencyMultiplier = 1;
    }

    @JsonCreator
    public Building(@JsonProperty("name") String name, 
        @JsonProperty("category") String category,
        @JsonProperty("n_upgrades") int n_upgrades,
        @JsonProperty("upgrade_recipes_pairs") ArrayList<UpgradeRecipesPair> upgradeRecipesPairs, 
        @JsonProperty("base_num_operations") int baseNumOperations, 
        @JsonProperty("efficiency_multiplier") double efficiencyMultiplier){
        
        this.name = name;
        this.category = category;
        this.n_upgrades = n_upgrades;
        this.upgradeRecipesPairs = upgradeRecipesPairs;
        this.baseNumOperations = baseNumOperations;
        this.efficiencyMultiplier = efficiencyMultiplier;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<UpgradeRecipesPair> getUpgradeRecipesPairs() {
        return upgradeRecipesPairs;
    }

    public int getBaseNumOperations() {
        return baseNumOperations;
    }

    public double getEfficiencyMultiplier() {
        return efficiencyMultiplier;
    }

    public int getN_upgrades() {
        return n_upgrades;
    }



}
