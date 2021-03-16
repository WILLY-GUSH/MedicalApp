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

public class PatientForgotPassword extends AppCompatActivity {


    private EditText editTextEmail6;
    private Button Reset;
    private ProgressBar progressBar9;

    FirebaseAuth auth2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forgot_password);



            Reset = (Button) findViewById(R.id.resetBtn3);
            editTextEmail6 = (EditText) findViewById(R.id.email8);
            progressBar9 = (ProgressBar) findViewById(R.id.progressBar8);

            auth2 = FirebaseAuth.getInstance();

            Reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Reset();
                }
            });

        }

        private void Reset(){
            String email7 = editTextEmail6.getText().toString().trim();

            if(email7.isEmpty()){
                editTextEmail6.setError("Email is required");
                editTextEmail6.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email7).matches()){
                editTextEmail6.setError("Please provide valid email");
                editTextEmail6.requestFocus();
                return;
            }
            progressBar9.setVisibility(View.VISIBLE);
            auth2.sendPasswordResetEmail(email7).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {
                        Toast.makeText(PatientForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(PatientForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
