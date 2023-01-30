package org.millida.dunespiceflight.utils;

public class PrototypeManager {
    public static boolean isInteger(String text) {
        try {
            int num = Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}