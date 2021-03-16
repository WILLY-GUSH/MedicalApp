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

public class DoctorLogin extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn3;
    private TextView registerItem2, editTextRegister, getEditTextForgotPassword3;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        loginBtn3 = (Button) findViewById(R.id.loginBtn3);
        loginBtn3.setOnClickListener(this);

        editTextRegister = (TextView) findViewById(R.id.registerNumber5);
        registerItem2 = (TextView) findViewById(R.id.registerItem3);
        registerItem2.setOnClickListener(this);
        getEditTextForgotPassword3 = (TextView) findViewById(R.id.forgotPassword3);
        getEditTextForgotPassword3.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email6);
        editTextPassword = (EditText) findViewById(R.id.password6);

        progressBar = (ProgressBar) findViewById(R.id.progressBar5);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerItem3:
                startActivity(new Intent(this, DoctorRegister.class));
                break;
            case R.id.forgotPassword3:
                startActivity(new Intent(this, DoctorForgotPassword.class));
                break;
            case R.id.loginBtn3:
                loginBtn3();
                break;
        }
    }

    private void loginBtn3(){
        String email6 = editTextEmail.getText().toString().trim();
        String password6 = editTextPassword.getText().toString().trim();
        String registerNumber5 = editTextRegister.getText().toString().trim();

        if(email6.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email6).matches()){
            editTextEmail.setError("Please provide a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password6.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password6.length() <6) {
            editTextPassword.setError("Minimum password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        if (registerNumber5.isEmpty()){
            editTextRegister.setError("Register number required");
            editTextRegister.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email6, password6).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(DoctorLogin.this, "Login Successful", Toast.LENGTH_LONG).show();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(DoctorLogin.this, DoctorMainPage.class));
                        progressBar.setVisibility(View.GONE);

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(DoctorLogin.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(DoctorLogin.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
    }
}