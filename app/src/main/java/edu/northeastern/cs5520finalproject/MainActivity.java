package edu.northeastern.cs5520finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//The below import is for soundpool, a tool for playing music notes.
import android.media.SoundPool;


public class MainActivity extends AppCompatActivity {

    private static Bitmap imageOriginal, imageScaled;
    private static Matrix matrix;
    private ImageView wheel;
    private ImageView wheelBackground;
    private int wheelHeight, wheelWidth;
    private double left;
    private double top;
    private double right;
    private double bottom;
    private int upperleftX;  //the upper left corner x coordinate of the wheel image
    private int upperleftY;   //the upper left corner y coordinate of the wheel image
    private float screenWidth;
    private float screenHeight;

    private Button startButton;

    private Button galleryButton;

    private TextView levelText;
    private int level;

    private double totalRotation; //This attribute keep tracks the total angle rotated in degree.


    //The following attibutes are for the music notes sound generation
    private SoundPool soundPool;
    private Integer integerSoundIDa;
    private Integer integerSoundIDb;
    private Integer integerSoundIDc;
    private Integer integerSoundIDd;
    private Integer integerSoundIDe;
    private Integer integerSoundIDf;
    private Integer integerSoundIDg;

    private float floatSpeed = 1.0f;

    private String musicSheet;
    private int musicSheetLength;
    private int musicStep = 0;

    private int stepsToPlay = 0;   //Tracking number of steps retrieving the hand movement data.




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ******************
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Drawable d = getDrawable(R.drawable.rybcolorwheel1536x1536withhole);
        Drawable dbackground = getDrawable(R.drawable.rybcolorwheel1536x1536mutednumbercontrast);
        wheel = new ImageView(this);
        wheelBackground= new ImageView(this);
        wheel.setImageDrawable(d);
        wheelBackground.setImageDrawable(dbackground);
        setContentView(R.layout.activity_main);


        totalRotation = 0;



        //initialize the button to start game
        startButton = new Button(this);
        startButton.setText("Play");


        //initialize the gallery button
        galleryButton = new Button(this);
        galleryButton.setText("Gallery");



        //initialize the level textview
        level = 1;
        levelText = new TextView(this);
        levelText.setText("LV: " + String.valueOf(level));




        //The following codes are for setting up the soundpool

        SoundPool.Builder builder = new SoundPool.Builder();
        soundPool = builder.build();



        //The mp3 files of the music note sound are in the "raw" folder inside res folder.

        integerSoundIDa = soundPool.load(this, R.raw.a3, 1);
        integerSoundIDb = soundPool.load(this, R.raw.b3, 1);
        integerSoundIDc = soundPool.load(this, R.raw.c3, 1);
        integerSoundIDd = soundPool.load(this, R.raw.d3, 1);
        integerSoundIDe = soundPool.load(this, R.raw.e3, 1);
        integerSoundIDf = soundPool.load(this, R.raw.f3, 1);
        integerSoundIDg = soundPool.load(this, R.raw.g3, 1);


        /**
         integerSoundIDa = soundPool.load(this, R.raw.pianoa, 1);
         integerSoundIDb = soundPool.load(this, R.raw.pianob, 1);
         integerSoundIDc = soundPool.load(this, R.raw.pianomiddlec, 1);
         integerSoundIDd = soundPool.load(this, R.raw.pianod, 1);
         integerSoundIDe = soundPool.load(this, R.raw.pianoe, 1);
         integerSoundIDf = soundPool.load(this, R.raw.pianof, 1);
         integerSoundIDg = soundPool.load(this, R.raw.pianog, 1);
         */




