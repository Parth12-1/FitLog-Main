package com.example.fitlog.ui.schedule;

import static java.time.temporal.ChronoUnit.DAYS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitlog.R;
import com.example.fitlog.databinding.FragmentScheduleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ScheduleFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String UID;
    private DatabaseReference mDatabase;
    private String SDate;
    private Boolean regimene = true;
    private String Muscle;
    private Date d2 = new Date();
    private Date d1 = new Date();

    private FragmentScheduleBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ScheduleViewModel scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = formatter.parse("1991-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSchedule;
        scheduleViewModel.getmText().observe(getViewLifecycleOwner(), textView::setText);


        final TextView textView1 = binding.muscletxt;
        scheduleViewModel.getniceText().observe(getViewLifecycleOwner(), textView1::setText);


        final Switch switchSched = binding.splitSwitch;
        switchSched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                regimene = b;
                if (b){
                    mDatabase.child("users").child(UID).child("scheduleUser").child("Wsplit").setValue("True");
                }
                else{
                    mDatabase.child("users").child(UID).child("scheduleUser").child("Wsplit").setValue("False");
                }
            }
        });


        final CalendarView caldatev = binding.calenderv;
        caldatev.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month++;
                //System.out.println(month + "/" + dayOfMonth +"/" + year);
                String date_string = month + "-" + dayOfMonth + "-" + year;
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    d2 = formatter.parse(date_string);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(d2.toString() + "d2");
                AfterFbase();
            }
        });


        //Do dumb code like if the user said they did nothing and it was push day, make the day they did nothing by name, but make the start date in firebase 3 days ago
        //Object task = scheduleViewModel.getStartDate().getValue();
        //System.out.println("HERE");
        //System.out.println(task.toString() + "hitta"); Doesnt work

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        UID = user.getUid();

        mDatabase.child("users")
                .child(UID)
                .child("scheduleUser").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        //.setValue(task.getResult().getValue());
                        SDate = task.getResult().child("startDate").getValue().toString();
                        //System.out.println(SDate + "GETTER");
                        if (task.getResult().child("Wsplit").getValue().toString() == "true"){
                            regimene = true;
                        }
                        else{
                            regimene = false;
                        }
                         Muscle = task.getResult().child("muscle").getValue().toString();
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            d1 = formatter.parse(SDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //System.out.println(d1.toString() + "d1");
                    }
                });


        final Button pushdia = binding.pushbtn;
        pushdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("push");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //String currentdate = formatter.format(d2);
                LocalDate currentdate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                mDatabase.child("users").child(UID).child("scheduleUser").child("startDate").setValue(currentdate.toString());
                try {
                    d1 = formatter.parse(currentdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(d1.toString() + "d1");
                AfterFbase();
            }
        });

        final Button pulldia = binding.pullbtn;
        pulldia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("push");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //String currentdate = formatter.format(d2);
                LocalDate currentdate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);
                mDatabase.child("users").child(UID).child("scheduleUser").child("startDate").setValue(currentdate.toString());
                try {
                    d1 = formatter.parse(currentdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(d1.toString() + "d1");
                AfterFbase();
            }
        });

        final Button legdia = binding.legbtn;
        legdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("push");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //String currentdate = formatter.format(d2);
                LocalDate currentdate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(2);
                mDatabase.child("users").child(UID).child("scheduleUser").child("startDate").setValue(currentdate.toString());
                try {
                    d1 = formatter.parse(currentdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(d1.toString() + "d1");
                AfterFbase();
            }
        });

        final Button restdia = binding.restbtn;
        restdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("push");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //String currentdate = formatter.format(d2);
                LocalDate currentdate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(3);
                mDatabase.child("users").child(UID).child("scheduleUser").child("startDate").setValue(currentdate.toString());
                try {
                    d1 = formatter.parse(currentdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(d1.toString() + "d1");
                AfterFbase();
            }
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void AfterFbase(){  //////////WORK ON NEGATIVES AND CHANGING THE BUTTON PRESS TO CHANGE THE DAY //////////////////////////////////////////////////////////////////////////////
        long difference = d2.getTime() - d1.getTime();
        float daysBetween = (difference / (1000*60*60*24));
        final TextView textView1 = binding.muscletxt;
        if (regimene == true){ // adaptive
            double adapmod = daysBetween%4;
            adapmod = Math.abs(adapmod);
            System.out.println(daysBetween%4);
            if (adapmod == 0){
                textView1.setText("PUSH DAY (Chest Shoulders & Triceps)");
            }
            else if (adapmod == 1){
                textView1.setText("PULL DAY (Bicep Back & Traps)");
            }
            else if (adapmod == 2){
                textView1.setText("LEG DAY (Legs & Abs)");
            }
            else{
                textView1.setText("REST DAY (or Cardio)");
            }
        }
        else{
            System.out.println(daysBetween%14);
            double weekmod = daysBetween%14;
            weekmod = Math.abs(weekmod);
            if (weekmod == 0){
                textView1.setText("HEAVY PUSH DAY (Chest Shoulders & Triceps)");
            }
            else if (weekmod == 1){
                textView1.setText("HEAVY PULL DAY (Bicep Back & Traps)");
            }
            else if (weekmod == 2){
                textView1.setText("HEAVY LEG DAY (Legs & Abs)");
            }
            else if (weekmod == 3){
                textView1.setText("REST DAY (or Cardio)");
            }
            else if (weekmod == 4){
                textView1.setText("HIGH REPS PUSH DAY (Chest Shoulders & Triceps)");
            }
            else if (weekmod == 5){
                textView1.setText("HIGH REPS PULL DAY (Bicep Back & Traps)");
            }
            else if (weekmod == 6){
                textView1.setText("HIGH REPS LEG DAY (Legs & Abs)");
            }

        }
    }
}



