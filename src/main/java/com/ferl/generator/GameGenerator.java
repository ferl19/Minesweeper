package com.ferl.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameGenerator {
    public int[][] generate(int width, int height, int mines, int safeX, int safeY, boolean safeSpot) {
        int[][] board = new int[height][width];
        List<int[]> cells = new ArrayList<>();

        // Filling list with coordinates except for the safe spot
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if (Math.abs(x - safeX) <= 1 &&
                    Math.abs(y - safeY) <= 1 && safeSpot) {
                    continue;
                }

                cells.add(new int[]{x, y});
            }
        }

        // Shuffling the coordinates
        Collections.shuffle(cells);

        // Give mines to the n first random coordinates
        for (int i = 0; i < mines; i++) {
            int[] pos = cells.get(i);
            int x = pos[0];
            int y = pos[1];

            board[y][x] = -1;
        }

        // Give a number to the cells that have mines as neighbors
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[y][x] == -1) continue;
                // Left check
                if (x > 0) {
                    if (board[y][x - 1] == -1) { board[y][x]++; }
                }
                // Diagonally left up
                if (x > 0 && y > 0) {
                    if (board[y - 1][x - 1] == -1) { board[y][x]++; }
                }
                // Up check
                if (y > 0) {
                    if (board[y - 1][x] == -1) { board[y][x]++; }
                }
                // Diagonally right up
                if (x < width - 1 && y > 0) {
                    if (board[y - 1][x + 1] == -1) { board[y][x]++;}
                }
                // Right check
                if (x < width - 1) {
                    if (board[y][x + 1] == -1) { board[y][x]++; }
                }
                // Diagonally right down
                if (x < width - 1 && y < height - 1) {
                    if (board[y + 1][x + 1] == -1) { board[y][x]++; }
                }
                // Down check
                if (y < height - 1) {
                    if (board[y + 1][x] == -1) { board[y][x]++; }
                }
                // Diagonally left down
                if (x > 0 && y < height - 1) {
                    if (board[y + 1][x - 1] == -1) { board[y][x]++; }
                }
            }
        }

        return board;
    }
}
