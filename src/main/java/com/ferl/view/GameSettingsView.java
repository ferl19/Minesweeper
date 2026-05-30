package com.ferl.view;

import com.ferl.manager.LangManager;
import com.ferl.manager.SceneManager;
import com.ferl.manager.StyleManager;
import com.ferl.model.Difficulty;
import com.ferl.model.DifficultySettings;
import com.ferl.model.GameRules;
import com.ferl.model.GameSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameSettingsView {
    private final SceneManager sceneManager;

    private Spinner<Integer> widthSpinner;
    private Spinner<Integer> heightSpinner;
    private Spinner<Integer> minesSpinner;
    private CheckBox safeSpotCheck;
    private Label recommendedLabel;

    public GameSettingsView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene createScene() {
        return createGameSettingsScene();
    }

    private VBox createTopSection() {
        // TITLE
        Label title = new Label(LangManager.getText("game.settings.label.title"));
        title.setId("title");

        // SUBTITLE
        Label subtitle = new Label(LangManager.getText("game.settings.label.subtitle"));
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

    private VBox createOptionsSection() {
        // DIFFICULTY
        Label difficultyLabel = new Label(LangManager.getText("game.settings.label.difficulty"));

        ComboBox<Difficulty> difficultySelection = new ComboBox<>();
        difficultySelection.getItems().addAll(Difficulty.values());
        difficultySelection.setValue(DifficultySettings.getDifficulty());
        difficultySelection.setOnAction(_ -> {
            DifficultySettings.setDifficulty(difficultySelection.getValue());
            updateCustomOptions();
        });

        HBox difficultyContainer = new HBox();
        difficultyContainer.getChildren().addAll(
                difficultyLabel,
                difficultySelection
        );
        difficultyContainer.setSpacing(10.0d);
        difficultyContainer.setAlignment(Pos.CENTER);

        // SIZE
        Label widthLabel = new Label(LangManager.getText("game.settings.label.width"));

        widthSpinner = new Spinner<>(
                GameRules.WIDTH_MIN,
                GameRules.WIDTH_MAX,
                GameSettings.getWidth()
        );
        widthSpinner.setEditable(true);

        HBox widthContainer = new HBox();
        widthContainer.getChildren().addAll(
                widthLabel,
                widthSpinner
        );
        widthContainer.setSpacing(10.0d);
        widthContainer.setAlignment(Pos.CENTER);

        Label heightLabel = new Label(LangManager.getText("game.settings.label.height"));

        heightSpinner = new Spinner<>(
                GameRules.HEIGHT_MIN,
                GameRules.HEIGHT_MAX,
                GameSettings.getHeight()
        );
        heightSpinner.setEditable(true);

        HBox heightContainer = new HBox();
        heightContainer.getChildren().addAll(
                heightLabel,
                heightSpinner
        );
        heightContainer.setSpacing(10.0d);
        heightContainer.setAlignment(Pos.CENTER);

        // SAFE SPOT
        Label safeSpotLabel = new Label(LangManager.getText("game.settings.label.safeSpot"));

        safeSpotCheck = new CheckBox();
        safeSpotCheck.setSelected(GameSettings.isSafeSpot());
        safeSpotCheck.setPadding(new Insets(1));

        HBox safeSpotContainer = new HBox();
        safeSpotContainer.getChildren().addAll(
                safeSpotLabel,
                safeSpotCheck
        );
        safeSpotContainer.setSpacing(10.0d);
        safeSpotContainer.setAlignment(Pos.CENTER);

        // MINES
        Label minesLabel = new Label(LangManager.getText("game.settings.label.mines"));

        minesSpinner = new Spinner<>(
                GameRules.MINES_MIN,
                GameRules.calcMaxMines(widthSpinner.getValue(), heightSpinner.getValue(),safeSpotCheck.isSelected()),
                GameSettings.getMines()
        );
        minesSpinner.setEditable(true);

        HBox minesContainer = new HBox();
        minesContainer.getChildren().addAll(
                minesLabel,
                minesSpinner
        );
        minesContainer.setSpacing(10.0d);
        minesContainer.setAlignment(Pos.CENTER);

        recommendedLabel = new Label();

        widthSpinner.valueProperty().addListener((_, _, _) -> {
            updateMinesSpinner();
            updateRecommendedLabel();
        });

        heightSpinner.valueProperty().addListener((_, _, _) -> {
            updateMinesSpinner();
            updateRecommendedLabel();
        });

        safeSpotCheck.selectedProperty().addListener((_, _, _) -> updateMinesSpinner());

        updateCustomOptions();
        updateRecommendedLabel();

        VBox optionsContainer = new VBox();
        optionsContainer.getChildren().addAll(
                difficultyContainer,
                widthContainer,
                heightContainer,
                minesContainer,
                recommendedLabel,
                safeSpotContainer
        );
        optionsContainer.setSpacing(20.0d);
        optionsContainer.setAlignment(Pos.CENTER);

        return optionsContainer;
    }

    private HBox createBottomSection() {
        // PLAY BUTTON
        Button playBtn = new Button(LangManager.getText("game.settings.button.play"));
        playBtn.setOnAction(_ -> {
            GameSettings.setWidth(widthSpinner.getValue());
            GameSettings.setHeight(heightSpinner.getValue());
            GameSettings.setMines(minesSpinner.getValue());
            GameSettings.setSafeSpot(safeSpotCheck.isSelected());

            sceneManager.showGame();
        });

        // BACK BUTTON
        Button backBtn = new Button(LangManager.getText("game.settings.button.back"));
        backBtn.setOnAction(_ -> {
            GameSettings.setWidth(widthSpinner.getValue());
            GameSettings.setHeight(heightSpinner.getValue());
            GameSettings.setMines(minesSpinner.getValue());
            GameSettings.setSafeSpot(safeSpotCheck.isSelected());

            sceneManager.showMenu();
        });

        HBox bottomContainer = new HBox();
        bottomContainer.getChildren().addAll(
                backBtn,
                playBtn
        );
        bottomContainer.setSpacing(20.0d);
        bottomContainer.setAlignment(Pos.CENTER);

        return bottomContainer;
    }

    private VBox createGameSettingsContainer() {
        VBox settingsContainer = new VBox();
        settingsContainer.getChildren().addAll(
                createTopSection(),
                createOptionsSection(),
                createBottomSection()
        );
        settingsContainer.setSpacing(20.0d);
        settingsContainer.setAlignment(Pos.CENTER);

        return settingsContainer;
    }

    private Scene createGameSettingsScene() {
        Scene scene = new Scene(createGameSettingsContainer());
        scene.getStylesheets().add(StyleManager.getThemeStyle());
        scene.getStylesheets().add(StyleManager.getBaseStyle());

        return scene;
    }

    private void updateMinesSpinner() {
        int currentVal = minesSpinner.getValue();
        int newMax = GameRules.calcMaxMines(
                widthSpinner.getValue(),
                heightSpinner.getValue(),
                safeSpotCheck.isSelected()
        );

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                GameRules.MINES_MIN,
                newMax,
                Math.min(newMax, currentVal)
        );

        minesSpinner.setValueFactory(valueFactory);
    }

    private void updateCustomOptions() {
        Difficulty difficulty = DifficultySettings.getDifficulty();
        boolean custom = (difficulty == Difficulty.CUSTOM);

        widthSpinner.setDisable(!custom);
        heightSpinner.setDisable(!custom);
        minesSpinner.setDisable(!custom);

        recommendedLabel.setManaged(custom);
        recommendedLabel.setVisible(custom);

        if (custom) return;

        widthSpinner.getValueFactory().setValue(difficulty.getWidth());
        heightSpinner.getValueFactory().setValue(difficulty.getHeight());
        minesSpinner.getValueFactory().setValue(difficulty.getMines());
    }

    private void updateRecommendedLabel() {
        recommendedLabel.setText(
                LangManager.getText("game.settings.label.mines.recommended") + " " +
                GameRules.calcRecommendedMinMines(widthSpinner.getValue(), heightSpinner.getValue()) + "–" +
                GameRules.calcRecommendedMaxMines(widthSpinner.getValue(), heightSpinner.getValue())
        );
    }
}
