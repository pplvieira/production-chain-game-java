package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.ItemRegistry;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.mechanics.RecipeHandler;


//@JsonTypeName("ProductionBuildingInstance__")
public class ProductionBuildingInstance extends BuildingInstance {
    //private Map<String, Integer> storage; // Resource storage // STORAGE HELD IN MAIN
    private String activeRecipeStr; // Current recipe being run
    //private List<String> allowedRecipeCategories; SHOULD GO INTO PRODUCTION BUILDING TYPE
    // @JsonInclude(JsonInclude.Include.ALWAYS)
    // private Recipe activeRecipe;
    //private ArrayList<String> allowedRecipeCategories; // carried in building types

    public ProductionBuildingInstance() {}


    public ProductionBuildingInstance(ProductionBuildingType type, String owner, int x, int y, double storageCapacity) {
        super(type, owner, x, y, storageCapacity);
        //this.storage = new HashMap<>();
        this.activeRecipe = null;
    }

    @JsonCreator
    public ProductionBuildingInstance(
        @JsonProperty("typeName") String typeName,
        @JsonProperty("owner") String owner,
        @JsonProperty("x") int x,
        @JsonProperty("y") int y,
        @JsonProperty("storageCap") double storageCapacity,
        @JsonProperty("activeRecipe") Recipe activeRecipe
        ) throws IOException {
        super(typeName, owner, x, y, storageCapacity, activeRecipe);
        //this.storage = new HashMap<>();
        //this.activeRecipe = null;
    }





    //public Map<String, Integer> getStorage() { return storage; }


    @Override
    public void updateNextTurn() {
        System.out.println("Processing production for " + this.type.getName());
        // Logic for consuming input, producing output

        // CHECKS??

        // RUN RECIPE

        // ITEMS EXPIRE

        // DEGRADE BUILDING CONDITION
        
    }


    public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
        ItemRegistry.loadFromJson("ItemRegistry_test.json");
        RecipeRegistry.loadFromJson("RecipeRegistry_test.json");

        //ProductionBuildingInstance buildingA = new ProductionBuildingInstance(new ProductionBuildingType(3), "Pedro", 0, 3, 15, new ArrayList<>(List.of("")));
        //ProductionBuildingType prodBuildA = new ProductionBuildingType();
        ProductionBuildingType prodBuildA = new ProductionBuildingType("TesteAllA", "--", "Hut", 1, 1, new ArrayList<>(List.of(new IngredientPair("Wood", 10))), 0, null, new RecipeHandler(1, new ArrayList<>(List.of("Basic silviculture"))));
        ProductionBuildingType prodBuildB = new ProductionBuildingType("TesteAllB", "--", "Hut", 1, 1, new ArrayList<>(List.of(new IngredientPair("Wood", 20))), 0, null, new RecipeHandler(2, new ArrayList<>(List.of("Basic silviculture"))));
        ProductionBuildingInstance buildingA = new ProductionBuildingInstance(prodBuildA, "Pedro", 0, 3, 15);
        ProductionBuildingInstance buildingB = new ProductionBuildingInstance(prodBuildB, "Pedro", 0, 4, 15);
        // IngredientsList ingredients = new IngredientsList();
        // ingredients.add(new IngredientPair("Wood", 2));
        IngredientsList ingredients = new IngredientsList(List.of(new IngredientPair("Wood", 1)));
        IngredientsList outputs     = new IngredientsList(List.of(new IngredientPair("Coal", 3)));
        buildingA.setActiveRecipe(new Recipe("Riceita", "Teste", "desc", 1, 1, ingredients, outputs, 0, true));
    
        System.out.println(buildingA.getStorage());
        System.out.println(buildingB.getStorage());
        buildingA.performRecipe();
        System.out.println(buildingA.getStorage());

        buildingA.addItem("Wood", 9, 10);
        System.out.println(buildingA.getStorage());
        buildingA.performRecipe();
        System.out.println(buildingA.getStorage());
        buildingA.performRecipe();
        System.out.println(buildingA.getStorage());
        buildingA.performRecipe();
        System.out.println(buildingA.getStorage());
        buildingA.performRecipe();
        System.out.println(buildingA.getStorage());

        // ACTIONS ON BUILDING B
        System.out.println("----------------- \nBuilding B things ");
        System.out.println("Valid recipes: " + buildingB.getAvailableRecipes());
        List<Recipe> silvicultureRecipes = RecipeRegistry.getRecipesByCategory("Basic silviculture");
        buildingB.setActiveRecipe(silvicultureRecipes.get(0));
        buildingB.performRecipe();

        List<Recipe> miningRecipes = RecipeRegistry.getRecipesByCategory("Basic mining");
        buildingB.setActiveRecipe(miningRecipes.get(0));
        buildingB.performRecipe();


        
        // Save to file
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<BuildingInstance> instances = List.of(buildingA, buildingB);
        mapper.writeValue(new File("teste_building_2.json"), instances);
        System.out.println("Written to JSON!");

    }
}
