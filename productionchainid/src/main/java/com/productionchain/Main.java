package com.productionchain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.productionchain.enginedatatypes.IngredientPair;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.buildings.BuildingTypeList;
import com.productionchain.buildings.ProductionBuildingInstance;
import com.productionchain.buildings.ProductionBuildingType;
import com.productionchain.enginedatatypes.ItemRegistry;
import com.productionchain.enginedatatypes.ItemType;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.IngredientsList;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.ResourceLoader;
import com.productionchain.mechanics.RecipeHandler;
import com.productionchain.constants.ItemConstants;
import com.productionchain.constants.RecipeConstants;
import com.productionchain.constants.BuildingConstants;

public class Main {
    public static void teste_building_type_list(){
        List<IngredientPair> buildingCost = new ArrayList<>();
        buildingCost.add(new IngredientPair("ItemA", 1)); 
        
        ArrayList<String> upgrades = new ArrayList<>();
        upgrades.add("upgrade1");

        ArrayList<String> baseRecipeCategories = new ArrayList<>();
        baseRecipeCategories.add("recipeCat1");

        // ProductionBuildingType prodBuildingA = new ProductionBuildingType("BuildA", "sdakj", "production", 1, 
        //         0.1, buildingCost, 3, upgrades, 1, baseRecipeCategories);
        // ProductionBuildingType prodBuildingB = new ProductionBuildingType("BuildB", "hdaha", "production", 1, 
        //         0.1, buildingCost, 3, upgrades, 1, baseRecipeCategories);

        // System.out.println(prodBuildingA);

        // BuildingTypeList build_list = new BuildingTypeList(ProductionBuildingType.class);
        // build_list.addBuilding(prodBuildingA);
        // build_list.addBuilding(prodBuildingB);
    }


    

    // =============================================================================
    public static void write_items_json(String jsonpath) throws IOException{
        ItemRegistry.addItem(new ItemType(ItemConstants.WOOD, ItemConstants.CATEGORY_RAW_MATERIAL, 1.0, 10.0, 50.0, true, true, true));
        ItemRegistry.addItem(new ItemType(ItemConstants.STONE, ItemConstants.CATEGORY_RAW_MATERIAL, 1.0, 10.0, 50.0, true, true, true));
        ItemRegistry.addItem(new ItemType(ItemConstants.COAL, ItemConstants.CATEGORY_RAW_MATERIAL, 1.0, 8.0, 100.0, true, true, true));

        ItemRegistry.addItem(new ItemType("Weat seed", "Seed", 0.1, 0.1, 12.0, true, true, true));
        ItemRegistry.addItem(new ItemType("Barley seed", "Seed", 0.1, 0.1, 12.0, true, true, true));


        //System.out.println(ItemRegistry);
        ItemRegistry.saveToJson(jsonpath);
    }

