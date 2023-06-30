package com.example.fitlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnsignup;
    private Button btnlogin;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextInputLayout email;
    private TextInputLayout password;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //////////REMOVEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
        //Intent intent = new Intent(this, Home.class);
        //startActivity(intent);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();

        email = (TextInputLayout) findViewById(R.id.emailTIL);
        password = (TextInputLayout) findViewById(R.id.passwordTIL);
        btnsignup = (Button) findViewById(R.id.signup_button);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSignUp();
                //database.getReference("Log In/" + "January 17" ).setValue("Hello, nueva!");
            }
        });

        btnlogin = (Button) findViewById(R.id.next_button);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logIn();
            }
        });


        password.getEditText().setOnKeyListener(new View.OnKeyListener() { //////STOPS WORKING
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                System.out.println(i);
                if (i == 66){
                    logIn();
                    return true;
                }
                return false;
            }
        });

    }

    public void openSignUp(){ //WORK ON SLIDE ANIMATION
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn() {
        mAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            System.out.println("INNN");
                            System.out.println(user.getUid() + "SIGN IN");
                            openHome();
                        }
                        else{
                            System.out.println("NOPE");
                        }
                    }
                });

    }

    public void openHome(){
        Intent intent = new Intent(this, HomeDrawer.class);
        startActivity(intent);
    }


}
