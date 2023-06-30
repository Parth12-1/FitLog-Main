package com.example.fitlog.ui.foodhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodhistoryViewModel extends ViewModel {//done

    private final MutableLiveData<String> mText;

    public FoodhistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodhistory fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}