package edu.northeastern.cs5520finalproject;

public class FrequencyPairModel {
    private float frequencyA;
    private float frequencyB;

    public FrequencyPairModel(float frequencyA, float frequencyB) {
        this.frequencyA = frequencyA;
        this.frequencyB = frequencyB;
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
