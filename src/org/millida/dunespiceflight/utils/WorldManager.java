package org.millida.dunespiceflight.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class WorldManager {
    public static void setResistance(Block block, float resistance) {
        net.minecraft.server.v1_16_R3.Block nmsBlock = ((CraftBlock) block).getNMS().getBlock();

        try {
            Field field = net.minecraft.server.v1_16_R3.Block.class.getDeclaredField("strength");
            field.setAccessible(true);
            field.setFloat(nmsBlock, resistance);
        } catch (Exception e) {
        }
    }

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