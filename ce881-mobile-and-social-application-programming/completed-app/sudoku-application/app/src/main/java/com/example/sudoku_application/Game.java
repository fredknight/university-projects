/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import java.util.Arrays;

public class Game {
    private int[][] basic;
    private int[][] grid;
    private int[][] solution;
    private int selColumn;
    private int selRow;

    Game() {
        solution = new int[9][9];
        basic = new int[9][9];
        grid = new int[9][9];
        selColumn = 0;
        selRow = 0;
    }

    public void printGrid() {
        for(int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(grid[i]));
        }
    }

    public int getSelColumn() {
        return selColumn;
    }

    public int getSelRow() {
        return selRow;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] getBasicGrid() {
        return basic;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void loadGrid(int[][] grid) {
        this.grid = grid;
    }

    public void loadBasicGrid(int[][] grid) { basic = grid; }

    public void loadSolution(int[][] grid) {
        solution = grid;
    }

    public void setSelColumn(int column) {
        selColumn = column;
    }

    public void setSelRow(int row) {
        selRow = row;
    }

    public void setGrid(int number) {
        if (selRow > 0 && selColumn > 0){
            if (basic[selRow - 1][selColumn - 1] == 0) {
                grid[selRow - 1][selColumn - 1] = number;
            }
        }
    }

    public void resetGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.grid[i][j] = this.basic[i][j];
            }
        }
    }

    public boolean checkGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.grid[i][j] != this.solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRowSafe(int r, int number) {
        for (int c = 0; c < 9; c++) {
            if (solution[r][c] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumnSafe(int c, int number) {
        for (int r = 0; r < 9; r++) {
            if (solution[r][c] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubBoxSafe(int r, int c, int number) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (solution[r + i][c + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkNumberSafe(int i, int j, int num) {
        return checkRowSafe(i, num) && checkColumnSafe(j, num) && checkSubBoxSafe(i - (i % 3), j - (j % 3), num);
    }

    private int randomNumber() {
        return (int) Math.floor((Math.random() * 9) + 1);
    }

    private void generateGrid() {
        for (int i = 0; i < 9; i += 3) {
            int number = randomNumber();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    while (!checkNumberSafe(i, i, number)) {
                        number = randomNumber();
                    }
                    solution[i + r][i + c] = number;
                }
            }
        }
        fill();
    }

    private boolean fill() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solution[r][c] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (checkNumberSafe(r, c, number)) {
                            solution[r][c] = number;
                            if (fill()) {
                                return true;
                            }
                            solution[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void newGame(double difficulty) {
        generateGrid();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                basic[r][c] = solution[r][c];
            }
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (Math.random() < difficulty) {
                    basic[r][c] = 0;
                }
            }
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                grid[r][c] = basic[r][c];
            }
        }
    }
}
