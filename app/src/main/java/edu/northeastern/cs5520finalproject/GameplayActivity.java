package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GameplayActivity extends AppCompatActivity {

    ImageButton button1, button2, button3, button4, button5, button6, button7, button8,
    button9, button10, button11, button12, button13, button14, button15, button16;

    FrequencyPairModel pair1, pair2, pair3, pair4, pair5, pair6, pair7, pair8;

    CardClickedModel previousClick, currentClick;

    private boolean resetButton = false;
    private boolean gameOver = false;

    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);

    private native void startEngine();

    private native void stopEngine();

    //293.665f, 329.00f

    // 261.63f; 329.63f;
    private float frequency1;
    private float frequency2;

    private static final long START_TIME_IN_MILLIS = 240000;
    private TextView textViewCountDown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    ProgressBar progressBar;
    ArrayList<FrequencyPairModel> frequencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        startEngine();

        setupButtons();

        pair1 = new FrequencyPairModel(261.63f, 329.63f);
        pair2 = new FrequencyPairModel(293.66f, 349.23f);
        pair3 = new FrequencyPairModel(329.63f, 392.00f);
        pair4 = new FrequencyPairModel(349.23f, 415.30f);
        pair5 = new FrequencyPairModel(392.00f, 466.16f);
        pair6 = new FrequencyPairModel(440.00f, 523.25f);
        pair7 = new FrequencyPairModel(493.88f, 587.33f);
        pair8 = new FrequencyPairModel(523.25f, 689.25f);
        initializeFrequencyList();
        Collections.shuffle(frequencyList);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(80);
        textViewCountDown = findViewById(R.id.text_countdown);
        startTimer();
    }

    private void initializeFrequencyList() {
        frequencyList.add(pair1);
        frequencyList.add(pair1);
        frequencyList.add(pair2);
        frequencyList.add(pair2);
        frequencyList.add(pair3);
        frequencyList.add(pair3);
        frequencyList.add(pair4);
        frequencyList.add(pair4);
        frequencyList.add(pair5);
        frequencyList.add(pair5);
        frequencyList.add(pair6);
        frequencyList.add(pair6);
        frequencyList.add(pair7);
        frequencyList.add(pair7);
        frequencyList.add(pair8);
        frequencyList.add(pair8);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        touchEvent(event.getAction());
//        return super.onTouchEvent(event);
//    }

    @Override
    public void onDestroy() {
        stopEngine();
        super.onDestroy();
    }

    protected void setupButtons() {
        // retrieving the buttons from the view
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);
        button13 = findViewById(R.id.button13);
        button14 = findViewById(R.id.button14);
        button15 = findViewById(R.id.button15);
        button16 = findViewById(R.id.button16);

        // creating a list of buttons for efficiency
        List<ImageButton> buttonList = new ArrayList<>(Arrays.asList(button1, button2, button3,
                button4, button5, button6, button7, button8, button9, button10, button11,
                button12, button13, button14, button15, button16));

        // Adding on click listeners for each button. Currently will log it's button number.
//        for (int i = 0; i < buttonList.size(); i++) {
//            final int finalI = i;
//            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (finalI == 0) {
//                        Log.i("GameplayActivity", String.valueOf(finalI + 1));
//                    }
//                    else {
//                        Log.i("GameplayActivity", String.valueOf(finalI + 1));
//                    }
//                }
//            });
//        }
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setImageResource(R.drawable.tile_clicked_foreground);
        }

        for (int i = 0; i < buttonList.size(); i++) {
            final int finalI = i;
            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "button clicked");
                    if(resetButton) {
                        previousClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);
                        currentClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);
                        previousClick = null;
                        currentClick = null;
                        resetButton = false;

                    }
                    if(previousClick == null) {
                        previousClick = new CardClickedModel(buttonList.get(finalI), frequencyList.get(finalI).getFrequencyA(), frequencyList.get(finalI).getFrequencyB());
                        buttonList.get(finalI).setImageResource(R.drawable.app_icon_foreground);
                    } else if (currentClick == null) {
                        if(buttonList.get(finalI) == previousClick.getButtonNumber()) {
                            return;
                        }
                        currentClick = new CardClickedModel(buttonList.get(finalI), frequencyList.get(finalI).getFrequencyA(), frequencyList.get(finalI).getFrequencyB());
                        buttonList.get(finalI).setImageResource(R.drawable.app_icon_foreground);
                    }
                    if (previousClick != null && currentClick != null) {
                        if(!checkMatch()) {
                            // set flag to true
                            resetButton = true;
//                            previousClick = null;
//                            currentClick = null;
                        }
                    }
