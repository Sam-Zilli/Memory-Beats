package edu.northeastern.cs5520finalproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class UserLevelRepository {

        private UserLevelDao mLevelDao;
        private LiveData<List<UserLevel>> mAllLevels;

        UserLevelRepository(Application application) {
            UserLevelRoomDatabase db = UserLevelRoomDatabase.getDatabase(application);
            mLevelDao = db.userLevelDao();
            mAllLevels = mLevelDao.getLevelsList();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        LiveData<List<UserLevel>> getLevels() {
            return mAllLevels;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        void insert(UserLevel userLevel) {
            UserLevelRoomDatabase.databaseWriteExecutor.execute(() -> {
                mLevelDao.insert(userLevel);
            });
        }
}
