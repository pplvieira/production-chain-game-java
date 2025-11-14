package com.productionchain.mechanics;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.IngredientsList;




public class AnimalInstance {

    @JsonIgnore
    AnimalType type;
    String typeName;

    int x, y; // Position on the map
    String owner;

    String gender; //  male or female
    String state;  // like alive, sick, pregnant, dead? 
    double age;
    double satisfaction;
    double productivity;
    double health;



    public AnimalInstance() {}


    public AnimalInstance(AnimalType type) {
        this.type = type;
        // System.out.println(">>> BuildingInstance(BuildingType type)");
        // System.out.println(type);
        this.typeName = type.getName();
        this.gender = (Math.random() < 0.5) ? "male" : "female";
        this.state  = "alive";
        this.age    = 0;
        this.satisfaction = 100;
        this.productivity = 100;
        this.health       = 100;
    }

    @JsonCreator
    public AnimalInstance(String typeName, String gender, String state, double age, double satisfaction, double productivity, double health) throws IOException {
        System.out.println(">>> BuildingInstance(String typeName)");
        /// LOAD BUILDING TYPE REGISTRY
        
        if( AnimalTypeRegistry.getAnimalByName(typeName) == null){
            System.out.println("[ERROR]: building '" + typeName + "' doesn't exist in registry!");
        }
        this.type = AnimalTypeRegistry.getAnimalByName(typeName);

        System.out.println("&&& teste: " + AnimalTypeRegistry.getAnimalByName(typeName));

        this.typeName = type.getName();
        this.gender = gender;
        this.state = state;
        this.age = age;
        this.satisfaction = satisfaction; // Default condition
        this.productivity = productivity;
        this.health = health;
    }

    @JsonSetter("typeName") // Called when JSON assigns this field
    public void settypeName(String typeName) throws IOException {
        this.typeName = typeName;
        this.resolveType(); // Reconstruct type from registry
    }

    public void resolveType() throws IOException {
        this.type = AnimalTypeRegistry.getAnimalByName(this.typeName);
    }

    public AnimalType getType() { return type; }
    public String getGender() { return gender; }
    public String getState() { return state; }
    public double getAge() { return age; }
    public double getSatisfaction() { return satisfaction; }
    public double getProductivity() { return productivity; }
    public double getHealth() { return health; }

    public void setGender(String gender) { this.gender = gender; }
    public void setState(String state) { this.state = state; }
    public void setAge(double age) { this.age = age; }
    public void setSatisfaction(double satisfaction) { this.satisfaction = satisfaction; }
    public void setProductivity(double productivity) { this.productivity = productivity; }
    public void setHealth(double health) { this.health = health; }


    @Override
    public String toString() {
        return "\nAnimal Instance: " + this.typeName  + " " + this.gender + " | Owner: " + this.owner +" ("+ this.x +","+ this.y + ") | " + this.satisfaction + "%/" + this.productivity + "%/" + this.health + "%" ;
    }


    public static void main(String[] args) {
        AnimalType vaca = new AnimalType("Cow", "-", 1, 2, 3, 10, new IngredientsList(List.of(new IngredientPair("Water", 10))), new IngredientsList(), new IngredientsList(), 0, 0, 0, new IngredientsList(), new IngredientsList());
        AnimalInstance vacaInstance = new AnimalInstance(vaca);
        
        System.out.println("Vaca" + vacaInstance);
        System.out.println("Vaca" + vacaInstance.getType());
    }
}