        //The following code makes a music sheet
        //musicSheet = "BAGACDCBCEFED#EBAG#ABAG#ACACBAGABAGABAGF#EBAG#ACDCBCEFED#EBAG#ABAG#ACACBAGABAGABAGF#EBAG#AEFGGAGFEDEFGGAGFEDCDEEFEDCCDEEFEDCBAG#ACDCBCEFED#EBAG#ABAG#ACABCBAG#AEFDCBA";
        musicSheet = "EDCDEEE#DDDEGG#EDCDEEE#EDDEDC#";
        //musicSheet = "GGDDEED#CCBBAAG#DDCCBBA#DDCCBBA#GGDDEED#CCBBAAG";
        //musicSheet = "FFEEDDC#";
        musicSheetLength = musicSheet.length();







        //The following codes are just to add the wheel and the wheel background to the layout. When created the wheel's
        // coordinate (the left upper corne of the button)  is at (0,0)
        ConstraintLayout CL = findViewById(R.id.constraintLayout);
        ConstraintSet set = new ConstraintSet();
        wheel.setId(View.generateViewId());  // cannot set id after add
        CL.addView(wheel,0);
        set.clone(CL);
        set.connect(wheel.getId(), ConstraintSet.TOP, CL.getId(), ConstraintSet.TOP, 0);
        set.applyTo(CL); // apply to layout

        set = new ConstraintSet();
        wheelBackground.setId(View.generateViewId());  // cannot set id after add
        CL.addView(wheelBackground,0);
        set.clone(CL);
        set.connect(wheelBackground.getId(), ConstraintSet.TOP, CL.getId(), ConstraintSet.TOP, 0);

        //The following codes are just to add the start play button to the layout. When created the button's
        // coordinate (the left upper corne of the button)  is at (0,0)
        set = new ConstraintSet();
        startButton.setId(View.generateViewId());  // cannot set id after add
        CL.addView(startButton,0);
        set.clone(CL);
        set.connect(startButton.getId(), ConstraintSet.TOP, CL.getId(), ConstraintSet.TOP, 0);

        //The following codes are just to add the gallery button to the layout. When created the button's
        // coordinate (the left upper corne of the button)  is at (0,0)
        set = new ConstraintSet();
        galleryButton.setId(View.generateViewId());  // cannot set id after add
        CL.addView(galleryButton,0);
        set.clone(CL);
        set.connect(galleryButton.getId(), ConstraintSet.TOP, CL.getId(), ConstraintSet.TOP, 0);





        //The following codes are just to add the level textview to the layout. When created the textview's
        // coordinate (the left upper corne of the button)  is at (0,0)
        set = new ConstraintSet();
        levelText.setId(View.generateViewId());  // cannot set id after add
        CL.addView(levelText,0);
        set.clone(CL);
        set.connect(levelText.getId(), ConstraintSet.TOP, CL.getId(), ConstraintSet.TOP, 0);



















        //setContentView(wheel);
        //setContentView(CL);
        //setContentView(wheelBackground);


        wheel.setScaleType(ImageView.ScaleType.MATRIX);
        wheelBackground.setScaleType(ImageView.ScaleType.MATRIX);
        final float[] dimensions = getWindowSize(this);
        screenWidth = dimensions[0];
        screenHeight = dimensions[1];
        matrix = setCenterAndScale(screenWidth, screenHeight, d);
        Matrix backgroundMatrix = setCenterAndScale(screenWidth, screenHeight, dbackground);
        wheel.setImageMatrix(matrix);
        wheelBackground.setImageMatrix(backgroundMatrix);
        wheel.setOnTouchListener(new WheelOnTouchListener(wheel, matrix));


        //The following code is to resize and place the button at the right place
        startButton.setWidth(Math.round(calculateButtonWidth(screenWidth, screenHeight)));
        startButton.setHeight(Math.round(calculateButtonHeight(screenWidth, screenHeight)));
        startButton.setX(calculateButtonX(screenWidth,screenHeight));
        startButton.setY(calculateButtonY(screenWidth,screenHeight));
        startButton.setTextSize(screenWidth / 32);

