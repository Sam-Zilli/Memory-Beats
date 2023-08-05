package edu.northeastern.cs5520finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // database name and version
    private static final String DATABASE_NAME = "puzzle_database.db";
    private static final int DATABASE_VERSION = 1;

    // schema for puzzle pieces table
    private static final String TABLE_PUZZLE_PIECES = "PuzzlePieces";
    private static final String COLUMN_PIECE_ID = "piece_id";
    private static final String COLUMN_PIECE_NUMBER = "piece_number";

    // schema for complete puzzle table
    private static final String TABLE_COMPLETED_PUZZLE = "PuzzlePieces";
    private static final String COLUMN_PUZZLE_ID = "puzzle_id";

    // Maybe change for data completed?
    private static final String COLUMN_PUZZLE_NUMBER = "puzzle_number";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create puzzle pieces table
        String createPuzzlePiecesEntry = "CREATE TABLE " + TABLE_PUZZLE_PIECES + " (" +
                COLUMN_PIECE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PIECE_NUMBER + " INTEGER)";
        db.execSQL(createPuzzlePiecesEntry);

        // Create complete puzzle table
        String createCompletePuzzleEntry = "CREATE TABLE " + TABLE_COMPLETED_PUZZLE + " (" +
                COLUMN_PUZZLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PUZZLE_NUMBER + " INTEGER)";
        db.execSQL(createCompletePuzzleEntry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Method to insert data into puzzle piece table
    public boolean insertPuzzlePiece(int pieceNumber) {
        // instantiate dp helper
//        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create new map of values.  Column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIECE_NUMBER, pieceNumber);
        long result = db.insert(TABLE_PUZZLE_PIECES, null, values);

        // returns -1 if not inserted successfully
        return result != -1;
    }
}
