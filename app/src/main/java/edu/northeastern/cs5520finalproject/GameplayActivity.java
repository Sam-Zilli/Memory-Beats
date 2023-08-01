package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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

    private float frequency1;
    private float frequency2;

    private int moveCounter = 0;

    private TextView moveText;

    private static final long START_TIME_IN_MILLIS = 240000;
    //private long START_TIME_IN_MILLIS = 240000;
    private TextView textViewCountDown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    // init level.  The main activity will pass the level into the gameplay activity which will then adjust the
    // the timer
    private int level = 0;

    ImageButton exitButton;

    ProgressBar progressBar;
    ArrayList<FrequencyPairModel> frequencyList = new ArrayList<>();
    //*************************************************************
    ArrayList<Float> frequencyAList = new ArrayList<>(Arrays.asList(261.63f, 293.66f, 329.63f, 349.23f, 392.00f, 440.00f, 493.88f, 523.25f));
    ArrayList<Float> frequencyBList = new ArrayList<>(Arrays.asList(329.63f, 349.23f, 392.00f, 415.30f, 466.16f, 523.25f, 587.33f, 689.25f));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // locking the screen orientation to remain consistent with our main menu screen
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ConstraintLayout constraintLayout = findViewById(R.id.gameLayout);

        // https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable
        // link for animated background tutorial: https://www.youtube.com/watch?v=4lEnLTqsnaw
        // Set AnimationDrawable to Constraint Layout's background
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        startEngine();

        setupButtons();

        // frequencyA and frequencyB contain 8 frequencies each.  Want to randomize the order each game
        Collections.shuffle(frequencyAList);
        Collections.shuffle(frequencyBList);

        // generating frequency pairs using the indices from frequencyAList and frequencyBList
        // adding this frequency pairs to a frequency list.  Each index will correspond to a specific button
        for(int i = 0; i < frequencyAList.size(); i++) {
            FrequencyPairModel newPair = new FrequencyPairModel(frequencyAList.get(i), frequencyBList.get(i));
            frequencyList.add(newPair);
            frequencyList.add(newPair);
        }

//        pair1 = new FrequencyPairModel(261.63f, 329.63f);
//        pair2 = new FrequencyPairModel(293.66f, 349.23f);
//        pair3 = new FrequencyPairModel(329.63f, 392.00f);
//        pair4 = new FrequencyPairModel(349.23f, 415.30f);
//        pair5 = new FrequencyPairModel(392.00f, 466.16f);
//        pair6 = new FrequencyPairModel(440.00f, 523.25f);
//        pair7 = new FrequencyPairModel(493.88f, 587.33f);
//        pair8 = new FrequencyPairModel(523.25f, 689.25f);
//        initializeFrequencyList();

        // shuffling the final frequencyList so that the order will be randomized
        Collections.shuffle(frequencyList);

        // Setting up progress bar.  Max set to 80 since there are 8 unique pairs
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(80);

        // Setting up countdown timer
        textViewCountDown = findViewById(R.id.text_countdown);

        //************** adjusting countdown timer according to level
        level = getIntent().getIntExtra("levelKey", 1);

        // initialize Move counter according to moves left
        setMoveCounterLevel();
        // Setting up textview to display move counter
        moveText = findViewById(R.id.moveCounter);
        String s = "Moves left: " + moveCounter;
        moveText.setText(s);

        if (level > 1) {
            timeLeftInMillis = START_TIME_IN_MILLIS - (20000L * (level - 1));
        }
        //START_TIME_IN_MILLIS = START_TIME_IN_MILLIS - (2L * level);
        startTimer();

        // Setting up exit button and onClickListener
        exitButton = findViewById(R.id.exitButton);
        // need to fix background
        exitButton.setImageResource(R.drawable.baseline_exit_to_app_24);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    timerRunning = false;
                    countDownTimer.cancel();
                    displayConfirmationMessage();
                }
            }
        });

        // Setting up textview to display move counter
