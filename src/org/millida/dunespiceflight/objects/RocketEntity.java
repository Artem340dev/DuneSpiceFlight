package org.millida.dunespiceflight.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;
import org.millida.dunespiceflight.threads.RocketAnimationThread;
import org.millida.dunespiceflight.utils.ChatUtil;
import org.millida.dunespiceflight.utils.RocketManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RocketEntity {
    private static List<RocketEntity> entities = new ArrayList<>();

    private ArmorStand rocketArmorStand, captainArmorStand;
    private List<ArmorStand> passengersArmorStands;

    public RocketEntity(ArmorStand rocketArmorStand, ArmorStand captainArmorStand, List<ArmorStand> passengersArmorStands) {
        this.rocketArmorStand = rocketArmorStand;
        this.captainArmorStand = captainArmorStand;
        this.passengersArmorStands = passengersArmorStands;

        entities.add(this);
    }

    public ArmorStand getCaptainArmorStand() {
        return captainArmorStand;
    }

    public ArmorStand getRocketArmorStand() {
        return rocketArmorStand;
    }

    public List<Player> getPassengers() {
        List<Player> passengers = new ArrayList<>();
        passengersArmorStands.stream().filter(passenger -> passenger.getPassenger() != null && passenger.getPassenger().getType().equals(EntityType.PLAYER)).forEach(passenger -> passengers.add((Player) passenger.getPassenger()));

        return passengers;
    }

    public List<ArmorStand> getPassengersArmorStands() {
        return passengersArmorStands;
    }

    public void remove() {
        passengersArmorStands.forEach(passengerArmorStand -> passengerArmorStand.remove());

        captainArmorStand.remove();
        rocketArmorStand.remove();
        entities.remove(this);
    }

    public void flyTo(Location to) {
        if (captainArmorStand.getPassenger() == null || !captainArmorStand.getPassenger().getType().equals(EntityType.PLAYER)) return;
        Player captain = (Player) this.getCaptainArmorStand().getPassenger();

        for (ItemStack item : RocketManager.getRocketResources()) {
            if (!captain.getInventory().containsAtLeast(item, item.getAmount())) {
                captain.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "&cНедостаточно ресурсов для полёта!"));
                return;
            }
        }

        RocketManager.getRocketResources().forEach(item -> captain.getInventory().removeItem(item));
        new RocketAnimationThread(captain, to.clone().add(0, 1, 0), this);
    }

    public boolean isCaptain(Player player) {
        if (captainArmorStand.getPassenger() == null || !captainArmorStand.getPassenger().getType().equals(EntityType.PLAYER)) return false;
        Player captain = (Player) captainArmorStand.getPassenger();

        return captain.getName().equals(player.getName());
    }

    public static RocketEntity getRocketEntity(ArmorStand armorStand) {
        for (RocketEntity entity : entities) {
            if (entity.getRocketArmorStand().equals(armorStand) || entity.getCaptainArmorStand().equals(armorStand) || entity.getPassengersArmorStands().contains(armorStand)) return entity;
        }

        return null;
    }

    public static RocketEntity getRocketEntityForCaptain(Player player) {
        for (RocketEntity entity : entities) {
            Player captain = (Player) entity.getCaptainArmorStand().getPassenger();
            if (captain != null && player.getName().equals(captain.getName())) return entity;
        }

        return null;
    }

    public static List<RocketEntity> getEntities() {
        return entities;
    }
}