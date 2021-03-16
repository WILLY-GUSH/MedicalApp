package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientLogin extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn2;
    private TextView registerItem, getEditTextForgotPassword2;
    private EditText editTextEmail3, editTextPassword3;

    private FirebaseAuth mAuth3;
    private ProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        loginBtn2 = (Button) findViewById(R.id.loginBtn2);
        loginBtn2.setOnClickListener(this);

        registerItem = (TextView) findViewById(R.id.registerItem);
        registerItem.setOnClickListener(this);
        getEditTextForgotPassword2 = (TextView) findViewById(R.id.forgotPassword2);
        getEditTextForgotPassword2.setOnClickListener(this);

        editTextEmail3 = (EditText) findViewById(R.id.email5);
        editTextPassword3 = (EditText) findViewById(R.id.password5);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar6);

        mAuth3 = FirebaseAuth.getInstance();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerItem:
                startActivity(new Intent(this, PatientRegister.class));
                break;
            case R.id.forgotPassword2:
                startActivity(new Intent(this, PatientForgotPassword.class));
                break;
            case R.id.loginBtn2:
                loginBtn2();
                break;
        }
    }

    private void loginBtn2(){
        String email5 = editTextEmail3.getText().toString().trim();
        String password5 = editTextPassword3.getText().toString().trim();


        if(email5.isEmpty()){
            editTextEmail3.setError("Email is required!");
            editTextEmail3.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email5).matches()){
            editTextEmail3.setError("Please provide a valid email");
            editTextEmail3.requestFocus();
            return;
        }

        if (password5.isEmpty()){
            editTextPassword3.setError("Password is required");
            editTextPassword3.requestFocus();
            return;
        }

        if (password5.length() <6) {
            editTextPassword3.setError("Minimum password length should be 6 characters");
            editTextPassword3.requestFocus();
            return;
        }

        progressBar3.setVisibility(View.VISIBLE);
        mAuth3.signInWithEmailAndPassword(email5, password5).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(PatientLogin.this, "Login Successful", Toast.LENGTH_LONG).show();

                if (user.isEmailVerified()){
                    startActivity(new Intent(PatientLogin.this, PatientMainPage.class));
                    progressBar3.setVisibility(View.GONE);

                }else {
                    user.sendEmailVerification();
                    Toast.makeText(PatientLogin.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                }


            }else {
                Toast.makeText(PatientLogin.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                progressBar3.setVisibility(View.GONE);
            }
            }

        });
    }
}