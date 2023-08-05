package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RewardActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        loadImages();
    }

    public void loadImages() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        TextView levelsCompletedText = findViewById(R.id.listOfCompletedLevels);

        // default value is false
        Boolean level1 = sharedPreferences.getBoolean("1", false);
        Boolean level2 = sharedPreferences.getBoolean("2", false);
        Boolean level3 = sharedPreferences.getBoolean("3", false);
        Boolean level4 = sharedPreferences.getBoolean("4", false);
        Boolean level5 = sharedPreferences.getBoolean("5", false);
        Boolean level6 = sharedPreferences.getBoolean("6", false);

        levelsCompletedText.append(" " + String.valueOf(level1));
        levelsCompletedText.append(" " + String.valueOf(level2));
        levelsCompletedText.append(" " + String.valueOf(level3));
        levelsCompletedText.append(" " + String.valueOf(level4));
        levelsCompletedText.append(" " + String.valueOf(level5));
        levelsCompletedText.append(" " + String.valueOf(level6));
    }
}