package com.productionchain.datatypesold;

import java.util.List;

import com.productionchain.buildings.ProductionBuildingType;

//import com.productionchain.buildingtypes.ProductionBuildingType;

public class ProductionBuildingConfig {
    private List<ProductionBuildingType> buildings;

    // Getters and Setters
    public List<ProductionBuildingType> getBuildings() { return buildings; }
    public void setBuildings(List<ProductionBuildingType> buildings) { this.buildings = buildings; }
}
