/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class GameActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static int streak;
    private static int last_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        streak = settings.getInt("streak", streak);
        last_day = settings.getInt("prev_day", last_day);
        setContentView(R.layout.activity_game);
        getStreak();
        displayStreak();
    }

    private void displayStreak() {
        String streakString;
        if (streak < 2) {
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

        if (day > last_day) {
            if (day == (last_day + 1)) {
                streak++;
            } else {
                streak = 1;
            }
            last_day = day;
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("streak", streak);
            editor.putInt("prev_day", last_day);
            editor.apply();
        }
    }

}