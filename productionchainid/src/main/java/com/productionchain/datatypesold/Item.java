package com.productionchain.datatypesold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    // REMOVE O item_ DAQUI !!!
    
    String name;

    String category;

    double storage_space;
    double weight;
    double how_long_to_go_bad;

    boolean item_isDurable;
    boolean item_isStorable;
    boolean item_isTransportable;

    //double[] testeapagar = {1,2,3,4};

    // EMPTY CONSTRUCTOR
    public Item(){
    }

    // CONTSTRUCTOR
    //@JsonCreator
    public Item(@JsonProperty("name") String name, 
        @JsonProperty("storage_space") double storage_space, 
        @JsonProperty("weight") double weight, 
        @JsonProperty("how_long_to_go_bad") Double how_long_to_go_bad, 
        @JsonProperty("item_isDurable") boolean item_isDurable, 
        @JsonProperty("item_isStorable") boolean item_isStorable, 
        @JsonProperty("item_isTransportable") boolean item_isTransportable){
        
        this.name = name;
        this.storage_space = storage_space;
        this.weight = weight;
        this.how_long_to_go_bad = how_long_to_go_bad;
        this.item_isDurable = item_isDurable;
        this.item_isStorable = item_isStorable;
        this.item_isTransportable = item_isTransportable;
    }

    // CONTSTRUCTOR
    @JsonCreator
    public Item(@JsonProperty("name") String name, 
        @JsonProperty("category") String category, 
        @JsonProperty("storage_space") double storage_space, 
        @JsonProperty("weight") double weight, 
        @JsonProperty("how_long_to_go_bad") Double how_long_to_go_bad, 
        @JsonProperty("item_isDurable") boolean item_isDurable, 
        @JsonProperty("item_isStorable") boolean item_isStorable, 
        @JsonProperty("item_isTransportable") boolean item_isTransportable){
        
        this.name = name;
        this.category = category;
        this.storage_space = storage_space;
        this.weight = weight;
        this.how_long_to_go_bad = how_long_to_go_bad;
        this.item_isDurable = item_isDurable;
        this.item_isStorable = item_isStorable;
        this.item_isTransportable = item_isTransportable;
    }


    public String getName(){
        return name;
    }
    public double getStorageSpace(){
        return storage_space;
    }
    public double getWeight(){
        return weight;
    }
    public boolean getIsDurable(){
        return item_isDurable;
    }
    public boolean getIsStorable(){
        return item_isStorable;
    }
    public boolean getIsTransportable(){
        return item_isTransportable;
    }
    public double getHowLongToGoBad(){
        return how_long_to_go_bad;
    }
    public String getCategory() {
        return category;
    }


    @Override
    public String toString(){
    return "Name: " + name + "\n-Weights: " + weight
                + "\n-Is durable: " + item_isStorable;
    }




}
