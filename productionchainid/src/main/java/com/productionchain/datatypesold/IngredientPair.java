package com.productionchain.datatypesold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IngredientPair {
    String item_name;
    double count;
    double probability;


    public IngredientPair(){
        System.out.println("||||| fui acionado");
        this.item_name = "";
        this.count = 1;
        this.probability = 1;
    }


    
    public IngredientPair(
        @JsonProperty("item_name") String item_name, 
        @JsonProperty("count") double count){
        this.item_name = item_name;
        this.count = count;
        this.probability = 1;
    }


    @JsonCreator
    public IngredientPair(
        @JsonProperty("item_name") String item_name, 
        @JsonProperty("count") double count, 
        @JsonProperty("probability") double probability ){
        this.item_name = item_name;
        this.count = count;
        this.probability = probability;
    }



    public String getItem_name() {
        return item_name;
    }

    public double getCount() {
        return count;
    }

    public double getProbability() {
        return probability;
    }


    

}
