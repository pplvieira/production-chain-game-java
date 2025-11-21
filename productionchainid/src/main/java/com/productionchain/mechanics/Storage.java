package com.productionchain.mechanics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;




@JsonIgnoreProperties(ignoreUnknown = true)
public class Storage {
    
    double capacity;
    Map<String, Double> storedItems;
    ArrayList<String> allowedItems;


    // CONSTRUCTORS
    public Storage() {
        this.capacity = -1.0;
        this.storedItems = new HashMap<>();
        //this.allowedItems = new AbstractSet<String>(); // DONT EVEN INITIALIZE
    }

    public Storage(Double capacity) {
        this.capacity = capacity;
        this.storedItems = new HashMap<>();
    }

    public Storage(Double capacity, ArrayList<String> allowedItems) {
        this.capacity = capacity;
        this.storedItems = new HashMap<>();
        this.allowedItems = allowedItems;
    }


    // SETTERS
    public void setCapacity(Double new_capacity){
        this.capacity = new_capacity;
    }

    /** Add an item to storage */
    public void addItem(String itemName, double quantity) {
        storedItems.put(itemName, storedItems.getOrDefault(itemName, 0.0) + quantity);
    }

    /** Add an item to storage. CHECK IS HAS ENOUGH SPACE */
    public void addItemCheckCapacity(String itemName, double quantity) {
        if (this.allowedItems != null && !this.allowedItems.contains(itemName)){ // check if allowed item
            System.out.println("[WARNING]: CANNOT STORE -" + itemName + "- HERE!");
        } else if (this.getUsedCapacity() + quantity > this.capacity){           // check if enough storage
            System.out.println("[WARNING]: NOT ENOUGH SPACE TO ADD ITEM! (" + itemName + ", " + quantity + ")");
        } else {
            storedItems.put(itemName, storedItems.getOrDefault(itemName, 0.0) + quantity);
        }
    }

    /** Remove an item (only if enough stock exists) */
    public boolean removeItem(String itemName, double quantity) {
        if (storedItems.getOrDefault(itemName, 0.0) >= quantity) {
            storedItems.put(itemName, storedItems.get(itemName) - quantity);
            if (storedItems.get(itemName) <= 0) {
                storedItems.remove(itemName); // Remove empty storedItems
            }
            return true;
        }
        return false; // Not enough stock
    }



    // GETTERS  
    @JsonIgnore
    public double getCapacity() {
        return capacity;
    }

    /** Sums over total capacity */
    @JsonIgnore
    public double getUsedCapacity() {
        double sum = 0;
        for (Double value : this.storedItems.values()) {
            sum += value;
        }
        return sum;
    }

    /** Check if storage has enough of an item */
    public boolean hasItem(String itemName, double quantity) {
        return storedItems.getOrDefault(itemName, 0.0) >= quantity;
    }

    /** Check if all ingredients for a recipe are available */
    public boolean hasIngredients(Recipe recipe) {
        for (IngredientPair ingredient : recipe.getIngredientslist().getIngredientslist()) {
            if (!hasItem(ingredient.getItem_name(), ingredient.getCount())) {
                return false;
            }
        }
        return true;
    }

    /** Get current stored storedItems */
    public Map<String, Double> getItems() {
        return storedItems;
    }



    // FILE HANDLING NEW ============================================================================
    // Read from JSON file
    public void loadFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.storedItems = mapper.readValue(new File(filePath), //,
            //RecipeList.class);
            //mapper.getTypeFactory().constructCollectionType(List.class, IngredientPair.class));
            mapper.getTypeFactory().constructMapType(Map.class, String.class, Double.class));
    }

    // Write to JSON file
    public void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.writeValue(new File(filePath), this.storedItems); //writerWithDefaultPrettyPrinter()
    }


    @Override
    public String toString() {
        return "Storage: " + storedItems.toString();
    }





    public static void main(String[] args) throws IOException {
        
        Storage storageA = new Storage(5.0);
        storageA.addItemCheckCapacity("Wood", 6);
        storageA.addItemCheckCapacity("Iron", 1);
        storageA.addItemCheckCapacity("Stick", 2);
        storageA.addItemCheckCapacity("Coal", 3);

        System.out.println(storageA);

        
        ArrayList<String> allowedItems_ = new ArrayList<>();
        allowedItems_.add("Wood");
        allowedItems_.add("Stick");
        allowedItems_.add("Lumber");
        Storage storageB = new Storage(5.0, allowedItems_);
        storageB.addItemCheckCapacity("Wood", 6);
        storageB.addItemCheckCapacity("Iron", 1);
        storageB.addItemCheckCapacity("Stick", 2);
        storageB.addItemCheckCapacity("Lumber", 2);
        storageB.addItemCheckCapacity("Wood", 1);

        System.out.println(storageB);

        
        storageB.saveToJson("storage_teste.json");


        //RecipeList readRecipes = listaReceitas.LoadFromFile("receitas_teste.json");
        Storage storageC = new Storage();
        storageC.loadFromJson("storage_teste.json");
        System.out.println(storageC);

    }



    
}
