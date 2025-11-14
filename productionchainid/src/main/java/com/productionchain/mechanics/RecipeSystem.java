package com.productionchain.mechanics;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.productionchain.buildings.ProductionBuildingType;
import com.productionchain.enginedatatypes.IngredientPair;
import com.productionchain.enginedatatypes.Recipe;

// IMPLEMENTED BY BUILDING INSTANCES (SCRAPPED)
public class RecipeSystem {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Recipe activeRecipe;

    
    //public RecipeSystem(){}

    public RecipeSystem(){

    }


    // // RECIPES 
    // // public void setActiveRecipe(String recipe) { this.activeRecipe = recipe; }
    // // public String getActiveRecipe() { return activeRecipe; }
    // public boolean canRunRecipe(Recipe recipe) { // call available recipes from this production building type
    //     return ((ProductionBuildingType) this.getType()).canRunRecipe(recipe);
    // }

    // @JsonIgnore
    // public List<Recipe> getAvailableRecipes() {  // call available recipes from this production building type
    //     return ((ProductionBuildingType) this.getType()).getAvailableRecipes();
    // }
    
    // public void setActiveRecipe(Recipe recipe) {
    //     if(recipe == null) {this.activeRecipe = null;}
    //     else {
    //         if(canRunRecipe(recipe)){ this.activeRecipe = recipe; }
    //         else { System.out.println("Building cannot run recipe! (" + recipe.getName() + ")"); }
    //     }
    // }

    // public Recipe getActiveRecipe() { return activeRecipe; }


    // public boolean hasAllIngredientsForRecipe(){ return hasAllIngredientsForRecipe(this.activeRecipe); }
    // public boolean hasAllIngredientsForRecipe(Recipe recipe){
    //     boolean hasAllIngredients = true;
    //     // FIRST CHECK IF HAS ALL ITEMS 
    //     if (recipe.getIngredientslist() != null){
    //         for (IngredientPair ingredientPair : recipe.getIngredientslist().getIngredientslist()) {
    //             if(!this.getStorage().hasItem(ingredientPair.getItem_name(), ingredientPair.getCount())){hasAllIngredients = false;}
    //         }
    //     }
    //     return hasAllIngredients;
    // }


    // public boolean hasStorageSpaceForRecipe(){ return hasStorageSpaceForRecipe(this.activeRecipe); }
    // public boolean hasStorageSpaceForRecipe(Recipe recipe){
    //     double neededStorageSpace = this.getStorage().getUsedCapacity();
        
    //     // FIRST CHECK IF HAS ALL SPACE FOR MORE
    //     for (IngredientPair ingredientPair : this.activeRecipe.getIngredientslist().getIngredientslist()) {
    //         neededStorageSpace -= ingredientPair.getCount();
    //     }
    //     for (IngredientPair outputPair : this.activeRecipe.getOutputslist().getIngredientslist()) {
    //         neededStorageSpace += outputPair.getCount();
    //     }
    //     return (this.getStorage().getCapacity() >= neededStorageSpace);
    // }


    // /** Perform recipe by converting ingredients into outputs. Checks if has all ingredients and space for outputs */
    // public void performRecipe(){
    //     if(this.getActiveRecipe() != null){
    //         boolean hasAllIngredients = hasAllIngredientsForRecipe();
    //         boolean hasStorageSpace   = hasStorageSpaceForRecipe();

    //         // ACTUALLY REMOVES INGREDIENTS AND ADDS OUTPUTS
    //         if(hasAllIngredients){
    //             if (hasStorageSpace) {
    //                 for (IngredientPair ingredientPair : this.activeRecipe.getIngredientslist().getIngredientslist()) {
    //                     this.storage.removeItem(ingredientPair.getItem_name(), ingredientPair.getCount());
    //                 }
    //                 for (IngredientPair outputPair : this.activeRecipe.getOutputslist().getIngredientslist()) {
    //                     this.storage.addItem(outputPair.getItem_name(), outputPair.getCount(), 10);
    //                 }
    //             } else {System.out.println("[ERROR]: No storage space to perform recipe");}
    //         } else {System.out.println("[ERROR]: Missing some ingredients");}

    //     } else {
    //         System.out.println("No selected recipe!");
    //     }
    //     // DEACTIVATE RECIPE
    //     this.setActiveRecipe(null);
    // }



}
