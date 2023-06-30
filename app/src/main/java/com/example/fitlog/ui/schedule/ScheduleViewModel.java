package com.example.fitlog.ui.schedule;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> niceText;
    private MutableLiveData<Object> taskstart;
    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;


    private String startDate;

    public ScheduleViewModel() {
        taskstart = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("This is schedule fragment");

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        UID = user.getUid();

        niceText = new MutableLiveData<>();
        niceText.setValue(UID);

        //Get Start Date
//        mDatabase.child("users")
//                .child(UID)
//                .child("scheduleUser").get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                taskstart.setValue(task.getResult().getValue());
//                //System.out.println(task.getResult().child("startDate").getValue() + "HITTERRRRR");
//                //Log.d("firebase", String.valueOf(task.getResult().getValue()));
//            }
//        });
    }

    public LiveData<String> getmText() {
        return mText;
    }

    public LiveData<String> getniceText(){return niceText;}

    public LiveData<Object> getStartDate(){return taskstart;}

}