
package com.sgod.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(57,255,20);
    private final int CYAN = Color.rgb(0,220,255);
    private final int BG = Color.rgb(5,10,14);

    private LinearLayout root;
    private TextView timerText, resultText, periodText, statusText;
    private Button startButton;

    private Handler handler = new Handler();
    private Random random = new Random();
    private ArrayList<String> history = new ArrayList<>();

    private int seconds = 60;
    private int period = 10001;
    private boolean running = false;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!running) return;

            seconds--;

            if (seconds <= 0) {
                seconds = 60;
                period++;
                demoResult();
            }

            updateTimer();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogin();
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        d.setStroke(2, GREEN);
        return d;
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setPadding(12,14,12,14);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private Button button(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(Color.BLACK);
        b.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(GREEN));
        return b;
    }

    private void setup() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(22,22,22,22);
        root.setBackgroundColor(BG);
        setContentView(root);
    }

    private void showLogin() {
        setup();

        root.addView(text("♛ VIP S_GOD ♛", 28, GREEN));
        root.addView(text("PREDICTOR • DEMO EDITION", 16, CYAN));
        root.addView(text("WELCOME TO DEMO APP", 19, Color.WHITE));

        EditText name = new EditText(this);
        name.setHint("Enter demo name");
        name.setHintTextColor(Color.GRAY);
        name.setTextColor(Color.WHITE);
        name.setBackground(shape(Color.rgb(15,25,30),24));
        root.addView(name);

        EditText pin = new EditText(this);
        pin.setHint("Enter demo PIN (any value)");
        pin.setHintTextColor(Color.GRAY);
        pin.setTextColor(Color.WHITE);
        pin.setBackground(shape(Color.rgb(15,25,30),24));
        root.addView(pin);

        Button enter = button("ENTER DEMO");
        root.addView(enter);

        root.addView(text("DEMO ONLY • NO REAL LOGIN",12,Color.GRAY));

        enter.setOnClickListener(v -> showHome());
    }

    private void showHome() {
        running = false;
        handler.removeCallbacks(timerRunnable);
        seconds = 60;
        setup();

        root.addView(text("⚡ VIP S_GOD ⚡",25,GREEN));
        root.addView(text("DEMO SIMULATION",13,Color.GRAY));

        periodText = text("PERIOD  " + period,18,Color.WHITE);
        root.addView(periodText);

        timerText = text("01:00",38,CYAN);
        timerText.setBackground(shape(Color.rgb(10,20,25),150));
        root.addView(timerText);

        statusText = text("SYSTEM READY",15,GREEN);
        root.addView(statusText);

        resultText = text("SAMPLE RESULT: --",24,Color.WHITE);
        resultText.setBackground(shape(Color.rgb(10,20,25),20));
        root.addView(resultText);

        startButton = button("▶ START DEMO");
        root.addView(startButton);

        startButton.setOnClickListener(v -> startDemo());

        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        Button h = button("HOME");
        Button hist = button("HISTORY");
        Button settings = button("SETTINGS");

        nav.addView(h);
        nav.addView(hist);
        nav.addView(settings);
        root.addView(nav);

        h.setOnClickListener(v -> showHome());
        hist.setOnClickListener(v -> showHistory());
        settings.setOnClickListener(v -> showSettings());

        root.addView(text("SIMULATED OUTPUT • NOT A PREDICTION",11,Color.GRAY));
    }

    private void startDemo() {
        if (running) return;
        running = true;
        seconds = 60;

        statusText.setText("ANIMATION RUNNING");
        startButton.setText("RUNNING...");
        startButton.setEnabled(false);

        updateTimer();
        handler.postDelayed(timerRunnable,1000);
    }

    private void demoResult() {
        String[] samples = {"SAMPLE A","SAMPLE B"};
        String value = samples[random.nextInt(2)];

        periodText.setText("PERIOD  " + period);
        resultText.setText("SAMPLE RESULT: " + value);
        statusText.setText("DEMO PERIOD UPDATED");

        history.add(0,"PERIOD "+period+"  •  "+value);
    }

    private void updateTimer() {
        timerText.setText(String.format(Locale.getDefault(),
            "%02d:%02d", seconds/60, seconds%60));
    }

    private void showHistory() {
        setup();
        root.addView(text("DEMO HISTORY",24,GREEN));

        if(history.isEmpty()) {
            root.addView(text("No demo events yet",16,Color.GRAY));
        }

        for(String item : history) {
            root.addView(text(item,16,Color.WHITE));
        }

        Button back = button("HOME");
        root.addView(back);
        back.setOnClickListener(v -> showHome());
    }

    private void showSettings() {
        setup();
        root.addView(text("SETTINGS",24,GREEN));
        root.addView(text("VIP S_GOD DEMO",18,Color.WHITE));
        root.addView(text("Version 1.0",14,Color.GRAY));
        root.addView(text("Simulation mode only",14,CYAN));

        Button back = button("HOME");
        root.addView(back);
        back.setOnClickListener(v -> showHome());
    }

    @Override
    protected void onDestroy() {
        running = false;
        handler.removeCallbacks(timerRunnable);
        super.onDestroy();
    }
}
