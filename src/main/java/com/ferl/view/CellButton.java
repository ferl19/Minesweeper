package com.ferl.view;

import javafx.scene.control.Button;

public class CellButton extends Button {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    public final int col;
    public final int row;

    private boolean flagged;
    private boolean revealed;

    public CellButton(int col, int row) {
        this.col = col;
        this.row = row;
        flagged = false;
        revealed = false;

        setPrefSize(WIDTH, HEIGHT);
    }

    public void setFlagged(boolean b) {
        flagged = b;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setRevealed(boolean b) {
        revealed = b;
    }

    public boolean isRevealed() {
        return revealed;
    }
}
