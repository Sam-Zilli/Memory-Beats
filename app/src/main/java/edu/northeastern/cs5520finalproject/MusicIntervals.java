package edu.northeastern.cs5520finalproject;

import android.os.Bundle;

import java.util.Dictionary;
import java.util.Hashtable;

public class MusicIntervals {

    public MusicIntervals() {
        Dictionary frequencies = new Hashtable();
        createFreqDictionary(frequencies);
    }


    private Dictionary createFreqDictionary(Dictionary frequencies) {
        frequencies.put("A4", 440.0);
        frequencies.put("A#4", 466.16);
        frequencies.put("B4", 493.88);
        frequencies.put("C5", 523.25);
        frequencies.put("C#5", 554.37);
        frequencies.put("D5", 587.33);
        frequencies.put("D#5", 622.25);
        frequencies.put("E5", 659.25);
        frequencies.put("F5", 698.46);
        frequencies.put("F#5", 739.99);
        frequencies.put("G5", 783.99);
        frequencies.put("G#5", 830.61);
        return frequencies;
    }

    private String getInterval(float interval1, float interval2) {
        return "hi";
    }



}
