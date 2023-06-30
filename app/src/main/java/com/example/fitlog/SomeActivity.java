package com.example.fitlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitlog.ui.addexercise.AddexerciseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Locale;

public class SomeActivity extends AppCompatActivity {


    private FloatingActionButton btnAdd;
    private TextInputEditText setTxt;
    private TextInputEditText repTxt;
    private TextInputEditText weightTxt;
    private TextView exercisename;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        String name = getIntent().getStringExtra("name");
        System.out.println(name + "nameeee");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();

        btnAdd =(FloatingActionButton) findViewById(R.id.fab);
        setTxt = (TextInputEditText) findViewById(R.id.setsEditText);
        repTxt = (TextInputEditText) findViewById(R.id.repsEditTxt);
        weightTxt = (TextInputEditText) findViewById(R.id.weightEditTxt);
        exercisename = (TextView) findViewById(R.id.exerciseName);

        exercisename.setText(name.toUpperCase(Locale.ROOT));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weightTxt.length()!=0 && repTxt.length()!=0 && setTxt.length()!=0){
                    LocalDate startDate = LocalDate.now();
                    jExercise data = new jExercise(name, repTxt.getText().toString(), setTxt.getText().toString(), weightTxt.getText().toString());
                    mDatabase.child("exerciseLog").child(UID).child(startDate.toString()).child(name).setValue(data);

                    Intent intent = new Intent(SomeActivity.this, HomeDrawer.class);
                    startActivity(intent);
                }
            }
        });





    }
}