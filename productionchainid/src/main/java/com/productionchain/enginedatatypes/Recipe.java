package com.productionchain.enginedatatypes;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    String          name;
    String          category;
    String          description;
    int             n_ingredients;
    int             n_outputs;
    double          duration;
    boolean         enabled;

    //@JsonUnwrapped
    IngredientsList ingredientslist;
    //@JsonUnwrapped
    IngredientsList outputslist;
    

    public Recipe() {} {}

    // empty constructor
    public Recipe(int n_ingredients, int n_outputs){
        this.name = "";
        this.category = "";
        this.description = "";
        this.n_ingredients = n_ingredients;
        this.n_outputs = n_outputs;
        //this.ingredientslist = new IngredientsList(n_ingredients);
        //this.outputslist = new IngredientsList(n_outputs);

        List<IngredientPair> ingredientslist_ = Stream.generate(IngredientPair::new).limit(n_ingredients).collect(Collectors.toList());
        this.ingredientslist = new IngredientsList(ingredientslist_);

        List<IngredientPair> outputslist_ = Stream.generate(IngredientPair::new).limit(n_outputs).collect(Collectors.toList());
        this.outputslist = new IngredientsList(outputslist_);

        this.duration = 0;
        this.enabled = false;
    }

    // CONTSTRUCTOR
    @JsonCreator
    public Recipe(
        @JsonProperty("name") String name,
        @JsonProperty("category") String category,
        @JsonProperty("description") String description,
        @JsonProperty("n_ingredients") int n_ingredients,
        @JsonProperty("n_outputs") int n_outputs,
        @JsonProperty("ingredients") IngredientsList ingredientslist,
        @JsonProperty("outputs") IngredientsList outputslist,
        @JsonProperty("duration") double duration,
        @JsonProperty("enabled") boolean enabled){

        this.name = name;
        this.category = category;
        this.description = description;
        this.n_ingredients = n_ingredients;
        this.n_outputs = n_outputs;
        this.ingredientslist = ingredientslist;
        this.outputslist = outputslist;
        this.duration = duration;
        this.enabled = enabled;
    }


    

    public void setIngredientslist(IngredientsList ingredientslist) {
        this.ingredientslist = ingredientslist;
    }


    // GETTERS
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getN_ingredients() {
        return n_ingredients;
    }

    public int getN_outputs() {
        return n_outputs;
    }

    public IngredientsList getIngredientslist() {
        return ingredientslist;
    }

    public IngredientsList getOutputslist() {
        return outputslist;
    }

    public double getDuration() {
        return duration;
    }

    public boolean isEnabled() {
        return enabled;
    }



    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(String.format("Recipe[name=%s, category=%s, duration=%.1f, enabled=%b]",
            name, category, duration, enabled));
        result.append(String.format("\n  Description: %s", description));
        result.append(String.format("\n  Recipe: %d ingredient(s) → %d output(s)", n_ingredients, n_outputs));

        if (ingredientslist != null && ingredientslist.size() > 0) {
            result.append("\n  Ingredients:");
            for (int i = 0; i < ingredientslist.size(); i++) {
                result.append(String.format("\n    [%d] %s", i, ingredientslist.get(i).toString()));
            }
        } else {
            result.append("\n  Ingredients: (none - gathering recipe)");
        }

        if (outputslist != null && outputslist.size() > 0) {
            result.append("\n  Outputs:");
            for (int i = 0; i < outputslist.size(); i++) {
                result.append(String.format("\n    [%d] %s", i, outputslist.get(i).toString()));
            }
        } else {
            result.append("\n  Outputs: (none)");
        }

        return result.toString();
    }
}

