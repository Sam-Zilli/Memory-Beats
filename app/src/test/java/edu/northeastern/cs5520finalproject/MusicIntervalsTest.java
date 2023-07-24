package edu.northeastern.cs5520finalproject;

import junit.framework.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class MusicIntervalsTest extends TestCase {

    MusicIntervals instanceOfMusicalIntervals;
    private double A4 = 440;
    private double AS4 = 466.1637615180898;
    @Test
    public void testFindInterval() {
          MusicIntervals instanceOfMusicIntervals = new MusicIntervals();
          int minor2nd = 1;
          int resultingInterval = instanceOfMusicIntervals.findInterval(A4, AS4);
          assertEquals(minor2nd, resultingInterval);
    }
}