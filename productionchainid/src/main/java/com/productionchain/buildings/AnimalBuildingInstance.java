package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.ItemType;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.mechanics.AnimalInstance;
import com.productionchain.mechanics.AnimalType;
import com.productionchain.mechanics.RecipeHandler;




public class AnimalBuildingInstance extends BuildingInstance{
    
    ArrayList<AnimalInstance> housedAnimals;
    double cleanliness;


    public AnimalBuildingInstance() {}


    public AnimalBuildingInstance(AnimalBuildingType type, String owner, int x, int y, double storageCapacity) {
        super(type, owner, x, y, storageCapacity);
        //this.storage = new HashMap<>();
        //this.activeRecipe = null;
        this.housedAnimals = new ArrayList<>();
        this.cleanliness   = 0;
    }

    @JsonCreator
    public AnimalBuildingInstance(
        @JsonProperty("typeName") String typeName,
        @JsonProperty("owner") String owner,
        @JsonProperty("x") int x,
        @JsonProperty("y") int y,
        @JsonProperty("storageCap") double storageCapacity,
        @JsonProperty("activeRecipe") Recipe activeRecipe,
        @JsonProperty("housedAnimals") ArrayList<AnimalInstance> housedAnimals,
        @JsonProperty("cleanliness") double cleanliness
        ) throws IOException {
        super(typeName, owner, x, y, storageCapacity, activeRecipe);
        //this.storage = new HashMap<>();
        //this.activeRecipe = null;
        this.housedAnimals = housedAnimals;
        this.cleanliness = cleanliness;
    }



    // GETTERS
    @JsonIgnore
    public ArrayList<AnimalInstance> getHousedAnimals() { return housedAnimals; }
    public double getCleanliness() { return cleanliness; }

    
    // SETTERS / ADDERS
    public void addHousedAnimal(AnimalInstance animal){ this.housedAnimals.add(animal);}
    public void setHousedAnimals(ArrayList<AnimalInstance> housedAnimals) { this.housedAnimals = housedAnimals; }
    public void setCleanliness(double cleanliness) { this.cleanliness = cleanliness; }



    // STATYSTICS !!! ============================
    /** Returns the mean of the parameter (age, happyness, ...) to be read from all the animals aggregate */
    // @JsonIgnore
    // public double getSummaryStatistics(String parameter){
    //     double r;
    //     switch (parameter) {
    //         case "age": return housedAnimals
        
    //         default:
    //             System.out.println("[ERROR]: Invalid/not implemented parameter request!");
    //             return -1;
    //     }
    //     double totalAvailable = items.get(itemName).stream().mapToDouble(b -> b.quantity).sum();

    // }



    /** Returns the summarystatistics of the parameter (age, happyness, ...) to be read from all the animals aggregate 
     * Accepted: "age", "satisfaction", "productivity", "health";
    */
    @JsonIgnore
    public DoubleSummaryStatistics getSummaryStatistics(String parameter){
        switch (parameter) {
            case "age":             return housedAnimals.stream().mapToDouble(AnimalInstance::getAge).summaryStatistics();
            case "satisfaction":    return housedAnimals.stream().mapToDouble(AnimalInstance::getSatisfaction).summaryStatistics();
            case "productivity":    return housedAnimals.stream().mapToDouble(AnimalInstance::getProductivity).summaryStatistics();
            case "health":          return housedAnimals.stream().mapToDouble(AnimalInstance::getHealth).summaryStatistics();
            default:
                System.out.println("[ERROR]: Invalid/not implemented parameter request!");
                return null;
        }
    }




    @Override
    public void updateNextTurn() {
        throw new UnsupportedOperationException("Not supported yet.");

        // UPDATE AGES

        // UPDATE CONDITION ( call a function defined in building instance)
    }




    public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
        AnimalType vaca = new AnimalType("Cow", "-", 1, 2, 3, 10, new IngredientsList(List.of(new IngredientPair("Water", 10))), new IngredientsList(), new IngredientsList(), 0, 0, 0, new IngredientsList(), new IngredientsList());
        AnimalInstance vacaInstance = new AnimalInstance(vaca);
        AnimalType vaca2 = new AnimalType("Cow", "-", 1, 2, 3, 10, new IngredientsList(List.of(new IngredientPair("Water", 10))), new IngredientsList(), new IngredientsList(), 0, 0, 0, new IngredientsList(), new IngredientsList());
        AnimalInstance vacaInstance2 = new AnimalInstance(vaca2);
        vacaInstance2.setAge(2);

        System.out.println(vacaInstance);

        AnimalBuildingType vacariaType = new AnimalBuildingType("Vacaria", "-", "Vacaria", 100, 0, new ArrayList<>(), 0, null, new RecipeHandler(), 10, 20, new ArrayList<>(List.of("Cow"))); 
        AnimalBuildingInstance vacariaInstance = new AnimalBuildingInstance(vacariaType, "Pedro", 0, 0, 10);

        System.out.println(vacariaInstance);
        vacariaInstance.addHousedAnimal(vacaInstance);
        vacariaInstance.addHousedAnimal(vacaInstance);
        vacariaInstance.addHousedAnimal(vacaInstance);
        System.out.println(vacariaInstance.getSummaryStatistics("age"));
        System.out.println(vacariaInstance.getSummaryStatistics("health"));

        vacariaInstance.addHousedAnimal(vacaInstance2);
        System.out.println(vacariaInstance);

        DoubleSummaryStatistics ageStats = vacariaInstance.getSummaryStatistics("age");
        System.out.println(ageStats);
        System.out.println(ageStats.getAverage());


        

        // Save to file
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<BuildingInstance> instances = List.of(vacariaInstance);
        mapper.writeValue(new File("AnimalBuildingInstances_teste.json"), instances);
        System.out.println("Written to JSON!");
        
    }
}
