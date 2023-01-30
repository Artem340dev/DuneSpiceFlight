package org.millida.dunespiceflight.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.millida.dunespiceflight.inventory.AbstractPlayerInventory;
import org.millida.dunespiceflight.utils.InventoryManager;

public class InventoryHandler implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        AbstractPlayerInventory inventory = InventoryManager.getInventory(event.getView().getTitle());
        if (inventory == null) return;

        event.setCancelled(true);
        event.getWhoClicked().closeInventory();

        inventory.onClickEvent(event);
    }
}