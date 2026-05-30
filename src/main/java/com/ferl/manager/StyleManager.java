package com.ferl.manager;

import com.ferl.model.AppSettings;
import com.ferl.model.Theme;
import com.ferl.view.CellButton;

import java.util.Objects;

public class StyleManager {
    public static String getBaseStyle() {
        return Objects.requireNonNull(StyleManager.class.getResource("/style/base.css")).toExternalForm();
    }

    public static String getThemeStyle() {
        String themeStyle;

        if (AppSettings.getTheme() == Theme.DARK) {
            themeStyle = "dark";
        } else {
            themeStyle = "light";
        }

        return Objects.requireNonNull(StyleManager.class.getResource("/style/" + themeStyle + ".css")).toExternalForm();
    }

    public static String getImageTheme(String imageType) {
        String imageTheme = (AppSettings.getTheme() == Theme.DARK) ? "dark" : "light";

        return Objects.requireNonNull(
                StyleManager.class.getResource(
                        "/images/" + imageTheme + "_theme_" + imageType + ".png"
                )
        ).toExternalForm();
    }

    public static void setCellButtonStyle(CellButton cell, int row, int col, boolean revealed) {
        String styleEven, styleOdd;

        if (revealed) {
            styleEven = "-fx-background-color: #c4d4d4;";
            styleOdd = "-fx-background-color: #9eaaaa;";
        } else {
            styleEven = "-fx-background-color: #12d7d4;";
            styleOdd = "-fx-background-color: #0faba9;";
        }

        if ((row + col) % 2 == 0) {
            cell.setStyle(styleEven);
        } else {
            cell.setStyle(styleOdd);
        }
    }

    public static void setNumberStyle(CellButton cell, int value) {
        clearNumberStyle(cell);

        if (value >= 1 && value <= 8) {
            cell.getStyleClass().add("number-" + value);
        }
    }

    public static void clearNumberStyle(CellButton cell) {
        for (int i = 0; i <= 8; i++) {
            cell.getStyleClass().remove("number-" + i);
        }
    }
}
