package com.productionchain.mechanics;


import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


// DOESNT EXTEND STORAGE. IS A CLASS ON INTS OWN
public class AdvancedStorage { // extends Storage 

    /** Map of item -> list of batches (FIFO queue) */
    double capacity;

    //@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private Map<String, List<ItemBatch>> items = new HashMap<>();;
    //private Map<String, Queue<ItemBatch>> items; // 🔹 Add Type Info

    private ArrayList<String> allowedItems; // Optional: Allowed item types

    public AdvancedStorage() {
        this.capacity = -1.0;
        this.items = new HashMap<>();
    }

    public AdvancedStorage(Double capacity, ArrayList<String> allowedItems) {
        this();
        this.capacity = capacity;
        this.allowedItems = allowedItems;
    }

    // public AdvancedStorage(double capacity, Map<String, Queue<ItemBatch>> items) {
    //     this.capacity = capacity;
    //     this.items = items;
    // }

    // public AdvancedStorage(ArrayList<String> allowedItems, double capacity, Map<String, Queue<ItemBatch>> items) {
    //     this.allowedItems = allowedItems;
    //     this.capacity = capacity;
    //     this.items = items;
    // }



    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    //@JsonIgnore //???
    public Map<String, List<ItemBatch>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<ItemBatch>> items) {
        this.items = items;
    }


    // ADD ITEMS
    /** Adds an item batch with specific durability */
    public boolean addItem(String itemName, double quantity, double durability) {
        items.putIfAbsent(itemName, new LinkedList<>()); // Ensure queue exists
        //items.get(itemName).offer(new ItemBatch(quantity, durability));
        items.get(itemName).add(new ItemBatch(quantity, durability));
        return true;
    }

    /** Add an item to storage. CHECK IS HAS ENOUGH SPACE */
    public void addItemCheckCapacity(String itemName, double quantity, double durability) {
        if (this.allowedItems != null && !this.allowedItems.contains(itemName)){ // check if allowed item
            System.out.println("[WARNING]: CANNOT STORE -" + itemName + "- HERE!");
        } else if (this.getUsedCapacity() + quantity > this.capacity){           // check if enough storage
            System.out.println("[WARNING]: NOT ENOUGH SPACE TO ADD ITEM! (" + itemName + ", " + quantity + ")");
        } else {
            //items.put(itemName, items.getOrDefault(itemName, 0.0) + quantity);
            items.putIfAbsent(itemName, new LinkedList<>()); // Ensure queue exists
            // items.get(itemName).offer(new ItemBatch(quantity, durability));
            items.get(itemName).add(new ItemBatch(quantity, durability));
        }
    }


    /** Removes items, using the oldest batch first (FIFO) */
    public boolean removeItem(String itemName, double quantity) {
        if (!items.containsKey(itemName)) return false;

        // Check if we have enough total quantity (atomic operation - all or nothing)
        if (!hasItem(itemName, quantity)) {
            return false;
        }

        //Queue<ItemBatch> batches = items.get(itemName);
        List<ItemBatch> batches = items.get(itemName);
        double remainingToRemove = quantity;

        while (!batches.isEmpty() && remainingToRemove > 0) {
            //ItemBatch batch = batches.peek();
            ItemBatch batch = batches.get(0); // equivalent to peek || batches.isEmpty() ? null : batches.get(0);
            if (batch.quantity > remainingToRemove) {
                batch.quantity -= remainingToRemove;
                remainingToRemove = 0;
            } else {
                remainingToRemove -= batch.quantity;
                batches.remove(0);  // Remove expired batch for lists
                // batches.poll(); // Remove expired batch
            }
        }

        // If all batches are removed, clean up the entry
        if (batches.isEmpty()) {
            items.remove(itemName);
        }

        return remainingToRemove == 0;
    }


    /** Sums over total capacity */
    @JsonIgnore
    public double getUsedCapacity() {
        double sum = 0;
        for (List<ItemBatch> batch_list : this.items.values()) {
            //sum += batch_.element().quantity;
            for (ItemBatch batch_ : batch_list) {
                sum += batch_.quantity;
            }
            //sum += batch_.get(0).quantity;
        }
        return sum;
    }


    /** Check if enough quantity exists */
    public boolean hasItem(String itemName, double quantity) {
        if (!items.containsKey(itemName)) return false;
        double totalAvailable = items.get(itemName).stream().mapToDouble(b -> b.quantity).sum();
        return totalAvailable >= quantity;
    }

    /** Get total quantity of an item across all batches */
    public double getItemQuantity(String itemName) {
        if (!items.containsKey(itemName)) return 0.0;
        return items.get(itemName).stream().mapToDouble(b -> b.quantity).sum();
    }

    /** Get available capacity */
    public double getAvailableCapacity() {
        return capacity - getUsedCapacity();
    }




    /** Decreases durability of all batches, removing spoiled ones */
    public void degradeItems() {
        for (String item : new HashSet<>(items.keySet())) {
            List<ItemBatch> batches = items.get(item);
            while (!batches.isEmpty() && batches.get(0).durability <= 1) {
                //batches.poll();
                batches.remove(0);
                System.out.println("X " + item + " batch has spoiled.");
            }

            for (ItemBatch batch : batches) {
                batch.durability--;
            }

            if (batches.isEmpty()) {
                items.remove(item);
            }
        }
    }




    // FILE HANDLING NEW ============================================================================
    // Read from JSON file
    public void loadFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AdvancedStorage loadedStorage = mapper.readValue(new File(filePath), //,
            //RecipeList.class);
            //mapper.getTypeFactory().constructCollectionType(List.class, IngredientPair.class));
            //mapper.getTypeFactory().constructMapType(Map.class, String.class, List.class));
            AdvancedStorage.class);
        this.allowedItems = loadedStorage.allowedItems;
        this.capacity     = loadedStorage.capacity;
        this.items        = loadedStorage.items;
    }

    // Write to JSON file
    public void saveToJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        //mapper.writeValue(new File(filePath), this.items); //writerWithDefaultPrettyPrinter()
        mapper.writeValue(new File(filePath), this); //writerWithDefaultPrettyPrinter()
    }




    @Override
    public String toString() {
        double used = this.getUsedCapacity();
        double available = this.getAvailableCapacity();
        int totalBatches = items.values().stream().mapToInt(List::size).sum();

        StringBuilder result = new StringBuilder();
        result.append(String.format("AdvancedStorage[capacity=%.1f, used=%.1f, available=%.1f, items=%d, batches=%d]",
            capacity, used, available, items.size(), totalBatches));

        if (allowedItems != null && !allowedItems.isEmpty()) {
            result.append("\n  Allowed Items: ").append(allowedItems);
        }

        if (!items.isEmpty()) {
            result.append("\n  Storage Contents:");
            items.forEach((item, batches) -> {
                double totalQty = batches.stream().mapToDouble(b -> b.quantity).sum();
                result.append(String.format("\n    - %s: %d batch(es), total=%.1f units",
                    item, batches.size(), totalQty));
                for (int i = 0; i < batches.size(); i++) {
                    ItemBatch batch = batches.get(i);
                    result.append(String.format("\n      * Batch[%d]: quantity=%.1f units, durability=%.0f turns",
                        i, batch.quantity, batch.durability));
                }
            });
        } else {
            result.append("\n  Storage Contents: (empty)");
        }

        return result.toString();
    }



    /** Inner class to store item batches */
    //@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    private static class ItemBatch {
        double quantity;
        double durability;

        public ItemBatch() {} // default

        public ItemBatch(double quantity, double durability) {
            this.quantity = quantity;
            this.durability = durability;
        }

        public double getQuantity(){
            return quantity;
        }
        public double getDurability(){
            return durability;
        }
    }



    public static void main(String[] args) throws IOException {
        ArrayList<String> allowedItems = new ArrayList<>(Arrays.asList("Wood", "Coal", "Stick"));
        AdvancedStorage advStorage = new AdvancedStorage(10.0, allowedItems);
        System.out.println(advStorage);

        advStorage.addItemCheckCapacity("Iron", 1.0, 3);
        advStorage.addItemCheckCapacity("Wood", 3.0, 3);
        advStorage.addItemCheckCapacity("Coal", 3.0, 4);
        System.out.println(advStorage);

        advStorage.degradeItems();
        System.out.println(advStorage);

        advStorage.addItemCheckCapacity("Wood", 2.0, 3);
        advStorage.addItemCheckCapacity("Wood", 2.0, 2);
        advStorage.degradeItems();
        System.out.println(advStorage);
        
        advStorage.saveToJson("adv_storage_teste.json"); /// SAVE HERE
        System.out.println("[Saved]");

        advStorage.removeItem("Wood", 1);
        System.out.println(advStorage);
        advStorage.removeItem("Wood", 3);
        System.out.println(advStorage);

        advStorage.degradeItems();
        System.out.println(advStorage);
        // advStorage.degradeItems();
        // System.out.println(advStorage);

        
        advStorage.removeItem("Wood", 2);
        System.out.println(advStorage);
        advStorage.degradeItems();
        System.out.println(advStorage);


        
        

        


        //RecipeList readRecipes = listaReceitas.LoadFromFile("receitas_teste.json");
        AdvancedStorage storageC = new AdvancedStorage();
        storageC.loadFromJson("adv_storage_teste.json");
        System.out.println(storageC);

        
    
    }

}