    // =============================================================================
    public static void write_recipes_json(String jsonpath) throws IOException{

        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_CHOP_WOOD, RecipeConstants.CATEGORY_BASIC_SILVICULTURE, "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 1)))), 1.0, true));
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_MINE_STONE, RecipeConstants.CATEGORY_BASIC_MINING, "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 10)))), 1.0, true));
        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_MINE_COAL, RecipeConstants.CATEGORY_BASIC_MINING, "--", 0, 1, new IngredientsList(), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 5)))), 1.0, true));

        RecipeRegistry.addRecipe(new Recipe(RecipeConstants.RECIPE_CHARCOAL, RecipeConstants.CATEGORY_BASIC_KILN, "--", 1, 1, new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 2)))), new IngredientsList(new ArrayList<>(List.of(new IngredientPair(ItemConstants.COAL, 3)))), 1.0, true));

        //System.out.println(RecipeRegistry);
        RecipeRegistry.saveToJson(jsonpath);
    }


    // =============================================================================
    public static void write_production_building_types_json(String jsonpath) throws IOException{
        BuildingTypeList<ProductionBuildingType> productionBuildingTypeList = new BuildingTypeList<>(ProductionBuildingType.class);
        // productionBuildingTypeList.addBuilding(new ProductionBuildingType("Hut", "---", "Hut", 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 10))), 0, null, 1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE))));
        // productionBuildingTypeList.addBuilding(new ProductionBuildingType("Mine", "-", "Mine", 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 20))), 0, null, 1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_MINING))));
        // productionBuildingTypeList.addBuilding(new ProductionBuildingType("Kiln", "-", "Kiln", 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 20))), 0, null, 1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_KILN, RecipeConstants.CATEGORY_BASIC_SILVICULTURE))));
        productionBuildingTypeList.addBuilding(new ProductionBuildingType(BuildingConstants.BUILDING_HUT, "---", BuildingConstants.BUILDING_HUT, 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 10))), 0, null, new RecipeHandler(1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_SILVICULTURE)))));
        productionBuildingTypeList.addBuilding(new ProductionBuildingType(BuildingConstants.BUILDING_MINE, "-", BuildingConstants.BUILDING_MINE, 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.WOOD, 20))), 0, null, new RecipeHandler(1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_MINING)))));
        productionBuildingTypeList.addBuilding(new ProductionBuildingType(BuildingConstants.BUILDING_KILN, "-", BuildingConstants.BUILDING_KILN, 1, 0, new ArrayList<>(List.of(new IngredientPair(ItemConstants.STONE, 20))), 0, null, new RecipeHandler(1, new ArrayList<>(List.of(RecipeConstants.CATEGORY_BASIC_KILN, RecipeConstants.CATEGORY_BASIC_SILVICULTURE)))));
    
        productionBuildingTypeList.saveToJson(jsonpath);
    }


    public static BuildingTypeList<ProductionBuildingType> teste_initialize1() throws IOException{
        /// LOAD ALL SORTS OF STUFF
        ItemRegistry.loadFromJson("ItemRegistry_test.json");
        RecipeRegistry.loadFromJson("RecipeRegistry_test.json");

        //final BuildingTypeList<ProductionBuildingType> productionBuildingRegistry;
        BuildingTypeList<ProductionBuildingType> productionBuildingRegistry = new BuildingTypeList<>(ProductionBuildingType.class);
        //productionBuildingRegistry.loadFromJson(ResourceLoader.getResourcePath("buildingtypes/ProductionBuildings.json"));
        productionBuildingRegistry.loadFromJson("ProductionBuildingRegistry_test.json");

        //ItemRegistry itemreg = ;
        System.out.println(new ItemRegistry());
        //System.out.println(ItemRegistry.getItemTypes()); //unnecessary
        System.out.println(RecipeRegistry.getRecipes());
        System.out.println(RecipeRegistry.getRecipes().size());
        System.out.println(RecipeRegistry.getRecipesByCategory(RecipeConstants.CATEGORY_BASIC_SILVICULTURE));
        System.out.println(RecipeRegistry.getRecipesByCategory(RecipeConstants.CATEGORY_BASIC_MINING));
        System.out.println(productionBuildingRegistry);


        
        //System.out.println(productionBuildingRegistry);
        ProductionBuildingType buildingTypeA = productionBuildingRegistry.getBuildingByName("Hut");
        System.out.println(buildingTypeA);
        ProductionBuildingInstance instanceA = new ProductionBuildingInstance(buildingTypeA, "Player1", 0, 0, 10);
        System.out.println(instanceA);


        System.out.println("instanceA type: " + ((ProductionBuildingType) instanceA.getType()));
        System.out.println("instanceA type recipes: " + ((ProductionBuildingType) instanceA.getType()).getAvailableRecipes());
        System.out.println("Available recipes: " + instanceA.getAvailableRecipes());
        instanceA.setActiveRecipe(RecipeRegistry.getRecipeByName("Mine coal"));
        System.out.println(instanceA.getActiveRecipe());
        instanceA.setActiveRecipe(RecipeRegistry.getRecipeByName("Chop wood"));
        System.out.println(instanceA.getActiveRecipe());

        System.out.println(instanceA.getStorage());
        instanceA.performRecipe();
        System.out.println(instanceA.getStorage());

        
        System.out.println("\n==========");
        ProductionBuildingType buildingTypeB = productionBuildingRegistry.getBuildingByName("Kiln");
        System.out.println(buildingTypeB);
        ProductionBuildingInstance instanceB = new ProductionBuildingInstance(buildingTypeB, "Player1", 1, 0, 20);
        System.out.println(instanceB);

        System.out.println(instanceB.getStorage());
        instanceB.setActiveRecipe(RecipeRegistry.getRecipeByName("Charcoal"));
        instanceB.performRecipe();
        System.out.println(instanceB.getStorage());
        instanceB.setActiveRecipe(RecipeRegistry.getRecipeByName("Chop wood"));
        instanceB.performRecipe();
        System.out.println(instanceB.getStorage());
        instanceB.performRecipe();
        System.out.println(instanceB.getStorage());
        instanceB.setActiveRecipe(RecipeRegistry.getRecipeByName("Charcoal"));
        instanceB.performRecipe();
        System.out.println(instanceB.getStorage());

        instanceB.updateNextTurn(); // does nothing for now



        // Save to file
        instanceB.setActiveRecipe(RecipeRegistry.getRecipeByName("Chop wood"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<ProductionBuildingInstance> instances = List.of(instanceA, instanceB);
        mapper.writeValue(new File("BuildingInstances_teste.json"), instances);
        System.out.println("Written to JSON!");




        return productionBuildingRegistry;
    }


    
    
    
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");


        // teste_building_type_list();
        write_items_json("ItemRegistry_test.json");
        write_recipes_json("RecipeRegistry_test.json");
        write_production_building_types_json("ProductionBuildingRegistry_test.json");

        //BuildingTypeList<ProductionBuildingType> productionBuildingRegistry = teste_initialize1();
        teste_initialize1();

    }
}