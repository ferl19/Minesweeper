package com.ferl.controller;

import com.ferl.generator.GameGenerator;
import com.ferl.manager.LangManager;
import com.ferl.manager.StyleManager;
import com.ferl.model.GameSettings;
import com.ferl.view.CellButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class GameController {
    private final GameGenerator gameGenerator;
    private int[][] board;
    private CellButton[][] cells;
    private boolean firstClick;
    private boolean gameOver;

    private Label result;

    private Label flagLabel;
    private int flagAmount;

    private int seconds;
    private Label timeLabel;
    private Timeline timer;

    public GameController(GameGenerator gameGenerator) {
        this.gameGenerator = gameGenerator;
        firstClick = true;
        gameOver = false;
    }

    public void setCells(CellButton[][] cells) {
        this.cells = cells;
    }

    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }

    public void setResult(Label result) {
        this.result = result;
    }

    private void setResultText(boolean win) {
        if (win) {
            result.setId("result-win");
            result.setText(LangManager.getText("game.result.win"));
        } else {
            result.setId("result-lose");
            result.setText(LangManager.getText("game.result.lose"));
        }

        result.setVisible(true);
    }

    private void clearResultText() {
        result.setId("result");
        result.setText("");
        result.setVisible(false);
    }

    public void setFlagLabel(Label flagLabel) {
        this.flagLabel = flagLabel;

        flagAmount = GameSettings.getMines();

        flagLabel.setText(String.valueOf(flagAmount));
    }

    public void setTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
        seconds = 0;

        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> {
                    seconds++;
                    this.timeLabel.setText(String.valueOf(seconds));
                })
        );
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private ImageView createMineImage() {
        Image mineImageSrc = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/mine.png")));
        ImageView mineImage = new ImageView(mineImageSrc);
        mineImage.setFitWidth(12);
        mineImage.setFitHeight(12);
        return mineImage;
    }

    private ImageView createFlagImage() {
        Image flagImageSrc = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/flag.png")));
        ImageView flagImage = new ImageView(flagImageSrc);
        flagImage.setFitWidth(12);
        flagImage.setFitHeight(12);
        return flagImage;
    }

    public void flagCell(CellButton cell) {
        if (firstClick || gameOver) return;

        if (cell.isFlagged()) {
            cell.setText("");
            cell.setFlagged(false);
            flagAmount++;
            flagLabel.setText(String.valueOf(flagAmount));
        } else if (!cell.isRevealed() && flagAmount >= 1) {
            cell.setGraphic(createFlagImage());
            cell.setFlagged(true);
            flagAmount--;
            flagLabel.setText(String.valueOf(flagAmount));
        }
    }

    public void revealCell(int col, int row, CellButton cell) {

        if (cell.isRevealed() || cell.isFlagged() || gameOver) {
            return;
        }

        if (firstClick) {
            board = gameGenerator.generate(
                    GameSettings.getWidth(),
                    GameSettings.getHeight(),
                    GameSettings.getMines(),
                    col,
                    row,
                    GameSettings.isSafeSpot()
            );

            timer.playFromStart();
            firstClick = false;
        }

        revealRecursive(col, row);
    }

    private void revealRecursive(int col, int row) {
        if (col < 0 || row < 0 ||
            col >= GameSettings.getWidth() ||
            row >= GameSettings.getHeight()) {
            return;
        }

        CellButton cell = cells[row][col];

        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        cell.setRevealed(true);

        int value = board[row][col];

        if (value == -1) {
            cell.setGraphic(createMineImage());
            timer.stop();
            revealAll();
            setResultText(false);
            return;
        }

        StyleManager.setCellButtonStyle(cell, row, col, true);

        if (value > 0) {
            StyleManager.setNumberStyle(cell, value);
            cell.setText(String.valueOf(value));
            if (winCheck()) setResultText(true);
            return;
        }

        cell.setText("");

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;

                revealRecursive(col + x, row + y);
            }
        }
    }

    private void revealAll() {
        for (int row = 0; row < GameSettings.getHeight(); row++) {
            for (int col = 0; col < GameSettings.getWidth(); col++) {
                CellButton cell = cells[row][col];
                int value = board[row][col];

                cell.setRevealed(true);
                cell.setText("");
                cell.setGraphic(null);

                if (value == -1) {
                    cell.setGraphic(createMineImage());
                    continue;
                } else if (value == 0) {
                    cell.setText("");
                } else if (value > 0) {
                    cell.setText(String.valueOf(value));
                }

                StyleManager.setNumberStyle(cell, value);
                StyleManager.setCellButtonStyle(cell, row, col, true);
            }
        }
    }

    public void reset() {
        seconds = 0;
        flagAmount = GameSettings.getMines();
        flagLabel.setText(String.valueOf(flagAmount));

        if (timeLabel != null) {
            timeLabel.setText("0");
        }

        if (timer != null) {
            timer.stop();
        }

        for (int row = 0; row < GameSettings.getHeight(); row++) {
            for (int col = 0; col < GameSettings.getWidth(); col++) {
                CellButton cell = cells[row][col];

                cell.setGraphic(null);
                cell.setText("");
                cell.setRevealed(false);
                cell.setFlagged(false);

                StyleManager.clearNumberStyle(cell);
                StyleManager.setCellButtonStyle(cell, row, col, false);
            }
        }

        board = null;
        firstClick = true;
        gameOver = false;

        clearResultText();
    }

    private boolean winCheck() {
        for (int row = 0; row < GameSettings.getHeight(); row++) {
            for (int col = 0; col < GameSettings.getWidth(); col++) {
                CellButton cell = cells[row][col];
                int value = board[row][col];

                if (cell.isRevealed() && value >= 0 ||
                    !cell.isRevealed() && value == -1) continue;

                return false;
            }
        }

        gameOver = true;
        timer.stop();
        return true;
    }
}
