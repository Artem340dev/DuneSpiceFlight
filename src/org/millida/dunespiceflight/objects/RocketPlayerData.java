package org.millida.dunespiceflight.objects;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class RocketPlayerData {
    private static HashMap<OfflinePlayer, RocketPlayerData> data = new HashMap<>();

    private OfflinePlayer owner;
    private HashMap<String, RocketWorld> worlds;

    public RocketPlayerData(OfflinePlayer owner) {
        this(owner, new HashMap<>());
    }

    public RocketPlayerData(OfflinePlayer owner, HashMap<String, RocketWorld> worlds) {
        this.owner = owner;
        this.worlds = worlds;

        data.put(owner, this);
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public HashMap<String, RocketWorld> getWorlds() {
        return worlds;
    }

    public void addWorld(RocketWorld world) {
        worlds.put(world.getWorld().getName(), world);
    }

    public void removeWorld(String name) {
        worlds.remove(name);
    }

    public boolean worldExists(String name) {
        return worlds.containsKey(name);
    }

    public RocketWorld getWorld(String name) {
        return worlds.get(name);
    }

    public static RocketPlayerData getRocketDataOrNull(OfflinePlayer player) {
        return data.get(player);
    }

    public static HashMap<OfflinePlayer, RocketPlayerData> getPlayerData() {
        return data;
    }
}