//                    if(gameOver) {
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(null);
//                        builder.setMessage("Congratulations! You have matched all pairs!")
//                                .setTitle("You Win!");
//                        builder.setCancelable(false)
//                                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        finish();
//                                    }
//                                });
//                        builder.setPositiveButton("New game", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                recreate();
//                            }
//                        });
//
//                    }
                    //buttonList.get(finalI).setImageResource(R.drawable.app_icon_foreground);
                }
            });
        }

        for (int i = 0; i < buttonList.size(); i++) {
            final int finalI = i;
            buttonList.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    frequency1 = frequencyList.get(finalI).getFrequencyA();
                    frequency2 = frequencyList.get(finalI).getFrequencyB();
                    touchEvent(event.getAction());
                    return onTouchEvent(event);
                }
                // need to override perform click
            });
        }
    }
    // change from boolean to void
    private boolean checkMatch() {
        if(previousClick.getFrequencyA() == currentClick.getFrequencyA() && previousClick.getFrequencyB() == currentClick.getFrequencyB()) {
            previousClick.getButtonNumber().setVisibility(View.INVISIBLE);
            currentClick.getButtonNumber().setVisibility(View.INVISIBLE);
            previousClick.getButtonNumber().setOnClickListener(null);
            previousClick.getButtonNumber().setOnTouchListener(null);
            currentClick.getButtonNumber().setOnClickListener(null);
            currentClick.getButtonNumber().setOnTouchListener(null);

            previousClick = null;
            currentClick = null;
            frequency1 = 0;
            frequency2 = 0;
            incrementProgress(10);
            return true;
        }
//        else {
//            previousClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);
//            currentClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);
//        }
        return false;
    }

    private void incrementProgress(int incrementValue) {
        int currentProgress = progressBar.getProgress();
        int newProgress = currentProgress + incrementValue;
        if(newProgress == progressBar.getMax()) {
            gameOver = true;
        }

        if(newProgress > progressBar.getMax()) {
            newProgress = progressBar.getMax();
        }
        progressBar.setProgress(newProgress);

        if(progressBar.getProgress() == progressBar.getMax()) {
            countDownTimer.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Congratulations! You have matched all pairs!")
                    .setTitle("You Win!");
            builder.setCancelable(false)
                    .setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.setPositiveButton("New game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressBar.setProgress(0);
                    recreate();
                }
            });
            builder.show();
        }

    }

    // https://developer.android.com/reference/android/os/CountDownTimer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                if(!gameOver) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameplayActivity.this);
                    builder.setMessage("Time ran out. Better luck next time!")
                            .setTitle("You Lose");
                    builder.setCancelable(false)
                            .setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            progressBar.setProgress(0);
//                            mCountDownTimer.cancel();
                            timerRunning = false;
                            recreate();
                        }
                    });
                    builder.show();
                }
            }
        }.start();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeLeftFormatted);

        if(minutes > 2) {
            textViewCountDown.setTextColor(Color.GREEN);
        } else {
            textViewCountDown.setTextColor(Color.RED);
        }
    }
}

//    public float getFrequency1(){
//        return frequency1;
//    }

//    public float getFrequency2(){
//        return frequency2;
//    }


