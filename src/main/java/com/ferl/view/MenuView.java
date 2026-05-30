package com.ferl.view;

import com.ferl.manager.LangManager;
import com.ferl.manager.SceneManager;
import com.ferl.manager.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuView {
    private final SceneManager sceneManager;

    public MenuView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        return createMenuScene();
    }

    private Label createTitle() {
        Label title = new Label(LangManager.getText("menu.label.title"));
        title.setId("title");
        return title;
    }

    private Button createPlayBtn() {
        Button playBtn = new Button(LangManager.getText("menu.button.play"));
        playBtn.setOnAction(_ -> sceneManager.showGameSettings());

        return playBtn;
    }

    private Button createSettingsBtn() {
        Button settingsBtn = new Button(LangManager.getText("menu.button.settings"));
        settingsBtn.setOnAction(_ -> sceneManager.showSettings());

        return settingsBtn;
    }

    private Button createQuitBtn() {
        Button quitBtn = new Button(LangManager.getText("menu.button.quit"));
        quitBtn.setOnAction(_ -> sceneManager.quit());

        return  quitBtn;
    }

    private VBox createMenuContainer() {
        VBox menuContainer = new VBox();
        menuContainer.getChildren().addAll(
                createTitle(),
                createPlayBtn(),
                createSettingsBtn(),
                createQuitBtn()
        );
        menuContainer.setSpacing(20.0d);
        menuContainer.setAlignment(Pos.CENTER);

        return menuContainer;
    }

    private Scene createMenuScene() {
        Scene scene = new Scene(createMenuContainer());
        scene.getStylesheets().add(StyleManager.getThemeStyle());
        scene.getStylesheets().add(StyleManager.getBaseStyle());

        return scene;
    }
}