        //The following code is to resize and place the level textview at the right place
        levelText.setWidth(Math.round(calculateLevelViewWidth(screenWidth, screenHeight)));
        levelText.setHeight(Math.round(calculateLevelViewHeight(screenWidth, screenHeight)));
        levelText.setX(calculateLevelViewX(screenWidth,screenHeight));
        levelText.setY(calculateLevelViewY(screenWidth,screenHeight));
        Log.i("heyhey",String.valueOf(Math.round(calculateLevelViewHeight(screenWidth, screenHeight))));
        levelText.setTextSize(screenWidth / 28);
        levelText.setGravity(Gravity.CENTER);


        //The following code is to resize and place the gallery button at the upper right corner
        galleryButton.setWidth(Math.round(calculateGalleryButtonWidth(screenWidth, screenHeight)));
        galleryButton.setHeight(Math.round(calculateGalleryButtonHeight(screenWidth, screenHeight)));
        galleryButton.setX(calculateGalleryButtonX(screenWidth,screenHeight));
        galleryButton.setY(calculateGalleryButtonY(screenWidth,screenHeight));
        galleryButton.setTextSize(screenWidth / 60);



        galleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //playA();
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);
            }
        });








        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //playA();
                Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                //**************** new: passing level to gameplay activity
                intent.putExtra("levelKey", level);
                startActivity(intent);
            }
        });







        /**
         if (imageOriginal == null) {
         imageOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.colorwheel1536x1536);
         }

         if (matrix == null) {
         matrix = new Matrix();
         } else {
         matrix.reset();
         }

         wheel = (ImageView) findViewById(R.id.imageView_wheel);
         wheel.setOnTouchListener(new WheelOnTouchListener());
         **/


        /**
         this.left = wheel.getLeft();
         this.top = wheel.getTop();
         this.bottom = wheel.getBottom();
         this.right = wheel.getRight();
         Log.i("testleft", String.valueOf(left));
         Log.i("testright", String.valueOf(right));
         Log.i("testbottom", String.valueOf(bottom));
         Log.i("testtop", String.valueOf(top));
         Log.i("testXcoordinate", String.valueOf(x));
         Log.i("testYcoordinate", String.valueOf(y));
         **/


        /**

         ViewTreeObserver vto = wheel.getViewTreeObserver();
         vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override public void onGlobalLayout() {

        if (wheelHeight == 0 || wheelWidth == 0) {
        wheelHeight = wheel.getHeight();
        wheelWidth = wheel.getWidth();

        int[] locations = new int[2];
        wheel.getLocationOnScreen(locations);
        upperleftX = locations[0];
        upperleftY= locations[1];
        Log.i("testcoordinate", String.format("X:%d Y:%d",upperleftX,upperleftY));

        Matrix resize = new Matrix();

        float xscale = (float)Math.min(wheelWidth, wheelHeight) / (float)imageOriginal.getWidth();


        }

        }
        });
         **/


    }



    // This function gets the size of the whole window.
    private static float[] getWindowSize(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            float width;
            float height;
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            width = windowMetrics.getBounds().width() - insets.left - insets.right;
            height = windowMetrics.getBounds().height() - insets.top - insets.bottom;
            Log.i ("testScreenSize1", String.valueOf(width));
            Log.i ("testScreenSize1", String.valueOf(height));
            return new float[] {width, height};


        } else {
            final Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.i ("testScreenSize2", String.valueOf(size.x));
            Log.i ("testScreenSize2", String.valueOf(size.y));
            return new float[] {size.x, size.y};

        }
    }


    //This function returns the transformation matrix that can set the image to the center of the screen and also scale the image to fit the screen
    private static Matrix setCenterAndScale(float width, float height, Drawable d) {
        final float drawableWidth = d.getIntrinsicWidth();
        final float drawableHeight = d.getIntrinsicHeight();
        final float widthScale = width / drawableWidth;
        final float heightScale = height / drawableHeight;
        final float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
        Matrix m = new Matrix();
        m.postScale(scale, scale);
        m.postTranslate((width - drawableWidth * scale) / 2F,
                (height - drawableHeight * scale) / 2F);
        return m;
    }


    //Calculate the height of the button, so that it will be of good size and never go off screen
    private static float calculateButtonHeight(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonHeight = (height - wheelSize) / 4;
        return buttonHeight;
    }

    //Calculate the width of the button, so that it will be of good size and never go off screen
    private static float calculateButtonWidth(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonWidth = width / 3;
        return buttonWidth;
    }


    //Calculate the x coordinate of the button, so that it will never go off screen
    private static float calculateButtonX(float width, float height) {
        float buttonX = width / 3;
        return buttonX;
    }

    //Calculate the y coordinate of the button, so that it will never go off screen
    private static float calculateButtonY(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonY = wheelSize + ((height - wheelSize) / 2) * (1 + (1/4));
        return buttonY;
    }



    //Calculate the height of the gallery button, so that it will be of good size and never go off screen
    private static float calculateGalleryButtonHeight(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonHeight = (height - wheelSize) / 6;
        return buttonHeight;
    }

    //Calculate the width of the gallery button, so that it will be of good size and never go off screen
    private static float calculateGalleryButtonWidth(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonWidth = width / 3;
        return buttonWidth;
    }


    //Calculate the x coordinate of the gallery button, so that it will never go off screen
    private static float calculateGalleryButtonX(float width, float height) {
        float buttonX = width - (width / 3) - (width / 24) ;
        return buttonX;
    }

    //Calculate the y coordinate of the button, so that it will never go off screen
    private static float calculateGalleryButtonY(float width, float height) {
        float wheelSize = Math.min(width, height);
        float buttonY = ((height - wheelSize) / 2) * (1 / 8);
        return buttonY;
    }





    //Calculate the height of the level textview, so that it will be of good size and never go off screen
    private static float calculateLevelViewHeight(float width, float height) {
        float wheelSize = Math.min(width, height);
        float textHeight = (height - wheelSize) / 4;
        return textHeight;
    }

    //Calculate the width of the level textview, so that it will be of good size and never go off screen
    private static float calculateLevelViewWidth(float width, float height) {
        float wheelSize = Math.min(width, height);
        float textWidth = width / 3;
        return textWidth;
    }


    //Calculate the x coordinate of the level textview, so that it will never go off screen
    private static float calculateLevelViewX(float width, float height) {
        float textX = 0;
        return textX;
    }

    //Calculate the y coordinate of the level textview, so that it will never go off screen
    private static float calculateLevelViewY(float width, float height) {
        float wheelSize = Math.min(width, height);
        float textY = wheelSize + ((height - wheelSize) / 2) * (1 + (1/4));
        return textY;
    }











    private class WheelOnTouchListener implements View.OnTouchListener {

        double startingAngle;

        private ImageView wheel;

        private Matrix matrix;



        WheelOnTouchListener( ImageView wheel, Matrix matrix) {
            this.wheel = wheel;
            this.matrix =matrix;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    Log.i("testMotionEvent1", String.valueOf(event.getX()));
                    Log.i("testMotionEvent1", String.valueOf(event.getY()));
                    this.startingAngle = getAngle(event.getX(), event.getY());
                    Log.i("testMotionEvent1.1", String.valueOf(startingAngle));
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("testMotionEvent2", String.valueOf(event.getX()));
                    Log.i("testMotionEvent2", String.valueOf(event.getY()));
                    double currentAngle = getAngle(event.getX(), event.getY());
                    Log.i("testMotionEvent2.1", String.valueOf(currentAngle));
                    float angleRotated = 0;
                    if (startingAngle > 150 && currentAngle < 0 ) {
                        angleRotated = (float) (startingAngle - (currentAngle + 360));
                    }
                    else if (startingAngle < 0 && currentAngle > 150 ) {
                        angleRotated = (float) (startingAngle + 360 - currentAngle);
                    }
                    else {
                        angleRotated = (float) (startingAngle - currentAngle);
                    }

                    //Adding the following lines to restrict user to only be able to spin counter clockwise
                    if (angleRotated > 0) {
                        //angleRotated = 0;
                    }


                    totalRotation = totalRotation + angleRotated; // Adding the angle rotated to the total rotation tracker
                    if (wheel!= null) {
                        rotateWheel(wheel, matrix, screenWidth / 2f, screenHeight / 2f, angleRotated);
                    }
                    this.startingAngle = currentAngle;
                    setLevel(totalRotation, levelText);

                    //Play a music note from the music sheet
                    if (stepsToPlay%6 == 0) {
                        playMusic(musicSheet.charAt((musicStep % musicSheetLength)));
                        musicStep += 1;
                    }
                    stepsToPlay += 1;






                    break;
                case MotionEvent.ACTION_UP:
                    break;



            }
            return true;
        }
    }


    //This function takes the x and y coordinate of the finger touch as arguments and returns
    // the angle related to the line from the center of the wheel to the right.
    private double getAngle(double x, double y) {
        //The following rx and ry are related to the center of the wheel image,
        // which is set to be the center of the screen in onCreate.
        //the - sign in front of the ry is because on android screen, point (0,0) is at the left
        // upper corner and rightward (this is the same as in euclidean coordinate) and downward (instead of upward) are positive directions.

        double rx = x - (screenWidth / 2d);
        double ry = -(y - (screenHeight / 2d));
        double angle = Math.toDegrees(Math.atan2(ry, rx));
        Log.i("testGetAngle", String.valueOf(angle));
        return angle;

    }


    //The following function rotate the wheel image according to the parameter degree.
    private static void rotateWheel(ImageView view,Matrix imageMatrix, float centerOfWheelx, float centerOfWheely, float degree) {

        imageMatrix.postTranslate(-centerOfWheelx, -centerOfWheely);
        imageMatrix.postRotate(degree);
        imageMatrix.postTranslate(centerOfWheelx, centerOfWheely);
        view.setImageMatrix(imageMatrix);



    }


    //The following function set the level of the level textview according to the current angle of the wheel
    private void setLevel(double angle, TextView levelText) {
        Log.i("angle", String.valueOf(angle));
        double levelsPassed;
        if (angle <= 0) {
            levelsPassed = -(angle - 30) / 60;
        }
        else {
            levelsPassed = -(angle + 30) / 60;
        }
        int levelsPassedInteger;
        if (levelsPassed >= 0) {
            levelsPassedInteger = (int)Math.floor(levelsPassed);
        }
        else {
            levelsPassedInteger = (int)Math.ceil(levelsPassed);
        }
        int accumulatedLevelPassed = levelsPassedInteger % 6;
        if (accumulatedLevelPassed < 0) {
            accumulatedLevelPassed += 6;
        }
        this.level = 1 + accumulatedLevelPassed;
        levelText.setText("LV: " + String.valueOf(level));

    }


    public void playMusic(char note) {
        if (note == 'A') {
            playA();
        }
        else if (note == 'B') {
            playB();
        }
        else if (note == 'C') {
            playC();
        }
        else if (note == 'D') {
            playD();
        }
        else if (note == 'E') {
            playE();
        }
        else if (note == 'F') {
            playF();
        }
        else if (note == 'G') {
            playG();
        }

    }



    public void playA(){
        Log.i("sound", "a");
        soundPool.play(integerSoundIDa, 1, 1, 1, 0, floatSpeed);
    }
    public void playB(){
        soundPool.play(integerSoundIDb, 1, 1, 1, 0, floatSpeed);
    }
    public void playC(){
        soundPool.play(integerSoundIDc, 1, 1, 1, 0, floatSpeed);
    }
    public void playD(){
        soundPool.play(integerSoundIDd, 1, 1, 1, 0, floatSpeed);
    }
    public void playE(){
        soundPool.play(integerSoundIDe, 1, 1, 1, 0, floatSpeed);
    }
    public void playF(){
        soundPool.play(integerSoundIDf, 1, 1, 1, 0, floatSpeed);
    }
    public void playG(){
        soundPool.play(integerSoundIDg, 1, 1, 1, 0, floatSpeed);
    }

}