package com.productionchain.datatypesold;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;



//@JsonDeserialize(as = ArrayList.class)
public class ItemList {
    @JsonProperty("itemlist")
    //public ArrayList<Item> itemlist = new ArrayList<>();
    public List<Item> itemlist; // = new ArrayList<>();


    public ItemList(){
        // empty constructor
        this.itemlist = new ArrayList<>();
    }

    public ItemList(ArrayList<Item> itemlist) {
        this.itemlist = itemlist;
    }

    public ItemList(List<Item> itemlist) {
        this.itemlist = itemlist;
    }

    public int size(){
        return itemlist.size();
    }



    public void add(Item item){
        itemlist.add(item);
    }

    public void set(int i, Item item){
        itemlist.set(i, item);
    }

    public Item get(int i){
        return itemlist.get(i);
    }

    public List<Item> getItemList() {
        return itemlist;
    }

    /*public void setItemlist(ArrayList<Item> itemlist) {
        this.itemlist = itemlist;
    }*/
    public void setItemlist(List<Item> itemlist) {
        this.itemlist = itemlist;
    }


    
    // DATA RELATED ================================================================================
    public String[] getItemListNames(){
        String[] ItemListNames = new String[itemlist.size()];
        for (int i = 0; i < itemlist.size(); i++) {
            ItemListNames[i] = itemlist.get(i).getName();
        }
        return ItemListNames;
    }


    // RETURNS -1 IF ITEM NOT IN
    public int getIndexOfItemByName(String item_name){
        boolean found = false;
        int i = 0;
        int n_items = itemlist.size();
        while(!found && i < n_items){
            if(itemlist.get(i).getName() == null ? item_name == null : itemlist.get(i).getName().equals(item_name)){
                found = true;
            } else { i++; }
        }
        if(!found){System.out.println("[NAME NOT FOUND]"); i=-1;}
        return i;
    }
    


    // FILE HANDLING ============================================================================
    // WRITE TO FILE
    public void SaveItems(String FILENAME){ // "SavedData/itemlist.json"
        
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
        for (int i = 0; i < itemlist.size(); i++) {
            Item item_ = itemlist.get(i); 
            try {
                String json = om.writer(printer).writeValueAsString(item_);
                System.out.println(json);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("[ATTEMPTING TO PRINT EVERYTHING]");
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



    public ItemList LoadFromFile(String FILENAME){

        // DEFINE MAPPER AND PRINTER
        ObjectMapper om = new ObjectMapper();
        //om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentArraysWith(new DefaultIndenter("  ", "\n"));
        printer.withObjectIndenter(new DefaultIndenter("  ", "\n"));
        
        /* om.setVisibility(om.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY)
                .withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE));
        */ 
        ItemList ITEMLIST_;

        try {

            // read JSON from a file and convert it to Java object
            //ItemList ITEMLIST = om.readValue(new File(FILENAME), new TypeReference<ArrayList<Item>>() {});
            //List<Item> itemlist_ = om.readValue(new File(FILENAME), new TypeReference<List<Item>>() {});
            ITEMLIST_ = om.readValue(new File(FILENAME), ItemList.class);
            System.out.println("ItemList read from JSON:");
            System.out.println(ITEMLIST_);
            //ItemList ITEMLIST = new ItemList(itemlist_);
            //ITEMLIST.setItemlist(new ArrayList<>(itemlist));
            //System.out.println(ITEMLIST);
            JOptionPane.showMessageDialog(null,
                "Items data loaded successfully",
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
