package edu.northeastern.cs5520finalproject;

import junit.framework.*;

import org.junit.Before;
import org.junit.Test;

public class MusicIntervalsTest extends TestCase {
    private MusicIntervals instanceOfMusicIntervals;
    private double A4 = 440;
    private double AS4 = 466.66;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testFindInterval() {
        MusicIntervals instanceOfMusicIntervals = new MusicIntervals();
        int minor2nd = 1;
        assertEquals(minor2nd, instanceOfMusicIntervals.findInterval((float) A4, (float) AS4));
    }


}