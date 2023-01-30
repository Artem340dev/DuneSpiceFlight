package org.millida.dunespiceflight.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class EconomyManager {
    private static Economy economy;

    public static void initialize() {
        economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
    }

    public static void withdrawPlayer(OfflinePlayer player, double amount) {
        economy.withdrawPlayer(player, amount);
    }

    public static void depositPlayer(OfflinePlayer player, double amount) {
        economy.depositPlayer(player, amount);
    }

    public static double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }
}