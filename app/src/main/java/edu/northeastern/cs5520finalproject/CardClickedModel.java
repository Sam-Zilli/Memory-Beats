package edu.northeastern.cs5520finalproject;

import android.widget.Button;
import android.widget.ImageButton;

public class CardClickedModel {
    private ImageButton buttonNumber;
    private float frequencyA;
    private float frequencyB;

    public CardClickedModel(ImageButton buttonNumber, float frequencyA, float frequencyB) {
        this.buttonNumber = buttonNumber;
        this.frequencyA = frequencyA;
        this.frequencyB = frequencyB;
    }

    public ImageButton getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(ImageButton buttonNumber) {
        this.buttonNumber = buttonNumber;
    }

    public float getFrequencyA() {
        return frequencyA;
    }

    public void setFrequencyA(float frequencyA) {
        this.frequencyA = frequencyA;
    }

    public float getFrequencyB() {
        return frequencyB;
    }

    public void setFrequencyB(float frequencyB) {
        this.frequencyB = frequencyB;
    }
}
