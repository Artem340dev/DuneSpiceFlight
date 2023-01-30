package org.millida.dunespiceflight.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.millida.dunespiceflight.utils.InventoryManager;

public abstract class AbstractPlayerInventory extends AbstractInventory<Player> {
    public AbstractPlayerInventory(String name) {
        super(name);
        InventoryManager.registerInventory(name, this);
    }
}