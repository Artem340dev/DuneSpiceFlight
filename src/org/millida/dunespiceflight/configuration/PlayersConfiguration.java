package org.millida.dunespiceflight.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.millida.dunespiceflight.objects.RocketPlayerData;
import org.millida.dunespiceflight.objects.RocketWorld;

public class PlayersConfiguration extends AbstractConfiguration {
    public PlayersConfiguration() {
        super("players.yml");
    }

    @Override
    public void onLoad() {
        this.loadConfig();

        for (String playerName : configuration.getConfigurationSection("players").getKeys(false)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            RocketPlayerData rocket = new RocketPlayerData(player);

            for (String worldName : configuration.getConfigurationSection("players." + playerName).getKeys(false)) {
                World world = Bukkit.getWorld(worldName);
                RocketWorld rocketWorld = new RocketWorld(world);

                for (String locationId : configuration.getConfigurationSection("players." + playerName + "." + worldName).getKeys(false)) {
                    int x = configuration.getInt("players." + playerName + "." + worldName + "." + locationId + ".x");
                    int y = configuration.getInt("players." + playerName + "." + worldName + "." + locationId + ".y");
                    int z = configuration.getInt("players." + playerName + "." + worldName + "." + locationId + ".z");

                    System.out.println("SAVE MY LOVE TONIGHT: " + world);

                    rocketWorld.addLocation(Integer.parseInt(locationId), new Location(world, x, y, z));
                }

                rocket.addWorld(rocketWorld);
            }
        }
    }

    @Override
    public void onSave() {
        RocketPlayerData.getPlayerData().forEach((player, rocket) -> {
            rocket.getWorlds().forEach((worldName, world) -> {
                this.set("players." + player.getName(), null);

                world.getLocations().forEach((index, location) -> {
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();

                    this.set("players." + player.getName() + "." + worldName + "." + index + ".x", x);
                    this.set("players." + player.getName() + "." + worldName + "." + index + ".y", y);
                    this.set("players." + player.getName() + "." + worldName + "." + index + ".z", z);
                });
            });
        });
    }
}