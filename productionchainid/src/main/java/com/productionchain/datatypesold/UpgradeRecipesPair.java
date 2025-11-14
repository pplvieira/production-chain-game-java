package com.productionchain.datatypesold;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpgradeRecipesPair {

    String upgrade_name;
    ArrayList<String> recipes_enabled;


    public UpgradeRecipesPair(){
        upgrade_name = "";
        recipes_enabled = new ArrayList<>();
        //recipes_enabled = "none";
    }

    @JsonCreator
    public UpgradeRecipesPair( 
        @JsonProperty("upgrade_name") String upgrade_name, 
        @JsonProperty("recipes_enabled") ArrayList<String> recipes_enabled){

        this.upgrade_name = upgrade_name;
        this.recipes_enabled = recipes_enabled;
    }


    public String getUpgrade_name() {
        return upgrade_name;
    }

    public ArrayList<String> getRecipes_enabled() {
        return recipes_enabled;
    }



}
