/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

public class Solver {
    private int[][] grid;
    private int selColumn;
    private int selRow;

    Solver() {
        grid = new int[9][9];
        selColumn = -1;
        selRow = -1;
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

    public void setSelColumn(int column) {
        selColumn = column;
    }

    public void setSelRow(int row) {
        selRow = row;
    }

    public void setGrid(int number) {
        if (selRow > 0 && selColumn > 0) {
            grid[selRow - 1][selColumn - 1] = number;
        }
    }

    public void copyGrid(int[][] old) {
        grid = old;
    }

    public void resetGrid() {
        grid = new int[9][9];
    }

    private boolean checkRowSafe(int r, int number) {
        for (int c = 0; c < 9; c++) {
            if (grid[r][c] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumnSafe(int c, int number) {
        for (int r = 0; r < 9; r++) {
            if (grid[r][c] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubBoxSafe(int r, int c, int number) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[r + i][c + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkNumberSafe(int i, int j, int num) {
        return checkRowSafe(i, num) && checkColumnSafe(j, num) && checkSubBoxSafe(i - (i % 3), j - (j % 3), num);
    }

    public void solveGrid() {
        solve();
    }

    private boolean solve() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (checkNumberSafe(r, c, number)) {
                            grid[r][c] = number;
                            if (solve()) {
                                return true;
                            }
                            grid[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkEntered() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] > 0) {
                    if (!checkNumberEntered(r, c, grid[r][c])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRowEntered(int r, int number) {
        int count = 0;
        for (int c = 0; c < 9; c++) {
            if (grid[r][c] == number) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumnEntered(int c, int number) {
        int count = 0;
        for (int r = 0; r < 9; r++) {
            if (grid[r][c] == number) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubBoxEntered(int r, int c, int number) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[r + i][c + j] == number) {
                    count++;
                }
                if (count > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkNumberEntered(int i, int j, int num) {
        return checkRowEntered(i, num) && checkColumnEntered(j, num) && checkSubBoxEntered(i - (i % 3), j - (j % 3), num);
    }

}
