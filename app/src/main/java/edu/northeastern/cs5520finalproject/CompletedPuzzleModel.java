package edu.northeastern.cs5520finalproject;

public class CompletedPuzzleModel {
    private int puzzleId;
    private boolean puzzleCompleted;

    public CompletedPuzzleModel(int puzzleId, boolean puzzleCompleted) {
        this.puzzleId = puzzleId;
        this.puzzleCompleted = puzzleCompleted;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public boolean isPuzzleCompleted() {
        return puzzleCompleted;
    }

    public void setPuzzleCompleted(boolean puzzleCompleted) {
        this.puzzleCompleted = puzzleCompleted;
    }
}
