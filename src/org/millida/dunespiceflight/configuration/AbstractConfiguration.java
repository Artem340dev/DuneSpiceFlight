package org.millida.dunespiceflight.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class AbstractConfiguration {
    private File file;
    protected FileConfiguration configuration;

    public AbstractConfiguration(String name) {
        this.file = new File(DuneSpiceFlightPlugin.getInstance().getDataFolder() + File.separator + name);
    }

    protected void loadConfig() {
        if (!file.exists()) {
            DuneSpiceFlightPlugin.getInstance().saveResource(file.getName(), false);
        }

        Reader configStream = new InputStreamReader(DuneSpiceFlightPlugin.getInstance().getResource(file.getName()));
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(configStream);

        this.configuration = YamlConfiguration.loadConfiguration(file);
        configuration.setDefaults(defaultConfig);
    }

    protected void saveConfig() {
        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public int getInt(String path) {
        return configuration.getInt(path);
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
        this.saveConfig();
    }

    public void createSection(String path) {
        configuration.createSection(path);
        this.saveConfig();
    }

    public boolean contains(String path) {
        return configuration.contains(path);
    }

    public abstract void onLoad();
    public abstract void onSave();
}