package edu.northeastern.cs5520finalproject;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserLevelViewModel extends AndroidViewModel {

    private UserLevelRepository mRepository;

    private final LiveData<List<UserLevel>> mAllLevels;

    public UserLevelViewModel (Application application) {
        super(application);
        mRepository = new UserLevelRepository(application);
        mAllLevels = mRepository.getLevels();
    }

    LiveData<List<UserLevel>> getLevels() { return mAllLevels; }

    public void insert(UserLevel userLevel) { mRepository.insert(userLevel); }
}
