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

public class PharmacyLogin extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private TextView registerItem3, editTextRegister, getEditTextForgotPassword;
    private EditText editTextEmail2, editTextPassword2;

    private FirebaseAuth mAuth2;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        editTextRegister = (TextView) findViewById(R.id.registerNumber4);
        getEditTextForgotPassword = (TextView) findViewById(R.id.forgotPassword);
        getEditTextForgotPassword.setOnClickListener(this);
        registerItem3 = (TextView) findViewById(R.id.registerItem2);
        registerItem3.setOnClickListener(this);

        editTextEmail2 = (EditText) findViewById(R.id.email4);
        editTextPassword2 = (EditText) findViewById(R.id.password4);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar4);

        mAuth2 = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerItem2:
                startActivity(new Intent(this, PharmacyRegister.class));
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, PharmacyForgotPassword.class));
                break;
            case R.id.loginBtn:
                loginBtn();
                break;
        }
    }

    private void loginBtn(){
        String email4 = editTextEmail2.getText().toString().trim();
        String password4 = editTextPassword2.getText().toString().trim();
        String registerNumber4 = editTextRegister.getText().toString().trim();

        if(email4.isEmpty()){
            editTextEmail2.setError("Email is required!");
            editTextEmail2.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email4).matches()){
            editTextEmail2.setError("Please provide a valid email");
            editTextEmail2.requestFocus();
            return;
        }

        if (password4.isEmpty()){
            editTextPassword2.setError("Password is required");
            editTextPassword2.requestFocus();
            return;
        }

        if (password4.length() <6) {
            editTextPassword2.setError("Minimum password length should be 6 characters");
            editTextPassword2.requestFocus();
            return;
        }
        if (registerNumber4.isEmpty()){
            editTextRegister.setError("Register number required");
            editTextRegister.requestFocus();
            return;
        }

        progressBar2.setVisibility(View.GONE);
        mAuth2.signInWithEmailAndPassword(email4, password4).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(PharmacyLogin.this, "Login Successful", Toast.LENGTH_LONG).show();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(PharmacyLogin.this, PharmacyMainPage.class));
                        progressBar2.setVisibility(View.GONE);

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(PharmacyLogin.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(PharmacyLogin.this, "Failed to register. Try again!",
                            Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
            }


        });
        }
}