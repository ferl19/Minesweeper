package com.ferl.view;

import com.ferl.manager.LangManager;
import com.ferl.manager.SceneManager;
import com.ferl.manager.SettingsManager;
import com.ferl.manager.StyleManager;
import com.ferl.model.AppSettings;
import com.ferl.model.Language;
import com.ferl.model.Theme;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsView {
    private final SceneManager sceneManager;

    public SettingsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        return createSettingsScene();
    }

    private VBox createTopSection() {
        // TITLE
        Label title = new Label(LangManager.getText("settings.label.title"));
        title.setId("title");

        // SUBTITLE
        Label subtitle = new Label(LangManager.getText("settings.label.subtitle"));
        subtitle.setId("subtitle");

        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(
                title,
                subtitle
        );
        topContainer.setSpacing(10.0d);
        topContainer.setAlignment(Pos.CENTER);

        return topContainer;
    }

    private HBox createOptionsSection() {
        // LANGUAGE OPTION
        Label langLabel = new Label(LangManager.getText("settings.label.language"));

        ComboBox<Language> langSelection = new ComboBox<>();
        langSelection.getItems().addAll(Language.values());
        langSelection.setValue(AppSettings.getLang());
        langSelection.setOnAction(_ -> {
            AppSettings.setLang(langSelection.getValue());
            SettingsManager.save();
            sceneManager.showSettings();
        });

        HBox langContainer = new HBox();
        langContainer.getChildren().addAll(
                langLabel,
                langSelection
        );
        langContainer.setSpacing(10.0d);
        langContainer.setAlignment(Pos.CENTER);

        // THEME OPTION
        Label themeLabel = new Label(LangManager.getText("settings.label.theme"));

        ComboBox<Theme> themeSelection = new ComboBox<>();
        themeSelection.getItems().addAll(Theme.values());
        themeSelection.setValue(AppSettings.getTheme());
        themeSelection.setOnAction(_ -> {
            AppSettings.setTheme(themeSelection.getValue());
            SettingsManager.save();
            sceneManager.showSettings();
        });

        HBox themeContainer = new HBox();
        themeContainer.getChildren().addAll(
                themeLabel,
                themeSelection
        );
        themeContainer.setSpacing(10.0d);
        themeContainer.setAlignment(Pos.CENTER);

        HBox optionsContainer = new HBox();
        optionsContainer.getChildren().addAll(
                langContainer,
                themeContainer
        );
        optionsContainer.setSpacing(20.0d);
        optionsContainer.setAlignment(Pos.CENTER);

        return optionsContainer;
    }

    private Button createBackBtn() {
        Button backBtn = new Button(LangManager.getText("settings.button.back"));

        backBtn.setOnAction(_ -> sceneManager.showMenu());

        return backBtn;
    }

    private VBox createSettingsContainer() {
        VBox settingsContainer = new VBox();
        settingsContainer.getChildren().addAll(
                createTopSection(),
                createOptionsSection(),
                createBackBtn()
        );
        settingsContainer.setSpacing(20.0d);
        settingsContainer.setAlignment(Pos.CENTER);

        return settingsContainer;
    }

    private Scene createSettingsScene() {
        Scene scene = new Scene(createSettingsContainer());
        scene.getStylesheets().add(StyleManager.getThemeStyle());
        scene.getStylesheets().add(StyleManager.getBaseStyle());

        return scene;
    }
}
