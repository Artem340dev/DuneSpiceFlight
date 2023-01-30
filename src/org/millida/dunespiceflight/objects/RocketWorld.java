package org.millida.dunespiceflight.objects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;

import java.util.HashMap;

public class RocketWorld {
    private World world;
    private HashMap<Integer, Location> locations;

    public RocketWorld(World world) {
        this(world, new HashMap<>());
    }

    public RocketWorld(World world, HashMap<Integer, Location> locations) {
        this.world = world;
        this.locations = locations;
    }

    public World getWorld() {
        return world;
    }

    public HashMap<Integer, Location> getLocations() {
        return locations;
    }

    public void addLocation(int index, Location location) {
        locations.put(index, location);
    }

    public void addLocation(Location location) {
        for (int i = 1; i <= 3; i++) {
            if (!locations.containsKey(i)) {
                locations.put(i, location);
                return;
            }
        }
    }

    public void removeLocation(int index) {
        locations.remove(index);
    }

    public boolean locationExists(int index) {
        return locations.containsKey(index);
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }
}