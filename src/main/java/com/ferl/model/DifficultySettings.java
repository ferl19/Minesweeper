package com.ferl.model;

public class DifficultySettings {
    private static Difficulty difficulty = Difficulty.EASY;

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        DifficultySettings.difficulty = difficulty;
    }
}
