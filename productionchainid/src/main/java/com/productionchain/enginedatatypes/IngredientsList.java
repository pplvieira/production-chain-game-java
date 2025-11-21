package com.productionchain.enginedatatypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IngredientsList {
    //@JsonProperty("ingredient_list") // ALERTA REMOVI ISTO
    //public ArrayList<Item> itemlist = new ArrayList<>();
    //@JsonUnwrapped //removed as well
    private List<IngredientPair> ingredientslist;


    public IngredientsList(){
        // empty constructor
        this.ingredientslist = new ArrayList<>();
    }

    public IngredientsList(int n_ingredients){
        // empty constructor
        this.ingredientslist = new ArrayList<>(n_ingredients);
    }

    public IngredientsList(ArrayList<IngredientPair> itemlist) {
        this.ingredientslist = itemlist;
    }

    public IngredientsList(List<IngredientPair> itemlist) {
        this.ingredientslist = itemlist;
    }

    public void setIngredientslist(List<IngredientPair> ingredientslist) {
        this.ingredientslist = ingredientslist;
    }

    @JsonCreator  // Deserialize from a list
    public static IngredientsList fromJson(List<IngredientPair> ingredients) {
        return new IngredientsList(ingredients);
    }



    public int size(){
        return ingredientslist.size();
    }

    @JsonValue
    public List<IngredientPair> getIngredientslist() {
        return ingredientslist;
    }
    
    public void add(IngredientPair ingredientPair_){
        ingredientslist.add(ingredientPair_);
    }

    public void set(int i, IngredientPair ingredientPair_){
        ingredientslist.set(i, ingredientPair_);
    }

    public IngredientPair get(int i){
        return ingredientslist.get(i);
    }




    // PROLLY NOT USED
    public void SaveIngredientList(String FILENAME){ 
        
        // DEFINE MAPPER AND PRINTER
        ObjectMapper om = new ObjectMapper();
        //om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentArraysWith(new DefaultIndenter("  ", "\n"));
        printer.withObjectIndenter(new DefaultIndenter("  ", "\n"));
        
        om.setVisibility(om.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY)
                .withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE));


        System.out.println("[ATTEMPTING TO WRITE EVERYTHING]");
        try {
            // covert Java object to JSON strings
            String json = om.writer(printer).writeValueAsString(this);
            //String json = om.writer(printer).writeValue(itemlist);

            System.out.println(json);

            om.writeValue(new File(FILENAME), this);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }



    public IngredientsList LoadFromFile(String FILENAME){
        // DEFINE MAPPER AND PRINTER
        ObjectMapper om = new ObjectMapper();
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentArraysWith(new DefaultIndenter("  ", "\n"));
        printer.withObjectIndenter(new DefaultIndenter("  ", "\n"));
        
        IngredientsList READLIST_;

        try {
            // read JSON from a file and convert it to Java object
            READLIST_ = om.readValue(new File(FILENAME), IngredientsList.class);
            System.out.println("ItemList read from JSON:");
            System.out.println(READLIST_);

        } catch (IOException e) {            
            System.out.println("ItemList read from JSON:");

            throw new RuntimeException(e);
        }

        return READLIST_;
    }


    @Override
    public String toString() {
        if (ingredientslist == null || ingredientslist.isEmpty()) {
            return "IngredientsList[size=0] (empty)";
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("IngredientsList[size=%d]", ingredientslist.size()));
        for (int i = 0; i < ingredientslist.size(); i++) {
            result.append(String.format("\n  [%d] %s", i, ingredientslist.get(i).toString()));
        }
        return result.toString();
    }


}
