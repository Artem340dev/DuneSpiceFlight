package org.millida.dunespiceflight.utils;

public class MathManager {
    public static int random(int min, int max) {
        int range = max - min;
        return (int) (Math.random() * range) + min;
    }
}