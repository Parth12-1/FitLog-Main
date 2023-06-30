package com.example.fitlog.ui.exercisehistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExercisehistoryViewModel extends ViewModel { //done

    private final MutableLiveData<String> mText;

    public ExercisehistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Exercisehistory fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}