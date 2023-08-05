package edu.northeastern.cs5520finalproject;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserLevel.class}, version = 1, exportSchema = false)

public abstract class UserLevelRoomDatabase extends RoomDatabase {

    public abstract UserLevelDao userLevelDao();

    private static volatile UserLevelRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Returns the singleton
    static UserLevelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserLevelRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserLevelRoomDatabase.class, "UserLevelDatabase")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserLevelDao dao = INSTANCE.userLevelDao();
                dao.deleteAll();

                UserLevel userLevel = new UserLevel(1);
                dao.insert(userLevel);
                userLevel = new UserLevel(7);
                dao.insert(userLevel);
            });
        }
    };
}
