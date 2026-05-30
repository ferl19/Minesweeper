package com.ferl.manager;

import com.ferl.controller.GameController;
import com.ferl.model.GameSettings;
import com.ferl.view.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private final Stage stage;
    private final GameController gameController;

    public SceneManager(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
        SettingsManager.load();
        showMenu();
    }

    public void showMenu() {
        MenuView menuView = new MenuView(this);
        setScene(menuView.createScene(), false);
    }

    public void showGameSettings() {
        gameController.setFirstClick(true);
        GameSettingsView gameSettingsView = new GameSettingsView(this);
        setScene(gameSettingsView.createScene(), false);
    }

    public void showGame() {
        GameView gameView = new GameView(this, gameController);
        setScene(gameView.createScene(), true);
    }

    public void showSettings() {
        SettingsView settingsView = new SettingsView(this);
        setScene(settingsView.createScene(), false);
    }

    public void quit() {
        Platform.exit();
    }

    private void setScene(Scene scene, boolean autoSize) {
        stage.setScene(scene);
        setWindowSize(autoSize);
    }

    private void setWindowSize(boolean autoSize) {
        final int minWindowWidth = 442;
        final int widthAddition = 250;
        int width = Math.max(GameSettings.getWidth() * CellButton.WIDTH + widthAddition, minWindowWidth);

        final int minWindowHeight = 300;
        final int heightAddition = 90;
        int height = Math.max(GameSettings.getHeight() * CellButton.HEIGHT + heightAddition, minWindowHeight);

        stage.setMinWidth((autoSize) ? width : 500);
        stage.setMinHeight((autoSize) ? height : 500);

        if (stage.isMaximized()) {
            return;
        }

        if (autoSize) {
            stage.setWidth(width);
            stage.setHeight(height);
        } else {
            stage.setWidth(500);
            stage.setHeight(500);
        }

        if (height <= 840) {
            stage.centerOnScreen();
        }
    }
}
