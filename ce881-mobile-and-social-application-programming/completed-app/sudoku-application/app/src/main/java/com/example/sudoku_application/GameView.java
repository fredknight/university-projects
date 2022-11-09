/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private int cellSize;
    private int gridSize;
    private final Rect numberBounds = new Rect();

    private final Game game = new Game();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Game getGame() {
        return game;
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        this.gridSize = Math.min(getMeasuredWidth(), getMeasuredHeight());;
        this.cellSize = gridSize / 9;
        setMeasuredDimension(gridSize, gridSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(16);
        border.setColor(getResources().getColor(R.color.black));
        border.setAntiAlias(true);

        fillCells(canvas, game.getSelColumn(), game.getSelRow());
        canvas.drawRect(0, 0, getWidth(), getHeight(), border);
        drawGrid(canvas);
        numberCells(canvas);
    }

    private void numberCells(Canvas canvas) {
        Paint numbers = new Paint();
        numbers.setStyle(Paint.Style.FILL);
        numbers.setColor(getResources().getColor(R.color.black));
        numbers.setAntiAlias(true);
        numbers.setTextSize((cellSize / 3) * 2);
        int[][] grid = game.getGrid();
        int[][] basic = game.getBasicGrid();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] > 0) {
                    if (grid[r][c] == basic[r][c]) {
                        numbers.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        numbers.setTypeface(Typeface.DEFAULT);
                    }
                    String number = Integer.toString(grid[r][c]);
                    numbers.getTextBounds(number, 0, number.length(), numberBounds);
                    float width = numbers.measureText(number);
                    canvas.drawText(number, (c * cellSize) + ((cellSize - width) / 2),
                            ((r * cellSize) + cellSize) - ((cellSize - (numberBounds.height())) / 2), numbers);
                }
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int selColumn = (int) Math.ceil(event.getX() / cellSize);
            int selRow = (int) Math.ceil(event.getY() / cellSize);
            if (game.getSelColumn() == selColumn && game.getSelRow() == selRow) {
                game.setSelColumn(-1);
                game.setSelRow(-1);
            } else {
                game.setSelColumn(selColumn);
                game.setSelRow(selRow);
            }
            return true;
        }
        return false;
    }

    private void fillCells(Canvas canvas, int column, int row) {
        Paint cell = new Paint();
        cell.setStyle(Paint.Style.FILL);
        cell.setAntiAlias(true);

        if (game.getSelColumn() != -1 && game.getSelRow() != -1) {
            cell.setColor(getResources().getColor(R.color.light_sel));
            canvas.drawRect((column - 1) * cellSize, 0,
                    column * cellSize, gridSize, cell);
            canvas.drawRect(0, (row - 1) * cellSize,
                    gridSize, row * cellSize, cell);

            cell.setColor(getResources().getColor(R.color.sel));
            canvas.drawRect((column - 1) * cellSize, (row - 1) * cellSize,
                    column * cellSize, row * cellSize, cell);
        }
        invalidate();
    }

    private void drawGrid(Canvas canvas) {
        Paint lines = new Paint();
        lines.setStyle(Paint.Style.STROKE);
        lines.setColor(getResources().getColor(R.color.black));

        for (int c = 0; c < 10; c++) {
            if (c % 3 == 0) {
                lines.setStrokeWidth(12);
            } else {
                lines.setStrokeWidth(4);
            }
            canvas.drawLine(cellSize * c, 0,
                    cellSize * c, getWidth(), lines);
        }
        for (int r = 0; r < 10; r++) {
            if (r % 3 == 0) {
                lines.setStrokeWidth(12);
            } else {
                lines.setStrokeWidth(4);
            }
            canvas.drawLine(0, cellSize * r,
                    getWidth(), cellSize * r, lines);
        }
    }
}
