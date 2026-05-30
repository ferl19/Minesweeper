package com.ferl.model;

public class AppSettings {
    private static Language lang = Language.ENGLISH;
    private static Theme theme = Theme.LIGHT;

    public static Language getLang() {
        return lang;
    }

    public static void setLang(Language lang) {
        AppSettings.lang = lang;
    }

    public static Theme getTheme() {
        return theme;
    }

    public static void setTheme(Theme theme) {
        AppSettings.theme = theme;
    }
}
