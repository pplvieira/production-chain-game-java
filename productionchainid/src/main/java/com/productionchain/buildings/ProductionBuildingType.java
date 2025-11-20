package com.productionchain.buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;
import com.productionchain.enginedatatypes.RecipeRegistry;
import com.productionchain.enginedatatypes.ResourceLoader;
import com.productionchain.mechanics.RecipeHandler;


//@JsonTypeName("ProductionBuildingType__")
public class ProductionBuildingType extends BuildingType{
    
    // int baseNumOperations;
    // ArrayList<String> baseRecipeCategories;


    public ProductionBuildingType(){
        super();
        // this.baseNumOperations = 1;
        // this.baseRecipeCategories = new ArrayList<>();
    }

    public ProductionBuildingType(@JsonProperty("name") String name, 
        @JsonProperty("description") String description,
        @JsonProperty("category") String category,
        @JsonProperty("baseEfficiency") double baseEfficiency,
        @JsonProperty("baseDegradationRate") double baseDegradationRate,
        @JsonProperty("buildingCost") ArrayList buildingCost,
        @JsonProperty("n_upgrades") int n_upgrades,
        @JsonProperty("upgrades") ArrayList<String> upgrades,
        @JsonProperty("baseRecipeCategories") RecipeHandler recipeHandler){
        super(name, description, category, baseEfficiency, baseDegradationRate, buildingCost, n_upgrades, upgrades, recipeHandler);
        // this.baseNumOperations    = baseNumOperations;
        // this.baseRecipeCategories = baseRecipeCategories;
    }


    // @Override
    // public String getType() {
    //     return "Production"; // Explicitly return "Production"
    // }


    // READ JSON
    ///* 
    public static List<ProductionBuildingType> loadProductionBuildingTypes(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductionBuildingConfig1 config = objectMapper.readValue(new File(filePath), ProductionBuildingConfig1.class);
        return config.getBuildings(); // Assuming the StagedBuildingConfig class maps correctly
    }
    // */
    
    // READ JSON
    /* 
    public static List<BuildingType> loadProductionBuildingTypes(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductionBuildingConfig1 config = objectMapper.readValue(new File(filePath), ProductionBuildingConfig1.class);
        return config.getBuildings(); // Assuming the StagedBuildingConfig class maps correctly
    }
    //*/


