package edu.northeastern.cs5520finalproject;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MusicIntervals {

    private double EQUAL_TEMP_RATIO = 1.059463094359295;

    public MusicIntervals() {
        Dictionary frequencies = new Hashtable();
        createFreqDictionary(frequencies);

        Dictionary intervalsFromA4 = new Hashtable();
        createIntervalsDictionaryFromA4(intervalsFromA4);


    }
    public int findInterval(double frequency1, double frequency2) {
        int number_of_semi_tones = 0;

        // checking for same note
        if(frequency1 == frequency2) {
           return number_of_semi_tones;
       }

       if(frequency1 < frequency2) {
            while(frequency1 <= frequency2) {
                if(frequency1 == frequency2) {
                    // caught up to 2nd note
                    return number_of_semi_tones;
                }
                frequency1 = frequency1*EQUAL_TEMP_RATIO;
                number_of_semi_tones += 1;
            }
       }

       return number_of_semi_tones;
    }

    private void createIntervalsDictionaryFromA4(Dictionary intervalsFromA4) {
        intervalsFromA4.put("Unison", "A4");
        intervalsFromA4.put("Minor2nd", "Bb4");
        intervalsFromA4.put("Major2nd", "B4");
        intervalsFromA4.put("Minor3rd", "C5");
        intervalsFromA4.put("Major3rd", "C#5");
        intervalsFromA4.put("Perfect4th", "D5");
        intervalsFromA4.put("Tritone", "D#5");
        intervalsFromA4.put("Perfect5th", "E5");
        intervalsFromA4.put("Minor6th", "F5");
        intervalsFromA4.put("Major6th", "F#5");
        intervalsFromA4.put("Minor7th", "G5");
        intervalsFromA4.put("Major7th", "G#5");
        intervalsFromA4.put("Octave", "A5");





    }


    private Dictionary createFreqDictionary(Dictionary frequencies) {
        // Frequencies provided by https://pages.mtu.edu/~suits/notefreqs.html
        // https://cmtext.indiana.edu/acoustics/chapter1_pitch.php#:~:text=The%20formula%20for%20successive%20equal,approximately%20equals%20~1%3A1.05946.&text=As%20an%20example%2C%20to%20find,by%201.05946%20to%20get%20~466.163.
        ArrayList<String> notes = new ArrayList<>();
        notes.add("A4");
        notes.add("A#4");
        notes.add("B4");
        notes.add("C5");
        notes.add("C#5");
        notes.add("D5");
        notes.add("D#5");
        notes.add("E5");
        notes.add("F5");
        notes.add("F#5");
        notes.add("G5");
        notes.add("G#5");


        double pitch = 440.0;
        for(int i = 0; i < 12; i++) {
            pitch = pitch * EQUAL_TEMP_RATIO;
            frequencies.put(notes.get(i), pitch);
        }

        for (int i = 0; i < 12; i++) {
            frequencies.put(i, String.valueOf(frequencies.get(i)));
        }
//        frequencies.put("A4", 440.0);
//        frequencies.put("A#4", 466.16);
//        frequencies.put("Bb4", 466.16);
//        frequencies.put("B4", 493.88);
//        frequencies.put("C5", 523.25);
//        frequencies.put("C#5", 554.37);
//        frequencies.put("D5", 587.33);
//        frequencies.put("D#5", 622.25);
//        frequencies.put("E5", 659.25);
//        frequencies.put("F5", 698.46);
//        frequencies.put("F#5", 739.99);
//        frequencies.put("G5", 783.99);
//        frequencies.put("G#5", 830.61);
//        frequencies.put("A5", 880.00);
        return frequencies;
    }
}
