package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientPair;
// import com.productionchain.enginedatatypes.ItemRegistry;
// import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.ItemRegistry;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.ResourceLoader;
import com.productionchain.mechanics.RecipeHandler;



public class BuildingTypeList<T extends BuildingType> {
    private List<T> buildings = new ArrayList<>();
    private final Class<T> type;

    public BuildingTypeList(Class<T> type) {
        this.type = type;
        this.buildings = new ArrayList<>();
    }


    // SETTERS
    public void setBuildings(List<T> buildings) { this.buildings = buildings; }

    public void addBuilding(BuildingType building){
        this.buildings.add((T) building);
    }


    // GETTERS
    public List<T> getBuildings() { return buildings; }

    public List<T> getBuildingsByCategory(String category){
        return buildings.stream()
                    .filter(building -> building.getCategory().equals(category))
                    .collect(Collectors.toList());
    }

    public T getBuildingByName(String name){
        return buildings.stream()
        .filter(building -> building.getName().equals(name)) // Match name
        .findFirst() // Get the first match
        .orElse(null);
    }


    // Read from JSON file
    public void loadFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.buildings = mapper.readValue(new File(filePath), 
            mapper.getTypeFactory().constructCollectionType(List.class, type));
    }

    // Write to JSON file
    public void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.writeValue(new File(filePath), this.buildings); //writerWithDefaultPrettyPrinter()
    }


    @Override
    public String toString() {
        String returnstr = "> ";
        returnstr += type.getSimpleName() + " list:";
        for (BuildingType building : buildings) {
            returnstr += "\n  - " + building.getName() + " | " + building.getCategory() + " | " ;
        }
        return returnstr;
    }



    public static void main(String[] args) {
        try {
            ///* create building list artificial primeira vez
            BuildingTypeList<ProductionBuildingType> productionList = new BuildingTypeList<>(com.productionchain.buildings.ProductionBuildingType.class);
            //List<ProductionBuildingType> edificios = new ArrayList<>();
            ProductionBuildingType edificioC = new ProductionBuildingType();
            //productionList.setBuildings(edificios);
            //*/

            String folderpath = ResourceLoader.getResourceFolderPath("buildingtypes/ProductionBuildings.json");
            // Load Production Buildings
            /*
            BuildingTypeList<ProductionBuildingType> productionList = new BuildingTypeList<>(ProductionBuildingType.class);
            System.out.println("Created new buildingtypelist");
            //productionList.loadFromJson(folderpath + "\\production_buildings.json");
            productionList.loadFromJson(folderpath + "\\ProductionBuildings.json");
            System.out.println("Loaded production buildings: " + productionList.getBuildings().size());
            // */

            // Modify and save new data
            ProductionBuildingType buildingA = new ProductionBuildingType();
            productionList.addBuilding(buildingA);
            productionList.addBuilding(edificioC);
            System.out.println("Edificio na lista: " + productionList.getBuildings().get(0));
            productionList.saveToJson(folderpath + "\\ProductionBuildings.json");

            // Load Production Buildings
            ///*
            BuildingTypeList<ProductionBuildingType> productionList2 = new BuildingTypeList<>(ProductionBuildingType.class);
            System.out.println("Created new buildingtypelist");
            //productionList.loadFromJson(folderpath + "\\production_buildings.json");
            productionList2.loadFromJson(folderpath + "\\ProductionBuildings.json");
            System.out.println("Loaded production buildings: " + productionList2.getBuildings().size());
            // */

        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("\n====================== ");
        ItemRegistry.loadFromJson("ItemRegistry_test.json");
        RecipeRegistry.loadFromJson("RecipeRegistry_test.json");

        //ProductionBuildingInstance buildingA = new ProductionBuildingInstance(new ProductionBuildingType(3), "Pedro", 0, 3, 15, new ArrayList<>(List.of("")));
        ProductionBuildingType prodBuildA = new ProductionBuildingType();
        ProductionBuildingType prodBuildB = new ProductionBuildingType("TesteAll", "-1", "Hut", 1, 1, new ArrayList<>(List.of(new IngredientPair("Wood", 10))), 0, null, new RecipeHandler(1, (ArrayList<String>) List.of("Basic silviculture")) );
        ProductionBuildingType prodBuildC = new ProductionBuildingType("TestAll2", "-2", "Hut", 1, 1, new ArrayList<>(List.of(new IngredientPair("Wood", 20))), 0, null, new RecipeHandler(1, (ArrayList<String>) List.of("Basic silviculture")) );
        //ProductionBuildingType prodBuildC = new ProductionBuildingType("TestAll2", "-2", "Hut", 1, 1, new ArrayList<>(List.of(new IngredientPair("Wood", 20))), 0, null, 1, new ArrayList<>(List.of("Basic silviculture")));
    
        BuildingTypeList<ProductionBuildingType> ProdBuildingRegistry = new BuildingTypeList<>(ProductionBuildingType.class);
        //ProdBuildingRegistry.setBuildings(new ArrayList<>(List.of(prodBuildA, prodBuildB, prodBuildC)));
        ProdBuildingRegistry.setBuildings(List.of(prodBuildA, prodBuildB, prodBuildC));
        System.out.println(ProdBuildingRegistry);

        BuildingTypeList<ProductionBuildingType> onlyHuts = new BuildingTypeList<>(ProductionBuildingType.class);
        onlyHuts.setBuildings(ProdBuildingRegistry.getBuildingsByCategory("Hut"));
        //ProdBuildingRegistry.getBuildingsByCategory("Hut");
        System.out.println("Onlyhuts: " + onlyHuts);


    }
}

/*

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingTypeList {
    
    ArrayList<BuildingType> buildingTypeList;


    // empty contructor
    public BuildingTypeList(){
        this.buildingTypeList = new ArrayList<>();
    }

    // CONSTRUCTOR FROM LIST
    @JsonCreator
    public BuildingTypeList(ArrayList<BuildingType> buildingTypeList){
        this.buildingTypeList = buildingTypeList;
    }


    // GET OR SET ALL BUILDING LIST
    public ArrayList<BuildingType> getBuildingTypeList() {
        return buildingTypeList;
    }

    public void setBuildingTypeList(ArrayList<BuildingType> buildingTypeList) {
        this.buildingTypeList = buildingTypeList;
    }

    // GETTER
    public BuildingType get(int i){
        return this.buildingTypeList.get(i);
    }


    // GET SIZE
    public int size(){
        return this.buildingTypeList.size();
    }


    // ADDER
    public void add(BuildingType buildingType){
        this.buildingTypeList.add(buildingType);
    }


    // READ JSON
    ///* 
    public static List<ProductionBuildingType> loadFromBuildingTypes(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductionBuildingConfig1 config = objectMapper.readValue(new File(filePath), ProductionBuildingConfig1.class);
        return config.getBuildings(); // Assuming the StagedBuildingConfig class maps correctly
    }

    // WRITE JSON
    public static void saveProductionBuildingTypes(ProductionBuildingConfig1 config, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), config);
    }

}
 */

