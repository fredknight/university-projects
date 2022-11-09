/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

public class NewGameActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static int last_day;
    private static int streak;
    private Game game;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        streak = settings.getInt("streak", streak);
        last_day = settings.getInt("prev_day", last_day);
        setContentView(R.layout.activity_game);
        getStreak();
        displayStreak();
        gameView = findViewById(R.id.GameView);
        game = gameView.getGame();
        selectDifficulty();
        saveGrids();
    }

    private void selectDifficulty() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getResources().getString(R.string.select_difficulty));
        final NumberPicker picker = new NumberPicker(getBaseContext());
        picker.setMaxValue(3);
        picker.setMinValue(0);
        picker.setValue(1);
        alertDialog.setView(picker);
        picker.setDisplayedValues(new String[]{"Easy", "Medium", "Hard", "Very Hard"});
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int difficulty = picker.getValue();
                if (difficulty == 0) {
                    game.newGame(0.25d);
                    saveGrids();
                } else if (difficulty == 1) {
                    game.newGame(0.35d);
                    saveGrids();
                } else if (difficulty == 2) {
                    game.newGame(0.45d);
                    saveGrids();
                } else if (difficulty == 3) {
                    game.newGame(0.55d);
                    saveGrids();
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        alertDialog.show();
    }

    private void displayStreak() {
        String streakString;
        if (streak == 1) {
            streakString = "Daily Game Streak: " + streak + " Day";
        } else {
            streakString = "Daily Game Streak: " + streak + " Days";
        }
        TextView textView = findViewById(R.id.textView5);
        textView.setText(streakString);
    }

    private void getStreak() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        int i = last_day;
        if (day > i && day != i + 1) {
            streak = 0;
            last_day = day;
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
            editor.putInt("streak", streak);
            editor.putInt("prev_day", last_day);
            editor.apply();
        }
    }

    public void buttonOne(View view) {
        game.setGrid(1);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonTwo(View view) {
        game.setGrid(2);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonThree(View view) {
        game.setGrid(3);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonFour(View view) {
        game.setGrid(4);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonFive(View view) {
        game.setGrid(5);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonSix(View view) {
        game.setGrid(6);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonSeven(View view) {
        game.setGrid(7);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonEight(View view) {
        game.setGrid(8);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonNine(View view) {
        game.setGrid(9);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonClear(View view) {
        game.setGrid(0);
        gameView.invalidate();
        saveGrids();
    }

    public void buttonReset(View view) {
        game.resetGrid();
        gameView.invalidate();
        saveGrids();
    }

    public void buttonCheck(View view) {
        if (game.checkGrid()) {
            updateStreak();
            displayStreak();
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage(getResources().getString(R.string.solution_correct));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "New Puzzle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage(getResources().getString(R.string.solution_incorrect));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    }

    private void updateStreak() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        if (streak == 0) {
            streak++;
            last_day = day;
        } else if (day > last_day) {
            if (day == last_day + 1) {
                streak++;
            } else {
                streak = 0;
            }
            last_day = day;
        }

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putInt("streak", streak);
        editor.putInt("prev_day", last_day);
        editor.apply();
    }

    private void saveGrids() {
        int[][] grid = game.getGrid();
        int[][] basic = game.getBasicGrid();
        int[][] solution = game.getSolution();
        String gridString = "";
        String basicString = "";
        String solutionString = "";
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                gridString = gridString + grid[r][c];
                basicString = basicString + basic[r][c];
                solutionString = solutionString + solution[r][c];
            }
        }
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString("grid", gridString);
        editor.putString("basic", basicString);
        editor.putString("solution", solutionString);
        editor.apply();
    }

    private void loadGrids() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String gridString = settings.getString("grid", "");
        String basicString = settings.getString("basic", "");
        String solutionString = settings.getString("solution", "");
        int[][] grid = new int[9][9];
        int[][] basic = new int[9][9];
        int[][] solution = new int[9][9];
        int counter = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = gridString.charAt(counter) - '0';
                basic[row][col] = basicString.charAt(counter) - '0';
                solution[row][col] = solutionString.charAt(counter) - '0';
                counter++;
            }
        }
        game.loadGrid(grid);
        game.loadBasicGrid(basic);
        game.loadSolution(solution);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_game);
            gameView = findViewById(R.id.GameView);
            game = gameView.getGame();
            loadGrids();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_game);
            gameView = findViewById(R.id.GameView);
            game = gameView.getGame();
            loadGrids();
        }
    }
}
