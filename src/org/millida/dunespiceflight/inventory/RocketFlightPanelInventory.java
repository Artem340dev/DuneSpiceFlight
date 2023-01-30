package org.millida.dunespiceflight.inventory;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;
import org.millida.dunespiceflight.objects.RocketEntity;
import org.millida.dunespiceflight.utils.*;

import java.util.Arrays;

public class RocketFlightPanelInventory extends AbstractPlayerInventory {
    public RocketFlightPanelInventory() {
        super("Панель управления полётом");
    }

    @Override
    public Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, this.getName());
        Bukkit.getWorlds().forEach(world -> inventory.addItem(ItemUtil.getItem(world.getName(), Arrays.asList("", "&fНажмите, чтобы &cполететь сюда"), Material.WHITE_WOOL, 1)));

        return inventory;
    }

    @Override
    public void onClickEvent(InventoryClickEvent event) {
        if (!event.getCurrentItem().hasItemMeta()) return;
        Player player = (Player) event.getWhoClicked();

        RocketEntity rocketEntity = RocketEntity.getRocketEntityForCaptain(player);
        if (!rocketEntity.isCaptain(player)) {
            player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "&cОшибка! Вы не командир космического корабля, чтобы выбирать точку для полёта."));
            return;
        }

        String worldName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

        World world = Bukkit.getWorld(worldName);
        Location location = WorldManager.getRandomLocationAtWorldForPlayer(player, world, -100000, 100000);

        rocketEntity.flyTo(location);
    }
}