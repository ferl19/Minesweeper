package com.ferl.model;

import com.ferl.manager.LangManager;

public enum Difficulty {
    EASY("game.settings.difficulty.easy", 10, 10, 15),
    MEDIUM("game.settings.difficulty.medium", 16, 16, 40),
    HARD("game.settings.difficulty.hard", 30, 16, 99),
    CUSTOM("game.settings.difficulty.custom", 0, 0, 0);

    private final String langKey;

    private final int width;
    private final int height;
    private final int mines;

    Difficulty(String langKey, int width, int height, int mines) {
        this.langKey = langKey;
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }

    @Override
    public String toString() {
        return LangManager.getText(langKey);
    }
}
