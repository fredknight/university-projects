/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

public class Game {

    int selColumn;
    int selRow;

    Game() {
        selColumn = -1;
        selRow = -1;
    }

    public int getSelColumn() {
        return selColumn;
    }

    public int getSelRow() {
        return selRow;
    }

    public void setSelColumn(int column) {
        selColumn = column;
    }

    public void setSelRow(int row) {
        selRow = row;
    }

}
