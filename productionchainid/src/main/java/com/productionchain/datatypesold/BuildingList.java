package com.productionchain.datatypesold;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;



public class BuildingList {
    //@JsonProperty("buildinglist") // ALERTA REMOVI 

    List<Building> buildinglist;


    public BuildingList(){
        // empty constructor
        this.buildinglist = new ArrayList<>();
    }

    public BuildingList(ArrayList<Building> buildinglist) {
        this.buildinglist = buildinglist;
    }

    public BuildingList(List<Building> buildinglist) {
        this.buildinglist = buildinglist;
    }

    public int size(){
        return buildinglist.size();
    }


    
    public void add(Building building){
        buildinglist.add(building);
    }

    public void set(int i, Building building){
        buildinglist.set(i, building);
    }

    public Building get(int i){
        return buildinglist.get(i);
    }

    public List<Building> getBuildingList() {
        return buildinglist;
    }


    
    // DATA RELATED ================================================================================
    public String[] getBuildingListNames(){
        String[] ItemListNames = new String[buildinglist.size()];
        for (int i = 0; i < buildinglist.size(); i++) {
            ItemListNames[i] = buildinglist.get(i).getName();
        }
        return ItemListNames;
    }


    // RETURNS -1 IF ITEM NOT IN
    public int getIndexOfBuildingByName(String item_name){
        boolean found = false;
        int i = 0;
        int n_items = buildinglist.size();
        while(!found && i < n_items){
            if(buildinglist.get(i).getName() == null ? item_name == null : buildinglist.get(i).getName().equals(item_name)){
                found = true;
            } else { i++; }
        }
        if(!found){System.out.println("[NAME NOT FOUND]"); i=-1;}
        return i;
    }




    // FILE HANDLING ============================================================================
    // WRITE TO FILE
    public void SaveBuildings(String FILENAME){ // "SavedData/buildinglist.json"
        
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

        // PRINT EACH ENTRY:
        System.out.println("\n[Print each entry] =====");
        for (int i = 0; i < buildinglist.size(); i++) {
            Building building_ = buildinglist.get(i); 
            try {
                String json = om.writer(printer).writeValueAsString(building_);
                System.out.println(json);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("[ATTEMPTING TO WRITE EVERYTHING]");
        try {
            om.writeValue(new File(FILENAME), this);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
        }
    }



    public BuildingList LoadFromFile(String FILENAME){
        // DEFINE MAPPER AND PRINTER
        ObjectMapper om = new ObjectMapper();
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentArraysWith(new DefaultIndenter("  ", "\n"));
        printer.withObjectIndenter(new DefaultIndenter("  ", "\n"));
        
        BuildingList ITEMLIST_;

        try {

            ITEMLIST_ = om.readValue(new File(FILENAME), BuildingList.class);
            JOptionPane.showMessageDialog(null,
                "Buildings data loaded successfully",
                "Success",
                JOptionPane.WARNING_MESSAGE);

        } catch (IOException e) {            
            JOptionPane.showMessageDialog(null,
                "Data loading failed! Check the code",
                "Warning",
                JOptionPane.WARNING_MESSAGE);

            throw new RuntimeException(e);
        }

        return ITEMLIST_;
    }


}
