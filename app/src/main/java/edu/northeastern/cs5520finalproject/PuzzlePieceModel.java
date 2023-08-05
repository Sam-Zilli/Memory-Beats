package edu.northeastern.cs5520finalproject;

public class PuzzlePieceModel {
    // pieceId will be the Key; pieceNumber will be the value
    private int pieceId;
    private int pieceNumber;

    public PuzzlePieceModel(int pieceId, int pieceNumber) {
        this.pieceId = pieceId;
        this.pieceNumber = pieceNumber;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }
}
