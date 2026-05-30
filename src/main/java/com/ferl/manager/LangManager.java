package com.ferl.manager;

import com.ferl.model.AppSettings;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangManager {

    public static String getText(String key) {
        Locale locale = switch (AppSettings.getLang()) {
            case POLISH -> Locale.of("pl");
            case GERMAN -> Locale.of("de");
            case SPANISH -> Locale.of("es");
            default -> Locale.of("en");
        };
        ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.messages", locale);
        return resourceBundle.getString(key);
    }
}
