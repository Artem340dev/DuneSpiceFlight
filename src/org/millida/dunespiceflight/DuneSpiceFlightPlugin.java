package org.millida.dunespiceflight;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.millida.dunespiceflight.commands.DuneSpiceFlightCommand;
import org.millida.dunespiceflight.configuration.MainConfiguration;
import org.millida.dunespiceflight.configuration.PlayersConfiguration;
import org.millida.dunespiceflight.handlers.InventoryHandler;
import org.millida.dunespiceflight.handlers.WorldHandler;
import org.millida.dunespiceflight.inventory.RocketFlightPanelInventory;
import org.millida.dunespiceflight.utils.EconomyManager;
import org.millida.dunespiceflight.utils.TownyManager;

public class DuneSpiceFlightPlugin extends JavaPlugin {
    public static final String CHAT_PREFIX = "&9&l&oDuneSpiceFlight &8>>> &f";
    public static final String ONBOARD_COMPUTER_PREFIX = "&9&l&oБортовой компьютер &8>>> &f";

    private static DuneSpiceFlightPlugin instance;

    private PlayersConfiguration playersConfiguration;
    private MainConfiguration mainConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        EconomyManager.initialize();
        TownyManager.initialize();

        this.mainConfiguration = new MainConfiguration();
        this.playersConfiguration = new PlayersConfiguration();

        mainConfiguration.onLoad();
        playersConfiguration.onLoad();

        new RocketFlightPanelInventory();
        new DuneSpiceFlightCommand().register();

        Bukkit.getPluginManager().registerEvents(new WorldHandler(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryHandler(), this);
    }

    @Override
    public void onDisable() {
        mainConfiguration.onSave();
        playersConfiguration.onSave();
    }

    public MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public PlayersConfiguration getPlayersConfiguration() {
        return playersConfiguration;
    }

    public static DuneSpiceFlightPlugin getInstance() {
        return instance;
    }
}