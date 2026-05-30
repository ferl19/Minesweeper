package com.ferl.view;

import com.ferl.controller.GameController;
import com.ferl.manager.LangManager;
import com.ferl.manager.SceneManager;
import com.ferl.manager.StyleManager;
import com.ferl.model.DifficultySettings;
import com.ferl.model.GameSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class GameView {
    private final SceneManager sceneManager;
    private final GameController gameController;

    public GameView(SceneManager sceneManager, GameController gameController) {
        this.sceneManager = sceneManager;
        this.gameController = gameController;
    }

    public Scene createScene() {
        return createGameScene();
    }

    private VBox createSideSection() {
        Label title = new Label(LangManager.getText("game.label.title"));
        title.setId("title");

        Label result = new Label("You Won");
        result.setId("result");
        result.setVisible(false);
        gameController.setResult(result);

        Label difficultyLabel = new Label(LangManager.getText("game.label.difficulty.level"));

        Label difficultyLevelLabel = new Label(DifficultySettings.getDifficulty().toString());

        VBox difficultyContainer = new VBox();
        difficultyContainer.getChildren().addAll(
                difficultyLabel,
                difficultyLevelLabel
        );
        difficultyContainer.setAlignment(Pos.CENTER);

        Image flagImageSrc = new Image(
                StyleManager.getImageTheme("flag")
        );

        ImageView flagImage = new ImageView(flagImageSrc);
        flagImage.setFitWidth(35);
        flagImage.setFitHeight(35);

        Label flagLabel = new Label("10");
        flagLabel.setId("flag-label");
        gameController.setFlagLabel(flagLabel);

        HBox flagContainer = new HBox();
        flagContainer.getChildren().addAll(
                flagImage,
                flagLabel
        );
        flagContainer.setSpacing(5.0d);
        flagContainer.setAlignment(Pos.CENTER);

        Image timeImageSrc = new Image(
                StyleManager.getImageTheme("clock")
        );

        ImageView timeImage = new ImageView(timeImageSrc);
        timeImage.setFitHeight(35);
        timeImage.setFitWidth(35);

        Label timeLabel = new Label("0");
        timeLabel.setId("time-label");
        gameController.setTimeLabel(timeLabel);

        HBox timeContainer = new HBox();
        timeContainer.getChildren().addAll(
                timeImage,
                timeLabel
        );
        timeContainer.setSpacing(5.0d);
        timeContainer.setAlignment(Pos.CENTER);

        HBox infoContainer = new HBox();
        infoContainer.getChildren().addAll(
                flagContainer,
                timeContainer
        );
        infoContainer.setSpacing(20.0d);
        infoContainer.setAlignment(Pos.CENTER);

        Button backBtn = new Button(LangManager.getText("game.button.back"));
        backBtn.setOnAction(_ -> {
            gameController.reset();
            sceneManager.showGameSettings();
        });

        Button resetBtn = new Button(LangManager.getText("game.button.reset"));
        resetBtn.setOnAction(_ -> gameController.reset());

        HBox bottomContainer = new HBox();
        bottomContainer.getChildren().addAll(
                backBtn,
                resetBtn
        );
        bottomContainer.setSpacing(20.0d);
        bottomContainer.setAlignment(Pos.CENTER);

        VBox sideContainer = new VBox();
        sideContainer.getChildren().addAll(
                title,
                result,
                difficultyContainer,
                infoContainer,
                bottomContainer
        );
        sideContainer.setSpacing(20.0d);
        sideContainer.setAlignment(Pos.CENTER);

        return sideContainer;
    }

    private GridPane createBoard() {
        GridPane board = new GridPane();
        board.setId("board");

        int width = GameSettings.getWidth();
        int height = GameSettings.getHeight();
        CellButton[][] cells = new CellButton[height][width];

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                CellButton cell = getCellButton(col, row);
                cell.getStyleClass().add("cell-btn");

                cells[row][col] = cell;

                board.add(cell, col, row);
            }
        }

        gameController.setCells(cells);

        board.setAlignment(Pos.CENTER);

        return board;
    }

    private CellButton getCellButton(int col, int row) {
        CellButton cell = new CellButton(col, row);
        cell.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                gameController.revealCell(cell.col, cell.row, cell);
            }

            if (e.getButton() == MouseButton.SECONDARY) {
                gameController.flagCell(cell);
            }
        });

        StyleManager.setCellButtonStyle(cell, row, col, false);

        return cell;
    }

    private HBox createGameContainer() {
        HBox gameContainer = new HBox();
        gameContainer.getChildren().addAll(
                createSideSection(),
                createBoard()
        );
        gameContainer.setSpacing(20.0d);
        gameContainer.setPadding(new Insets(20));
        gameContainer.setAlignment(Pos.CENTER);
        return gameContainer;
    }

    private Scene createGameScene() {
        Scene scene = new Scene(createGameContainer());
        scene.getStylesheets().add(StyleManager.getThemeStyle());
        scene.getStylesheets().add(StyleManager.getBaseStyle());

        return scene;
    }
}
