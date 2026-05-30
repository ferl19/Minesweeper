package com.ferl.model;

public class GameRules {
    public static final int WIDTH_MIN = 4;
    public static final int WIDTH_MAX = 40;

    public static final int HEIGHT_MIN = 4;
    public static final int HEIGHT_MAX = 30;

    public static final int MINES_MIN = 1;

    public static final int DEFAULT_WIDTH = 12;
    public static final int DEFAULT_HEIGHT = 10;
    private static final double DEFAULT_MINES_RATIO = 0.15;

    public static int calcDefaultMines(int width, int height) {
        return (int) Math.round(width * height * DEFAULT_MINES_RATIO);
    }

    public static int calcMaxMines(int width, int height, boolean safeSpot) {
        int reservedCells = (safeSpot) ? 9 : 1;
        return (width * height) - reservedCells;
    }

    public static int calcRecommendedMinMines(int width, int height) {
        return (int) Math.round(width * height * 0.15);
    }

    public static int calcRecommendedMaxMines(int width, int height) {
        return (int) Math.round(width * height * 0.23);
    }
}
