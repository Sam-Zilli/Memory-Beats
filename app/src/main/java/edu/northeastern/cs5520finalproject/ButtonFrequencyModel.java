package edu.northeastern.cs5520finalproject;

// No longer need
public class ButtonFrequencyModel {
    private Integer buttonNumber;
    private float frequencyA;
    private float frequencyB;

    public ButtonFrequencyModel(Integer buttonNumber, float frequencyA, float frequencyB) {
        this.buttonNumber = buttonNumber;
        this.frequencyA = frequencyA;
        this.frequencyB = frequencyB;
    }

    public Integer getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(Integer buttonNumber) {
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
