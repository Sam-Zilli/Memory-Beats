package edu.northeastern.cs5520finalproject;

import junit.framework.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class IntervalsTest extends TestCase {

    Intervals instanceOfMusicalIntervals;
    private double A4 = 440;
    private double AS4 = 466.1637615180898;
    @Test
    public void testFindInterval() {
        Intervals instanceOfMusicIntervals = new Intervals();
        int minor2nd = 1;
        int resultingInterval = instanceOfMusicIntervals.findInterval(A4, AS4);
        assertEquals(minor2nd, resultingInterval);
    }
}