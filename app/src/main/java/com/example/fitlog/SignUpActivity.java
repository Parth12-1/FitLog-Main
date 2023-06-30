package com.example.fitlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
///////
/////
////// LEFT OFF GETTING THE TEXT INPUT TO SEND TO THE FUNCTION THAT WILL SEND EMAIL AND USERNAME TO FIREBASE
//////
/////
public class SignUpActivity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnSignUp;
    private TextInputLayout email;
    private TextInputLayout password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnCancel = (Button) findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCancel();

            }
        });

        btnSignUp = (Button) findViewById(R.id.signup_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = (TextInputLayout) findViewById(R.id.emailTIL);

                password = (TextInputLayout) findViewById(R.id.passwordTIL);
                signUp();
            }
        });


    }

    public void openCancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp() {
        mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            System.out.println("SUCCESS");
                            System.out.println(user.getUid() + "SIGN IN");
                            openSetUp();
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("FAILURE");

                        }
                    }
                });
    }

    public void openSetUp(){
        Intent intent = new Intent(this, SetUpActivity.class);
        startActivity(intent);
    }


}