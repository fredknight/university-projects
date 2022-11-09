/**
 * Author:
 * StudentID: fk18726
 * RegNumber: 1804162
 **/

package com.example.sudoku_application;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SolverActivity extends AppCompatActivity {
    private Solver solver;
    private SolverView solverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);
        solverView = findViewById(R.id.SolverView);
        solver = solverView.getSolver();
        TextView textView = findViewById(R.id.textView5);
        textView.setText(R.string.enter_puzzle);
    }

    public void buttonOne(View view) {
        solver.setGrid(1);
        solverView.invalidate();
    }

    public void buttonTwo(View view) {
        solver.setGrid(2);
        solverView.invalidate();
    }

    public void buttonThree(View view) {
        solver.setGrid(3);
        solverView.invalidate();
    }

    public void buttonFour(View view) {
        solver.setGrid(4);
        solverView.invalidate();
    }

    public void buttonFive(View view) {
        solver.setGrid(5);
        solverView.invalidate();
    }

    public void buttonSix(View view) {
        solver.setGrid(6);
        solverView.invalidate();
    }

    public void buttonSeven(View view) {
        solver.setGrid(7);
        solverView.invalidate();
    }

    public void buttonEight(View view) {
        solver.setGrid(8);
        solverView.invalidate();
    }

    public void buttonNine(View view) {
        solver.setGrid(9);
        solverView.invalidate();
    }

    public void buttonClear(View view) {
        solver.setGrid(0);
        solverView.invalidate();
    }

    public void buttonReset(View view) {
        solver.resetGrid();
        solverView.invalidate();
        TextView textView = findViewById(R.id.textView5);
        textView.setText(R.string.enter_puzzle);
    }

    public void buttonSolve(View view) {
        if (!solver.checkEntered()) {
            TextView textView = findViewById(R.id.textView5);
            textView.setText(R.string.invalid_puzzle);
        } else {
            new Solve().execute();
            solverView.invalidate();
        }
    }

    private class Solve extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            solver.solveGrid();
            return null;
        }

        protected void onPostExecute(Void result) {
            TextView textView = findViewById(R.id.textView5);
            textView.setText(R.string.solution_found);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int[][] old = solver.getGrid();
            setContentView(R.layout.activity_solver);
            solverView = findViewById(R.id.SolverView);
            solver = solverView.getSolver();
            solver.copyGrid(old);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            int[][] old = solver.getGrid();
            setContentView(R.layout.activity_solver);
            solverView = findViewById(R.id.SolverView);
            solver = solverView.getSolver();
            solver.copyGrid(old);
        }
    }
}
