package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

//    static {
//        System.loadLibrary("native-lib");
//    }

//    private native void touchEvent(int action);
//
//    private native void startEngine();
//
//    private native void stopEngine();
    //Button gameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //gameButton = findViewById(R.id.button3);
        //startEngine();
    }

    public void startGameplayActivity(View view) {
        Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
        startActivity(intent);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        touchEvent(event.getAction());
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public void onDestroy() {
//        stopEngine();
//        super.onDestroy();
//    }
}