    // WRITE JSON
    public static void saveProductionBuildingTypes(ProductionBuildingConfig1 config, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), config);
    }




    @Override
    public String toString(){
        return "Production Building: " + this.name + " | "  + this.category + " | " + this.description + " | " + this.baseDegradationRate + " ~ " + this.baseEfficiency +
            " || " + this.getRecipeHandler().getBaseNumOperations() + " | " + this.getRecipeHandler().getBaseRecipeCategories();
    }


    /// ========================================================================================================================
    /// ========================================================================================================================

    public static void main(String[] args) throws IOException {
        // BUILD AND WRITE THE FIRST ONE

        // CONSTRUCT EXAMPLE BUILDING
        ArrayList<IngredientPair> buildingCost = new ArrayList<>();
        buildingCost.add(new IngredientPair("Wood", 1));
        ProductionBuildingType buildingTest = new ProductionBuildingType("teste", "desc", "Field", 1, 1, buildingCost, 0, new ArrayList<>(), new RecipeHandler());
        //ProductionBuildingType buildingTest = new ProductionBuildingType("teste", "desc", "Field", 1, 1, buildingCost, 0, new ArrayList<>(), new RecipeHandler(), 1, new ArrayList<>());

        // ADD NEW BUILDING TO THE LIST
        ProductionBuildingConfig1 ProductionBuildingList = new ProductionBuildingConfig1();
        ProductionBuildingList.addBuilding(buildingTest);





        // CHOSE DIRECTORY TO WRITE
        ///*
        //JFileChooser fileChooser = new JFileChooser();
        JFileChooser fileChooser = new JFileChooser(ResourceLoader.getResourceFolderPath("buildingtypes/ProductionBuildings.json"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CHOSE DIRECTORY OF BUILDINGS JSONS", "gif");
        //fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Directory: " +
            fileChooser.getSelectedFile().getName());
            System.out.println("Full path to directory: " +
            fileChooser.getSelectedFile().getPath());
        } //*/



        // WRITE TO FILE IN DIRECTORY
        String FILENAME = "ProductionBuildings.json";
        try {
            saveProductionBuildingTypes(ProductionBuildingList, fileChooser.getSelectedFile().getPath() + "\\" + FILENAME);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        
        // RELOAD AND WRITE AGAIN
        try {
            //List<ProductionBuildingType> loadedlist = loadProductionBuildingTypes(fileChooser.getSelectedFile().getPath() + "\\" + FILENAME);
            List<ProductionBuildingType> loadedlist = loadProductionBuildingTypes(fileChooser.getSelectedFile().getPath() + "\\" + FILENAME);
            

            /// CREATE NEW BUILDINGING AND ADD IT
            ArrayList<IngredientPair> IngredientPair = new ArrayList<>();
            IngredientPair.add(new IngredientPair("itemA", 1));
            
            ArrayList<String> upgrades = new ArrayList<>();
            upgrades.add("upgrade1");

            ArrayList<String> baseRecipeCategories = new ArrayList<>();
            baseRecipeCategories.add("recipeCat1");

            ProductionBuildingType buildB = new ProductionBuildingType("BuildA", "ajsd", "production", 1, 0.1, IngredientPair, 1, upgrades, new RecipeHandler());
            
            loadedlist.add(buildB);
            System.out.println("BUILDB" + buildB);
            // ==================== ^^^^^^^^^^ ========================

            saveProductionBuildingTypes(new ProductionBuildingConfig1(loadedlist), fileChooser.getSelectedFile().getPath() + "\\" + "FILENAME.json");


            //ProductionBuildingType building1 = loadedlist.get(0);
            BuildingType building1 = loadedlist.get(0);
            System.out.println("------ testes ------");
            System.out.println(building1);
            System.out.println(building1.getCategory());
            ProductionBuildingType buildingTransformed = (ProductionBuildingType) building1;
            System.out.println(buildingTransformed.getRecipeHandler().getBaseNumOperations());
            


            BuildingInstance buildingInstance = new ProductionBuildingInstance(buildingTransformed, "DRINZO", 0, 0, 0);
            System.out.println("[instance]: " + buildingInstance.getCondition());
            System.out.println("[instance]: " + buildingInstance.getType().getDescription() + " | " + buildingInstance.getType().getCategory());
            System.out.println("[instance]: " + ((ProductionBuildingType) buildingInstance.getType()).getRecipeHandler().getBaseNumOperations());
            System.out.println("[instance]: " + ((ProductionBuildingType) buildingInstance.getType()).getRecipeHandler().getBaseRecipeCategories());

            //ProductionBuildingType b = new ProductionBuildingType(1);
            // ProductionBuildingType b = new ProductionBuildingType();
            // //ProductionBuildingType b = new BuildingType(1);
            // BuildingType a = (BuildingType) b;
            // System.out.println(a);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        


        /*
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        //DirectoryDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        //dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        String directory = dialog.getDirectory();
        String whateva = dialog.getName();
        dialog.dispose();
        System.out.println(file + " chosen.");
        System.out.println("Directory:" + directory);
        System.out.println("whateva:" + whateva);
        ///*


        /*
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
            // What to do with the file, e.g. display it in a TextArea
            textarea.read( new FileReader( file.getAbsolutePath() ), null );
            } catch (IOException ex) {
            System.out.println("problem accessing file"+file.getAbsolutePath());
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
        */



        /*
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creating a new JFXPanel (even if we don't use it), will initialize the JavaFX toolkit.
        new JFXPanel();

        DirectoryChooser directoryChooser = new DirectoryChooser();

        JButton button = new JButton("Choose directory");
        button.addActionListener(e ->
            // runLater is called to create the directory chooser on the JavaFX application thread.
            Platform.runLater(() -> {
                File selectedDirectory = directoryChooser.showDialog(null);
                System.out.println(selectedDirectory);
            })
        );
        frame.getContentPane().add(button);

        frame.pack();
        frame.setVisible(true);
        */
    }

}







class ProductionBuildingConfig1 { //ORIGINAL
    private List<ProductionBuildingType> buildings;

    // CONSTRUCTOR
    public ProductionBuildingConfig1(){ this.buildings = new ArrayList<>(); }
    public ProductionBuildingConfig1(List<ProductionBuildingType> buildings){ this.buildings = buildings; }

    // Getters and Setters
    public List<ProductionBuildingType> getBuildings() { return buildings; }
    public void setBuildings(List<ProductionBuildingType> buildings) { this.buildings = buildings; }
    public void addBuilding(ProductionBuildingType building) { this.buildings.add(building); }
}

// class ProductionBuildingConfigNEW { // NEW
//     private List<BuildingType> buildings;

//     // CONSTRUCTOR
//     public ProductionBuildingConfigNEW(){ this.buildings = new ArrayList<>(); }
//     public ProductionBuildingConfigNEW(List<BuildingType> buildings){ this.buildings = buildings; }

//     // Getters and Setters
//     public List<BuildingType> getBuildings() { return buildings; }
//     public void setBuildings(List<BuildingType> buildings) { this.buildings = buildings; }
//     public void addBuilding(BuildingType building) { this.buildings.add(building); }
// }
