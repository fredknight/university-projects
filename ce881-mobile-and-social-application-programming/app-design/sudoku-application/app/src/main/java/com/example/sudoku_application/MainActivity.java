/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        user_name = settings.getString("user_name", user_name);
        setTheme(R.style.Theme_CE881Assignment);
        setContentView(R.layout.splash_screen);
        setTitle("Home");
        Objects.requireNonNull(getSupportActionBar()).hide();
        new SplashScreen().execute();
    }

    private void endSplashScreenHandler() {
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).show();
        greeting();
    }

    private class SplashScreen extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (Exception ignored) {
            }
            return null;
        }

        protected void onPostExecute(Void voids) {
            endSplashScreenHandler();
        }

    }

    static String aboutItem = "About";
    static String nameItem = "Change Name";
    static String gameItem = "New Game";
    static String solverItem = "Solver";

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(aboutItem);
        menu.add(gameItem);
        menu.add(solverItem);
        menu.add(nameItem);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(aboutItem)) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getTitle().equals(gameItem)) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getTitle().equals(solverItem)) {
            Intent intent = new Intent(this, SolverActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getTitle().equals(nameItem)) {
            user_name = null;
            new Greeting().execute();
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "tag");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(R.string.quit_app_title);
            alertDialog.setMessage(this.getResources().getString(R.string.quit_app_prompt));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static String user_name = null;
    private static String prefix = null;

    private void greeting() {
        if (user_name == null) {
            new Greeting().execute();
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "tag");
        } else {
            getPrefix();
            String greeting = prefix + ", " + user_name + "!";
            TextView textView = findViewById(R.id.textView);
            textView.setText(greeting);
        }

    }

    private void getPrefix() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            prefix = "Good Morning";
        } else if (hour < 18) {
            prefix = "Good Afternoon";
        } else {
            prefix = "Good Evening";
        }
    }

    private static final Object lock = new Object();

    private class Greeting extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (lock) {
                while (user_name == null) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            getPrefix();
            if (!user_name.matches(".*\\w.*")) {
                user_name = "User";
            } else {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("user_name", user_name);
                editor.apply();
            }
            String greeting = prefix + ", " + user_name + "!";
            TextView textView = findViewById(R.id.textView);
            textView.setText(greeting);
        }

    }

    public static class CustomDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText editText = new EditText(getActivity().getBaseContext());
            editText.setSingleLine();
            builder.setView(editText);
            builder.setMessage(R.string.enter_name);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    synchronized (lock) {
                        user_name = editText.getText().toString();
                        lock.notify();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) { }
            });
            return  builder.create();
        }

    }

}