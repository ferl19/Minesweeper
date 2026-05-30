package com.ferl.model;

public class GameSettings {
    private static int width = GameRules.DEFAULT_WIDTH;
    private static int height = GameRules.DEFAULT_HEIGHT;
    private static int mines = GameRules.calcDefaultMines(width, height);
    private static boolean safeSpot = true;

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        GameSettings.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        GameSettings.height = height;
    }

    public static int getMines() {
        return mines;
    }

    public static void setMines(int mines) {
        GameSettings.mines = mines;
    }

    public static boolean isSafeSpot() {
        return safeSpot;
    }

    public static void setSafeSpot(boolean safeSpot) {
        GameSettings.safeSpot = safeSpot;
    }
}
