package org.millida.dunespiceflight.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.millida.dunespiceflight.utils.InventoryManager;

public abstract class AbstractInventory<T> {
    private String name;

    public AbstractInventory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Inventory createInventory(T object);
    public abstract void onClickEvent(InventoryClickEvent event);
}