package com.zolachu.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    private boolean running;
    private int seconds = 0;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running");
            seconds = savedInstanceState.getInt("seconds");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    protected void onStart() {
        super.onStart();

        if (wasRunning == true) {
            running = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;

    }



    public void onClickStart(View v) {
        running = true;
    }

    public void onClickStop(View v) {
        running = false;
    }

    public void onClickReset(View v) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds/3600;
                int minutes = (seconds - hours * 3600) / 60;
                int second = seconds % 60;
                String time = String.format(getString(R.string.time_format), hours, minutes, second);
                tvTime = findViewById(R.id.time_view);
                tvTime.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }

        });
    }
}