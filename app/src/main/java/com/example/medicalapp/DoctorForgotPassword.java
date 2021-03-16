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

public class DoctorForgotPassword extends AppCompatActivity {


    private EditText editTextEmail5;
    private Button Reset;
    private ProgressBar progressBar8;

    FirebaseAuth auth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_forgot_password);


            Reset = (Button) findViewById(R.id.resetBtn2);
            editTextEmail5 = (EditText) findViewById(R.id.email9);
            progressBar8 = (ProgressBar) findViewById(R.id.progressBar9);

            auth2 = FirebaseAuth.getInstance();

            Reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Reset();
                }
            });

        }

        private void Reset(){
            String email9 = editTextEmail5.getText().toString().trim();

            if(email9.isEmpty()){
                editTextEmail5.setError("Email is required");
                editTextEmail5.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email9).matches()){
                editTextEmail5.setError("Please provide valid email");
                editTextEmail5.requestFocus();
                return;
            }
            progressBar8.setVisibility(View.VISIBLE);
            auth2.sendPasswordResetEmail(email9).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {
                        Toast.makeText(DoctorForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(DoctorForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
