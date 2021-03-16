package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PharmacyForgotPassword extends AppCompatActivity {

    private EditText editTextEmail4;
    private Button Reset;
    private ProgressBar progressBar7;

    FirebaseAuth auth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_forgot_password);

        Reset = (Button) findViewById(R.id.resetBtn);
        editTextEmail4 = (EditText) findViewById(R.id.email7);
        progressBar7 = (ProgressBar) findViewById(R.id.progressBar7);

        auth2 = FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

    }

    private void Reset(){
        String email7 = editTextEmail4.getText().toString().trim();

        if(email7.isEmpty()){
            editTextEmail4.setError("Email is required");
            editTextEmail4.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email7).matches()){
            editTextEmail4.setError("Please provide valid email");
            editTextEmail4.requestFocus();
            return;
        }
        progressBar7.setVisibility(View.VISIBLE);
        auth2.sendPasswordResetEmail(email7).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(PharmacyForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(PharmacyForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}