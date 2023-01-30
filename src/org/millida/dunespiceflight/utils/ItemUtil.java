package org.millida.dunespiceflight.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtil {
    public static ItemStack getItem(String name, List<String> lore, Material material) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtil.parseColor(name));
        meta.setLore(ChatUtil.parseColor(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(String name, List<String> lore, Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtil.parseColor(name));
        meta.setLore(ChatUtil.parseColor(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(String name, List<String> lore, Material material, int amount, int customModelData) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtil.parseColor(name));
        meta.setLore(ChatUtil.parseColor(lore));
        meta.setCustomModelData(customModelData);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(Material material, int amount, int customModelData) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setCustomModelData(customModelData);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        return item;
    }
}