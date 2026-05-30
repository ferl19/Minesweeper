package com.ferl.manager;

import com.ferl.model.AppSettings;
import com.ferl.model.Language;
import com.ferl.model.Theme;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class SettingsManager {

    private static final Path CONFIG_PATH = Paths.get(
            System.getProperty("user.home"), ".ferl", "minesweeper", "settings.properties"
    );

    private static final String KEY_LANG  = "lang";
    private static final String KEY_THEME = "theme";

    public static void save() {
        Properties props = new Properties();
        props.setProperty(KEY_LANG,  AppSettings.getLang().name());
        props.setProperty(KEY_THEME, AppSettings.getTheme().name());

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (OutputStream out = new FileOutputStream(CONFIG_PATH.toFile())) {
                props.store(out, "Minesweeper settings");
            }
        } catch (IOException e) {
            System.err.println("Failed to save settings: " + e.getMessage());
        }
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        Properties props = new Properties();
        try (InputStream in = new FileInputStream(CONFIG_PATH.toFile())) {
            props.load(in);

            String langValue  = props.getProperty(KEY_LANG);
            String themeValue = props.getProperty(KEY_THEME);

            if (langValue  != null) AppSettings.setLang(Language.valueOf(langValue));
            if (themeValue != null) AppSettings.setTheme(Theme.valueOf(themeValue));

        } catch (IOException e) {
            System.err.println("Failed to load settings: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Corrupted settings file, using defaults: " + e.getMessage());
        }
    }
}