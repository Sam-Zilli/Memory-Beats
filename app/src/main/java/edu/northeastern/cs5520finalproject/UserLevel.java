package edu.northeastern.cs5520finalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserLevel")
public class UserLevel {

    @PrimaryKey(autoGenerate = true)

    @NonNull
    @ColumnInfo(name = "UserLevel")
    private int Level;

    public UserLevel(@NonNull int Level){
        this.Level = Level;
    }

    public UserLevel(String stringExtra) {
    }

    public int getLevel() {
        return this.Level;
    }
}
