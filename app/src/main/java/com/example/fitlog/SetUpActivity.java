package com.example.fitlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SetUpActivity extends AppCompatActivity {

    private Button btnNext, btnLoss, btnMaintain, btnGain;
    private Switch switchSched;
    private TextInputEditText ageEdit, heightEdit, weightEdit, fatEdit;
    private TextView changeTxt;
    private boolean fatfilled = false;
    private boolean weightfilled = false;
    private boolean heightfilled = false;
    private Timer timerfat = new Timer();
    private Timer timerweight = new Timer();
    private Timer timerheight = new Timer();
    private Timer timerage = new Timer();
    private final long DELAY = 1000; // in ms
    private DatabaseReference mDatabase;
    private String UID;
    private FirebaseAuth mAuth;
    private String recco;
    private int clicked = 1;
    private boolean sched = false;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();


//        UserCreate user1 = new UserCreate(UID, user.getEmail());
//
//        mDatabase.child("users").child(UID).setValue(user1);




        btnNext = (Button) findViewById(R.id.sunextBTN);
        btnLoss = (Button) findViewById(R.id.losebtn);
        btnGain = (Button) findViewById(R.id.gainbtn);
        btnMaintain = (Button) findViewById(R.id.maintainbtn);
        changeTxt = (TextView) findViewById(R.id.changeTxt);
        ageEdit = (TextInputEditText) findViewById(R.id.ageEditTxt);
        heightEdit = (TextInputEditText) findViewById(R.id.heightEditTxt);
        weightEdit = (TextInputEditText) findViewById(R.id.weightEditTxt);
        fatEdit = (TextInputEditText) findViewById(R.id.fatEditTxt);
        switchSched = (Switch) findViewById(R.id.splitSwitch);

        btnNext.setClickable(false);

        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 1;
                btnMaintain.setTextColor(Color.parseColor("#FF2D00F"));
                System.out.println(clicked);
            }
        });
        btnLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 0;
                btnLoss.setTextColor(Color.parseColor("#FF2D00"));
                System.out.println(clicked);
            }
        });
        btnGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = 2;
                btnGain.setTextColor(Color.parseColor("#FF2D00"));
                System.out.println(clicked);
            }
        });


        fatEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timerfat != null)
                    timerfat.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 2) {

                    timerfat = new Timer();
                    timerfat.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //System.out.println("RUNNING");
                            fatfilled = true;//////
                            recco = recommendation();
                            //System.out.println("SENDITTTTT" + recco+ " no null");
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    changeTxt.setText(recco);
                                }
                            });

                        }

                    }, DELAY);
                }
            }
        });



        weightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timerweight != null)
                    timerweight.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 2) {

                    timerweight = new Timer();
                    timerweight.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //System.out.println("RUNNING");
                            weightfilled = true;
                            recco = recommendation();
                            //System.out.println("SENDITTTTT"+ recco+ " no null");
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    changeTxt.setText(recco);
                                }
                            });
                        }
                    }, DELAY);
                }
            }
        });

        heightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timerheight != null)
                    timerheight.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 2) {

                    timerheight = new Timer();
                    timerheight.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //System.out.println("RUNNING");
                            heightfilled = true;
                            recco = recommendation();
                            //System.out.println("SENDITTTTT"+ recco + " no null");
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    changeTxt.setText(recco);
                                }
                            });
                        }

                    }, DELAY);
                }
            }
        });


        ageEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timerage != null)
                    timerage.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 2) {

                    timerage = new Timer();
                    timerage.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //System.out.println("RUNNING");
                        }

                    }, DELAY);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });


        switchSched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sched = true;

                }
                else{
                    sched = false;

                }
            }
        });

    }







    public String recommendation(){
        if (heightfilled && weightfilled && fatfilled){

            if (Double.parseDouble(fatEdit.getText().toString()) < 14){
                return "Our recommendation is for you to gain weight due to your body fat, you could gain muscle at a fast rate without worrying about too much fat over your muscles. (bulk)";
            }
            else if (Double.parseDouble(fatEdit.getText().toString()) < 18){
                return"Our recommendation is for you to maintain weight due to your body fat, you could gain muscle at a decent rate, but do not want to gain too much fat as the time losing that fat would be counterproductive. (maintain)";
            }
            else{
                return "Our recommendation is for you to lose weight as your body fat is too high, it is recommended that you lose the fat on your body before gaining muscle. (cut)";
            }


        }
        else if (heightfilled && weightfilled){
            double h = Double.parseDouble(heightEdit.getText().toString());
            double w = Double.parseDouble(weightEdit.getText().toString());
            double bmi = (w*703)/Math.pow(h,2);
            return bmiConclusion((int)bmi);
        }
        return null;
    }

    public String bmiConclusion(int bmi){
        if (bmi < 23){
            return "Our recommendation is for you to gain weight as your BMI is " + bmi + " Which is on the lower end of the BMI scale, you could gain muscle at a fast rate without worrying about too much fat over your muscles. (bulk)";
        }
        else if (bmi < 29){
            return "Our recommendation is for you to maintain weight as your BMI is " + bmi + " Which is near the middle of the BMI scale, you could gain muscle at a decent rate, but do not want to gain too much fat as the time losing that fat would be counterproductive. (maintain)";
        }
        else{
            return "Our recommendation is for you to lose weight as your BMI is " + bmi + " Which is on the higher end of the BMI scale, you would gain muscle at a slower rate, but would be decreasing the fat on your body. (cut)";
        }
    }

    public void openHome(){
        //System.out.println(UID + " " + ageEdit.getText() + " " + heightEdit.getText() + " " + weightEdit.getText() + " " + fatEdit.getText() + " " + clicked + " " + sched);

        if (ageEdit.getText()!=null && heightEdit.getText()!=null && weightEdit.getText()!=null) {
            if (fatEdit.getText()==null){
                fatEdit.setText("0");
            }
            //ScheduleUser scheduleUser = new ScheduleUser(sched);

            UserCreate user1 = new UserCreate(UID, user.getEmail(), ageEdit.getText().toString(), heightEdit.getText().toString(), weightEdit.getText().toString(), fatEdit.getText().toString(), clicked, sched);
            mDatabase.child("users").child(UID).setValue(user1);
            Intent intent = new Intent(this, HomeDrawer.class);
            startActivity(intent);
            //mDatabase.child("Schedule").child(UID).setValue(scheduleUser);
        }
    }
}
