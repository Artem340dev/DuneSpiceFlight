package org.millida.dunespiceflight.utils;

import org.millida.dunespiceflight.inventory.AbstractInventory;
import org.millida.dunespiceflight.inventory.AbstractPlayerInventory;

import java.util.HashMap;

public class InventoryManager {
    private static HashMap<String, AbstractPlayerInventory> inventories;

    static {
        inventories = new HashMap<>();
    }

    public static void registerInventory(String name, AbstractPlayerInventory inventory) {
        inventories.put(name, inventory);
    }

    public static AbstractPlayerInventory getInventory(String name) {
        return inventories.get(name);
    }
}