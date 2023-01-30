package org.millida.dunespiceflight.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TownyManager {
    private static TownyAPI towny;

    public static void initialize() {
        towny = TownyAPI.getInstance();
    }

    public static boolean isWilderness(Location at) {
        return towny.isWilderness(at);
    }

    public static boolean isResident(Player player, Location at) {
        return towny.getTown(at).hasResident(player);
    }
}