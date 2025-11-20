package com.productionchain.datatypesold;

import java.util.List;

import com.productionchain.buildings.ProductionBuildingType;

//import com.productionchain.buildingtypes.ProductionBuildingType;

/**
 * @deprecated This class is part of the legacy datatypesold package and should not be used.
 * Use com.productionchain.buildings.BuildingTypeList instead.
 */
@Deprecated
class ProductionBuildingConfig {
    private List<ProductionBuildingType> buildings;

    // Getters and Setters
    public List<ProductionBuildingType> getBuildings() { return buildings; }
    public void setBuildings(List<ProductionBuildingType> buildings) { this.buildings = buildings; }
}
