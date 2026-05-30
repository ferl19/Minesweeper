package com.ferl.model;

import com.ferl.manager.LangManager;

public enum Theme {
    LIGHT("app.settings.theme.light"),
    DARK("app.settings.theme.dark");

    private final String langKey;

    Theme(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public String toString() {
        return LangManager.getText(langKey);
    }
}
