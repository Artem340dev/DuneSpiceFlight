package org.millida.dunespiceflight.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldManager {
    public static Location getRandomLocationAtWorldForPlayer(Player player, World world, int min, int max) {
        Location loc;

        while (true) {
            loc = getHighestBlockAt(world, MathManager.random(min, max), MathManager.random(min, max));

            if (!loc.getBlock().isEmpty() && !loc.getBlock().isLiquid()) {
                if (TownyManager.isWilderness(loc)) {
                    return loc;
                } else {
                    if (TownyManager.isResident(player, player.getLocation())) {
                        return loc;
                    }
                }
            }
        }
    }

    private static Location getHighestBlockAt(World world, int x, int z) {
        int y = 255;

        while (y > 0) {
            Location loc = new Location(world, x, y, z);
            if (!loc.getBlock().isEmpty()) return loc;

            y--;
        }

        return new Location(world, x, 1, z);
    }
}