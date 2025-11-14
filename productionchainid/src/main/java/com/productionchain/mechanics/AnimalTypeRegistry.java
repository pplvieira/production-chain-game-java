package com.productionchain.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientsList;



public class AnimalTypeRegistry{
    private static final List<AnimalType> animalTypes = new ArrayList<>();



    public AnimalTypeRegistry() {}

    // public AnimalTypeList(List<AnimalType> animaltypes) {
    //     this.animalTypes = animaltypes;
    // }


    public static void addAnimal(AnimalType animal){
        AnimalTypeRegistry.animalTypes.add(animal);
    }


    public static List<AnimalType> getAnimalTypes(){
        return animalTypes;
    }


    public static AnimalType getAnimalByName(String name) {
        return animalTypes.stream()
        .filter(animal -> animal.getName().equals(name)) // Match name
        .findFirst() // Get the first match
        .orElse(null);
    }


    // public static List<AnimalType> getRecipesByCategory(String category) {
    //     return animalTypes.stream()
    //         .filter(animal -> animal.getCategory().equals(category))
    //         .collect(Collectors.toList());
    // }




    // LOAD FROM JSON file
    public static void loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        animalTypes.clear(); // to avoid duplicates
        try {
            List<AnimalType> read_animals = mapper.readValue(new File(filePath), new TypeReference<List<AnimalType>>() {});
            for (AnimalType animal : read_animals) {
                //this.recipes.put(recipe.getName(), recipe);
                animalTypes.add(animal);
            }
            System.out.println("RecipeRegistry successfully loaded from JSON.");
        } catch (IOException e) {
            System.err.println("Failed to load RecipeRegistry from JSON.");
        }
    }

    // Write to JSON file
    public static void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), AnimalTypeRegistry.animalTypes); //writerWithDefaultPrettyPrinter()
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nAnimal type registry: ( )\n");
        animalTypes.forEach(itemType -> {
            result.append(itemType.toString());
            result.append("\n");
        });
        return result.toString();
    }


    public static void main(String[] args) {
        
        AnimalTypeRegistry.addAnimal(new AnimalType("Pio", "--", 1, 10, 12, 20, new IngredientsList(), new IngredientsList(), new IngredientsList(), 0.1, 0.2, 70, new IngredientsList(), new IngredientsList()));
        AnimalTypeRegistry.addAnimal(new AnimalType("Pio2", "--", 1, 0, 12, 20, new IngredientsList(), new IngredientsList(), new IngredientsList(), 0.1, 0.2, 70, new IngredientsList(), new IngredientsList()));
        System.out.println(AnimalTypeRegistry.getAnimalTypes());
    }

}
