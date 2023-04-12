package org.millida.dunespiceflight.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.millida.dunespiceflight.objects.RocketPlayerData;
import org.millida.dunespiceflight.objects.RocketEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RocketManager {
    private static int rocketPrice, rocketMaxDestinations;
    private static boolean canArmorStandVisible;
    private static double rocketMaxHeatlh;
    private static ItemStack rocketItem, rocketArmorHeadItem;
    private static List<ItemStack> rocketResources;

    public static List<ItemStack> getRocketResources() {
        return rocketResources;
    }

    public static void setRocketResources(List<ItemStack> rocketResources) {
        RocketManager.rocketResources = rocketResources;
    }

    public static ItemStack getRocketItem() {
        return rocketItem;
    }

    public static void setRocketItem(ItemStack rocketItem) {
        RocketManager.rocketItem = rocketItem;
    }

    public static ItemStack getRocketArmorHeadItem() {
        return rocketArmorHeadItem;
    }

    public static void setRocketArmorHeadItem(ItemStack rocketArmorHeadItem) {
        RocketManager.rocketArmorHeadItem = rocketArmorHeadItem;
    }

    public static int getRocketPrice() {
        return rocketPrice;
    }

    public static int getRocketMaxDestinations() {
        return rocketMaxDestinations;
    }

    public static void setRocketMaxDestinations(int rocketMaxDestinations) {
        RocketManager.rocketMaxDestinations = rocketMaxDestinations;
    }

    public static boolean canArmorStandVisible() {
        return canArmorStandVisible;
    }

    public static void setCanArmorStandVisible(boolean canArmorStandVisible) {
        RocketManager.canArmorStandVisible = canArmorStandVisible;
    }

    public static void setRocketPrice(int rocketPrice) {
        RocketManager.rocketPrice = rocketPrice;
    }

    public static double getRocketMaxHeatlh() {
        return rocketMaxHeatlh;
    }

    public static void setRocketMaxHeatlh(double rocketMaxHeatlh) {
        RocketManager.rocketMaxHeatlh = rocketMaxHeatlh;
    }

    public static RocketEntity spawnRocket(Location at) {
        ArmorStand rocketArmorStand = EntityUtil.spawnArmorStand(at, canArmorStandVisible, false, false, false, false, rocketMaxHeatlh);
        rocketArmorStand.setHelmet(rocketArmorHeadItem);
        EntityUtil.setMetadata(rocketArmorStand, "dunespiceflightrocket", true);

        ArmorStand captainArmorStand = EntityUtil.spawnArmorStand(at.clone().add(0, 0, 1), canArmorStandVisible, true, false, false, false, rocketMaxHeatlh);
        ArmorStand passengerArmorStand1 = EntityUtil.spawnArmorStand(at.clone().add(-1.5, 0, -0.75), canArmorStandVisible, true, false, false, false, rocketMaxHeatlh);
        ArmorStand passengerArmorStand2 = EntityUtil.spawnArmorStand(at.clone().add(-1.5, 0, -1.5), canArmorStandVisible, true, false, false, false, rocketMaxHeatlh);

        List<ArmorStand> passengers = Arrays.asList(passengerArmorStand1, passengerArmorStand1, passengerArmorStand2);
        RocketEntity rocketEntity = new RocketEntity(rocketArmorStand, captainArmorStand, passengers);

        return rocketEntity;
    }
}