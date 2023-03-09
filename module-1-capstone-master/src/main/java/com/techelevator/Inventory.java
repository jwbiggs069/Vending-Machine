package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Inventory {
    /*stores the data from a file containing information about the
    snacks*/
    private static List<String> vendingFileData = new ArrayList<>();
    /*maps the snack's slot ID to a Snack
    object containing information about the snack
     */
    private static LinkedHashMap<String, Snack> snacks = new LinkedHashMap<>(); // snacks now display in order
    /*represents the file containing the snack data.*/
    private final File vendingFile = new File("vendingmachine.csv");

    /*reads the data from the vendingFile and stores it in the snacks
    map and the vendingFileData list. It catches a
    FileNotFoundException if the file cannot be found.
     */
    public String InventoryStock() {
        try {
            Scanner InventoryStock = new Scanner(vendingFile);
            while (InventoryStock.hasNextLine()) {
                String inventoryLine = InventoryStock.nextLine();
                String[] stringsplit = inventoryLine.split("\\|");
                Snack object = new Snack(stringsplit[0], stringsplit[1], Double.parseDouble(stringsplit[2]), stringsplit[3]);
                snacks.put(object.getSlotID(), object);
                vendingFileData.add(stringsplit[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());
        }

        return "Inventory Stock.";
    }
    /*iterates through the snacks map and prints the information
    about each snack to the console.
     */
    public String displayInventory() {
        //advaced for loop
        for (Snack i : snacks.values()) {
            System.out.println(i.toString());
        }
        // look into Linked Hashmap, or Tree Map to order results
        return "\r\n Back to main menu.";
    }
    /*return the vendingFileData list and the snacks map, respectively.*/
    public static List<String> getVendingFileData() {
        return vendingFileData;
    }
    public static LinkedHashMap<String, Snack> getSnacks() {
        return snacks;
    }
}