//        moveText = findViewById(R.id.moveCounter);
    }

    private void setMoveCounterLevel() {
        if(level == 1) {
            moveCounter = 30;
        } else if (level == 2 || level == 3) {
            moveCounter = 25;
        } else if (level == 4 || level == 5) {
            moveCounter = 20;
        } else {
            moveCounter = 15;
        }
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

        // Setting up default image for each button (card flipped down)
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setImageResource(R.drawable.tile_clicked_foreground);
        }

        // Setting up onClickListener for each button. Memory game logic located here
        for (int i = 0; i < buttonList.size(); i++) {
            final int finalI = i;
            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "button clicked");
                    // This will trigger only if two notes have been clicked but their frequencies don't match
                    if(resetButton) {
                        // flip card down to default image
                        previousClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);
                        currentClick.getButtonNumber().setImageResource(R.drawable.tile_clicked_foreground);

                        // reset both previousClick and currentClick to null and resetButton to false
                        previousClick = null;
                        currentClick = null;
                        resetButton = false;

                    }
                    if(previousClick == null) {
                        previousClick = new CardClickedModel(buttonList.get(finalI), frequencyList.get(finalI).getFrequencyA(), frequencyList.get(finalI).getFrequencyB());
                        // flip card up
                        buttonList.get(finalI).setImageResource(R.drawable.app_icon_foreground);
                    } else if (currentClick == null) {
                        // ensures that a button can't match itself
                        if(buttonList.get(finalI) == previousClick.getButtonNumber()) {
                            return;
                        }
                        currentClick = new CardClickedModel(buttonList.get(finalI), frequencyList.get(finalI).getFrequencyA(), frequencyList.get(finalI).getFrequencyB());
                        // flip card up
                        buttonList.get(finalI).setImageResource(R.drawable.app_icon_foreground);
                    }
                    if (previousClick != null && currentClick != null) {
                        // check if the two buttons have the same frequencies
                        if(!checkMatch()) {
                            // set flag to true if buttons do not match
                            resetButton = true;

                            // increase move counter and display updated count
//                            moveCounter++;
//                            String current = "Move: " + moveCounter;

                            // *****************
                            moveCounter--;
                            String current = "Moves Left: " + moveCounter;
                            moveText.setText(current);
                        }
                    }
                    // ********************** check if exceed max moves allowed
                    if(moveCounter == 0) {
                        gameOver = true;
                        timerRunning = false;
                        countDownTimer.cancel();
                        displayNoMovesLeftMessage();
                    }
                }
            });
        }

        // Setting up onTouchListener for each button
        // OnTouchListener is responsible for producing the frequencies
        for (int i = 0; i < buttonList.size(); i++) {
            final int finalI = i;
            buttonList.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    frequency1 = frequencyList.get(finalI).getFrequencyA();
                    frequency2 = frequencyList.get(finalI).getFrequencyB();
                    // this line is required to generate sound
                    touchEvent(event.getAction());
                    return onTouchEvent(event);
                }
                // need to override perform click
            });
        }
    }

    private void displayNoMovesLeftMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameplayActivity.this);
        builder.setMessage("You ran out of moves!  Better luck next time!")
                .setTitle("No Moves Left");
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
                progressBar.setProgress(0);
                setMoveCounterLevel();
                recreate();
            }
        });
        builder.show();
    }


    // Method to check if two frequency pairs match
    private boolean checkMatch() {
        if(previousClick.getFrequencyA() == currentClick.getFrequencyA() && previousClick.getFrequencyB() == currentClick.getFrequencyB()) {
            // If buttons match, make both buttons invisible
            previousClick.getButtonNumber().setVisibility(View.INVISIBLE);
            currentClick.getButtonNumber().setVisibility(View.INVISIBLE);

            // Deactivate onClickListener and onTouchListener for both buttons
            previousClick.getButtonNumber().setOnClickListener(null);
            previousClick.getButtonNumber().setOnTouchListener(null);
            currentClick.getButtonNumber().setOnClickListener(null);
            currentClick.getButtonNumber().setOnTouchListener(null);

            // Reset previousClick and currentClick
            previousClick = null;
            currentClick = null;

            // set both frequencies to 0
            frequency1 = 0;
            frequency2 = 0;

            // increment progress bar accordingly and update move counter
            incrementProgress(10);
//            moveCounter++;
//            String current = "Move: " + moveCounter;

            // ***************
            moveCounter--;
            String current = "Moves Left: " + moveCounter;
            moveText.setText(current);
            return true;
        }
        return false;
    }

    // Method to increment progress bar
    private void incrementProgress(int incrementValue) {
        int currentProgress = progressBar.getProgress();
        int newProgress = currentProgress + incrementValue;

        // if progress bar is completely filled, this means that game is over
        if(newProgress == progressBar.getMax()) {
            gameOver = true;
        }

        // display updated progress in progress bar
        if(newProgress > progressBar.getMax()) {
            newProgress = progressBar.getMax();
        }
        progressBar.setProgress(newProgress);

        // if progress bar is full, game is over.  Now display You Win alert dialog
        if(progressBar.getProgress() == progressBar.getMax()) {
            countDownTimer.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Congratulations! You have matched all pairs!")
                    .setTitle("You Win!");
            builder.setCancelable(false)
                    .setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // close activity and return to main menu
                            finish();
                        }
                    });
            builder.setPositiveButton("New game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // start a new activity (new game)
                    progressBar.setProgress(0);
                    setMoveCounterLevel();
                    recreate();
                }
            });
            builder.show();
        }

    }

    // https://developer.android.com/reference/android/os/CountDownTimer
    // referred to following link for tutorial: https://www.youtube.com/watch?v=zmjfAcnosS0
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
                    // Timer ran out, player loses, display alert dialog
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
                            // might be redundant
                            //timerRunning = false;
                            setMoveCounterLevel();
                            recreate();
                        }
                    });
                    builder.show();
                }
            }
        }.start();
        timerRunning = true;
    }

    // Method to update countdown timer
    private void updateCountDownText() {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeLeftFormatted);

        // *********** color settings will be different if level is 4, 5 or 6
        if(level >= 4) {
            if (minutes > 1) {
                textViewCountDown.setTextColor(Color.GREEN);
            } else {
                textViewCountDown.setTextColor(Color.RED);
            }
        } else {
            if(minutes > 2) {
                textViewCountDown.setTextColor(Color.GREEN);
            } else {
                textViewCountDown.setTextColor(Color.RED);
            }
        }

        // If countdown timer reaches below 3 minutes, text color changes to red
//        if(minutes > 2) {
//            textViewCountDown.setTextColor(Color.GREEN);
//        } else {
//            textViewCountDown.setTextColor(Color.RED);
//        }
    }

    // Confirm if user wants to quit if game is currently running
    @Override
    public void onBackPressed() {
        if(timerRunning) {
            timerRunning = false;
            countDownTimer.cancel();
           displayConfirmationMessage();
        }
        //super.onBackPressed();
    }

    public void displayConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameplayActivity.this);
        builder.setMessage("You are currently in a game session. You will lose all progress if you quit.  Are you sure you want to quit?")
                .setTitle("Quit confirmation");
        builder.setCancelable(false)
                .setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setPositiveButton("Resume game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerRunning = true;
                startTimer();
            }
        });
        builder.show();
    }
}



