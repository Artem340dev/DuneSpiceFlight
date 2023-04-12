package org.millida.dunespiceflight.configuration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.millida.dunespiceflight.utils.RocketManager;
import org.millida.dunespiceflight.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

public class MainConfiguration extends AbstractConfiguration {
    public MainConfiguration() {
        super("config.yml");
    }

    @Override
    public void onLoad() {
        this.loadConfig();

        List<ItemStack> rocketResources = new ArrayList<>();
        for (String indentify : configuration.getConfigurationSection("rocketResources").getKeys(false)) {
            Material material = Material.valueOf(configuration.getString("rocketResources." + indentify + ".material"));

            int amount = configuration.contains("rocketResources." + indentify + ".amount") ? configuration.getInt("rocketResources." + indentify + ".amount") : 1;
            ItemStack item;

            if (configuration.contains("rocketResources." + indentify + ".customModelData")) {
                int customModelData = configuration.getInt("rocketResources." + indentify + ".customModelData");
                item = ItemUtil.getItem(material, amount, customModelData);
            } else {
                item = ItemUtil.getItem(material, amount);
            }

            rocketResources.add(item);
        }

        int rocketMaxDestinations = configuration.getInt("rocketMaxDestinations");
        int rocketPrice = configuration.getInt("rocketPrice");
        double rocketMaxHealth = configuration.getDouble("rocketMaxHealth");
        boolean canArmorStandVisible = configuration.getBoolean("canArmorStandVisible");

        RocketManager.setCanArmorStandVisible(canArmorStandVisible);
        RocketManager.setRocketMaxDestinations(rocketMaxDestinations);
        RocketManager.setRocketResources(rocketResources);
        RocketManager.setRocketMaxHeatlh(rocketMaxHealth);
        RocketManager.setRocketPrice(rocketPrice);

        String rocketName = configuration.getString("rocketItem.name");
        List<String> rocketLore = configuration.getStringList("rocketItem.lore");
        Material rocketMaterial = Material.valueOf(configuration.getString("rocketItem.material"));
        int rocketCustomModelData = configuration.getInt("rocketItem.customModelData");

        String rocketArmorHeadName = configuration.getString("rocketArmorHeadItem.name");
        List<String> rocketArmorHeadLore = configuration.getStringList("rocketArmorHeadItem.lore");
        Material rocketArmorHeadMaterial = Material.valueOf(configuration.getString("rocketArmorHeadItem.material"));
        int rocketArmorHeadCustomModelData = configuration.getInt("rocketArmorHeadItem.customModelData");

        RocketManager.setRocketItem(ItemUtil.getItem(rocketName, rocketLore, rocketMaterial, 1, rocketCustomModelData));
        RocketManager.setRocketArmorHeadItem(ItemUtil.getItem(rocketArmorHeadName, rocketArmorHeadLore, rocketArmorHeadMaterial, 1, rocketArmorHeadCustomModelData));
    }

    @Override
    public void onSave() {
        this.saveConfig();
    }
}