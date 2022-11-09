/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static boolean changeName = false;
    private static String prefix = null;
    private static String user_name = null;

    static String newGameItem = "New Game";
    static String solverItem = "Solver";
    static String aboutItem = "About";
    static String nameItem = "Set Name";

    private static final Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        user_name = settings.getString("user_name", user_name);
        setTheme(R.style.Theme_CE881Assignment);
        setContentView(R.layout.splash_screen);
        setTitle("Main Menu");
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
                return null;
            } catch (Exception ignored) {
            }
            return null;
        }

        protected void onPostExecute(Void voids) {
            endSplashScreenHandler();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(aboutItem);
        menu.add(nameItem);
        return true;
    }

    public void buttonContinue(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void buttonNew(View view) {
        warningMessage(new Intent(this, NewGameActivity.class));
    }

    public void buttonSolver(View view) {
        startActivity(new Intent(this, SolverActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String gameItem = "Continue Game";
        if (item.getTitle().equals(newGameItem)) {
            warningMessage(new Intent(this, NewGameActivity.class));
            return true;
        } else if (item.getTitle().equals(gameItem)) {
            startActivity(new Intent(this, GameActivity.class));
            return true;
        } else if (item.getTitle().equals(solverItem)) {
            startActivity(new Intent(this, SolverActivity.class));
            return true;
        } else if (item.getTitle().equals(aboutItem)) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (item.getTitle().equals(nameItem)) {
            user_name = null;
            changeName = true;
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
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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
                editor.putString("user_name", MainActivity.user_name);
                editor.apply();
            }
            String greeting = prefix + ", " + user_name + "!";
            TextView textView = findViewById(R.id.textView);
            textView.setText(greeting);
        }
    }

    public static class CustomDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText editText = new EditText(getActivity().getBaseContext());
            editText.setSingleLine();
            builder.setView(editText);
            if (!MainActivity.changeName) {
                builder.setTitle(R.string.first_greetings);
            }
            builder.setMessage(R.string.enter_name);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    synchronized (lock) {
                        user_name = editText.getText().toString();
                        changeName = false;
                        lock.notify();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    if (!changeName) {
                        synchronized (lock) {
                            user_name = "User";
                            changeName = false;
                            lock.notify();
                        }
                    }
                }
            });
            return builder.create();
        }
    }

    private void warningMessage(Intent newGame) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.new_game));
        alertDialog.setMessage(getResources().getString(R.string.new_game_prompt));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.startActivity(newGame);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
            getPrefix();
            String greeting = prefix + ", " + user_name + "!";
            TextView textView = findViewById(R.id.textView);
            textView.setText(greeting);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            getPrefix();
            String greeting = prefix + ", " + user_name + "!";
            TextView textView = findViewById(R.id.textView);
            textView.setText(greeting);
        }
    }
}
