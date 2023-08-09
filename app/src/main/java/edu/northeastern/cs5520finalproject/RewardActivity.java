package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RewardActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reward);
        loadImages();
    }

    // this is causing an audio bug.  Bug seems to be fixed
    // pressing back button will redirect to main activity page and not gameplay page
    @Override
    public void onBackPressed() {
        //finish();
        Intent intent = new Intent(RewardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void returnToMainMenu(View view) {
        Intent intent = new Intent(RewardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void loadImages() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // default value is false
        Boolean level1 = sharedPreferences.getBoolean("1", false);
        Boolean level2 = sharedPreferences.getBoolean("2", false);
        Boolean level3 = sharedPreferences.getBoolean("3", false);
        Boolean level4 = sharedPreferences.getBoolean("4", false);
        Boolean level5 = sharedPreferences.getBoolean("5", false);
        Boolean level6 = sharedPreferences.getBoolean("6", false);

        ImageButton one = findViewById(R.id.one);
        one.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton two= findViewById(R.id.two);
        two.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton three = findViewById(R.id.three);
        three.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton four = findViewById(R.id.four);
        four.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton five = findViewById(R.id.five);
        five.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton six = findViewById(R.id.six);
        six.setScaleType(ImageView.ScaleType.FIT_CENTER);

        if(level1) {
            //one.setImageResource(R.drawable.one);
            one.setImageResource(R.drawable.puzzle_1);
        }
        if(level2) {
            //two.setImageResource(R.drawable.two);
            two.setImageResource(R.drawable.puzzle_2);
        }
        if(level3) {
            //three.setImageResource(R.drawable.three);
            three.setImageResource(R.drawable.puzzle_3);
        }
        if(level4) {
            //four.setImageResource(R.drawable.four);
            four.setImageResource(R.drawable.puzzle_4);
        }
        if(level5) {
            //five.setImageResource(R.drawable.five);
            five.setImageResource(R.drawable.puzzle_5);
        }
        if(level6) {
            //six.setImageResource(R.drawable.six);
            six.setImageResource(R.drawable.puzzle_6);
        }
    }
}