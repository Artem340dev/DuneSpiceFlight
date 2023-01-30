package org.millida.dunespiceflight.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ChatUtil {
    public static String parseColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> parseColor(List<String> list) {
        list.replaceAll(ChatUtil::parseColor);
        return list;
    }
}