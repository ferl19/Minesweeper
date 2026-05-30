package com.ferl;

import com.ferl.controller.GameController;
import com.ferl.generator.GameGenerator;
import com.ferl.manager.SceneManager;
import com.ferl.manager.SettingsManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameGenerator gameGenerator = new GameGenerator();
        GameController gameController = new GameController(gameGenerator);
        SceneManager sceneManager = new SceneManager(stage, gameController);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}