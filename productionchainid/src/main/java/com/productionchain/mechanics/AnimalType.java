package com.productionchain.mechanics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productionchain.enginedatatypes.IngredientsList;





@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimalType {    
    // REMOVE O item_ DAQUI !!!
    
    String name;

    String description;
    double spaceOccupied;

    double timeToMaturity;
    double timeToReproductiveAge;
    double lifeSpan;

    /// IngredientsList preffered food???????
    IngredientsList essencialFoods;  // include water
    IngredientsList preferredFoods;  //
    IngredientsList acceptedFoods;   //
    double minFoodRequirement;       // in kg
    double optimalFoodRequirement;   // in kg
    double minFoodProductivity;      // 70 (%) //optimal food productivity is 100
    // IngredientsList minConsumption;  // LIMIT TO LIVE
    // IngredientsList baseConsumption; // LIMIT TO PRODUCE OUTPUT

    IngredientsList productionPerMonth; // RESOURCES THAT CAN BE PRODUCED PER MONTH
    IngredientsList lootWhenKilled;     // 


    //double[] testeapagar = {1,2,3,4};

    // EMPTY CONSTRUCTOR
    public AnimalType(){
    }

    // CONTSTRUCTOR
    @JsonCreator
    public AnimalType(
        @JsonProperty("name") String name, 
        @JsonProperty("description") String description,   
        @JsonProperty("spaceOccupied") double spaceOccupied, 
        @JsonProperty("timeToMaturity") double timeToMaturity, 
        @JsonProperty("timeToReproductiveAge") double timeToReproductiveAge,  
        @JsonProperty("lifeSpan") double lifeSpan, 
        @JsonProperty("essencialFoods") IngredientsList essencialFoods,
        @JsonProperty("preferredFoods") IngredientsList preferredFoods,
        @JsonProperty("acceptedFoods" ) IngredientsList acceptedFoods,  //
        @JsonProperty("minFoodRequirement") double minFoodRequirement,       // in kg
        @JsonProperty("optimalFoodRequirement") double optimalFoodRequirement,   // in kg
        @JsonProperty("minFoodProductivity") double minFoodProductivity,      // 70 (%) //optimal food productivity is 100
        @JsonProperty("lootWhenKilled") IngredientsList lootWhenKilled,
        @JsonProperty("productionPerMonth") IngredientsList productionPerMonth) {
        this.name = name;
        this.description = description;
        this.spaceOccupied = spaceOccupied;
        this.timeToMaturity = timeToMaturity;
        this.timeToReproductiveAge = timeToReproductiveAge;
        this.lifeSpan = lifeSpan;

        this.essencialFoods = essencialFoods;
        this.preferredFoods = preferredFoods;
        this.acceptedFoods  = acceptedFoods;
        this.minFoodRequirement = minFoodRequirement;
        this.optimalFoodRequirement = optimalFoodRequirement;
        this.minFoodProductivity = minFoodProductivity;

        this.lootWhenKilled = lootWhenKilled;
        this.productionPerMonth = productionPerMonth;
    }



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getSpaceOccupied() {
        return spaceOccupied;
    }

    public double getTimeToMaturity() {
        return timeToMaturity;
    }

    public double getTimeToReproductiveAge() {
        return timeToReproductiveAge;
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    // public IngredientsList getMinConsumption() {
    //     return minConsumption;
    // }

    // public IngredientsList getBaseConsumption() {
    //     return baseConsumption;
    // }

    public IngredientsList getProductionPerMonth() {
        return productionPerMonth;
    }

    public IngredientsList getLootWhenKilled() {
        return lootWhenKilled;
    }

    public IngredientsList getEssencialFoods() {
        return essencialFoods;
    }

    public IngredientsList getPreferredFoods() {
        return preferredFoods;
    }

    public IngredientsList getAcceptedFoods() {
        return acceptedFoods;
    }

    public double getMinFoodRequirement() {
        return minFoodRequirement;
    }

    public double getOptimalFoodRequirement() {
        return optimalFoodRequirement;
    }

    public double getMinFoodProductivity() {
        return minFoodProductivity;
    }


    

    @Override
    public String toString(){
        return "Name: " + name + " | times: " + timeToMaturity + "/" + timeToReproductiveAge + "/" + lifeSpan;
    }


}
