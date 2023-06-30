package com.example.fitlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class SomeActivity2 extends AppCompatActivity {


    private FloatingActionButton btnAdd;
    private TextInputEditText portionTxt;
    private TextView foodname, calname;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private FirebaseAuth mAuth;
    public String calfood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some2);

        String name = getIntent().getStringExtra("name");
        System.out.println(name + "nameeee");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        UID = user.getUid();

        btnAdd = (FloatingActionButton) findViewById(R.id.fab);
        portionTxt = (TextInputEditText) findViewById(R.id.portionEditTxt);
        foodname = (TextView) findViewById(R.id.FoodName);
        calname = (TextView) findViewById(R.id.CalName);

        DatabaseReference ref = mDatabase.child("foodList").child(UID).child(name)/*.child("calorie")*/;

        if (ref!=null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Iterable<DataSnapshot> x = snapshot.getChildren();
                        String bruceWayne = snapshot.child("calorie").getValue().toString();
                        System.out.println(bruceWayne);
                        calfood = bruceWayne;
                        System.out.println("INNER" + calfood);
                        System.out.println(calfood + "     call!!!!!!!!!!!!!!!!!!!");

                        calname.setText(calfood);
                        foodname.setText(name.toUpperCase(Locale.ROOT));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage() + "CRAZYYYYY");
                }
            });
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (weightTxt.length()!=0 && repTxt.length()!=0 && setTxt.length()!=0){
                if (portionTxt.length() != 0) {
                    LocalDate startDate = LocalDate.now();
                    System.out.println(calfood);

                    jFood data = new jFood(name, String.valueOf(Integer.parseInt(calfood) * Integer.parseInt(portionTxt.getText().toString())));
                    mDatabase.child("foodLog").child(UID).child(startDate.toString()).child(name).setValue(data);

                    Intent intent = new Intent(SomeActivity2.this, HomeDrawer.class);
                    startActivity(intent);
                }
            }
        });

   }
}
