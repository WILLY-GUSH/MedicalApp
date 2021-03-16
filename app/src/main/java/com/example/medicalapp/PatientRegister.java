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
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegister extends AppCompatActivity implements View.OnClickListener{

        private Button registerUser3;
        private TextView backToLogin3;
        private EditText editTextUserName3, editTextPassword3, editTextEmail3;
        private ProgressBar progressBar3;
        private FirebaseAuth fAuth3;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient_register);

            registerUser3 = (Button) findViewById(R.id.btnLogin3);
            registerUser3.setOnClickListener(this);

            editTextUserName3 = (EditText) findViewById(R.id.userName3);
            editTextPassword3 = (EditText) findViewById(R.id.password3);
            editTextEmail3 = (EditText) findViewById(R.id.email3);

            backToLogin3 = (TextView) findViewById(R.id.backLogin3);
            backToLogin3.setOnClickListener(this);


            progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
            fAuth3 = FirebaseAuth.getInstance();
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backLogin3:
                    startActivity(new Intent(this, PatientLogin.class));
                    break;
                case R.id.btnLogin3:
                    btnLogin3();

            }
        }

        private void btnLogin3() {
            String email3 = editTextEmail3.getText().toString().trim();
            String password3 = editTextPassword3.getText().toString().trim();
            String userName3 = editTextUserName3.getText().toString().trim();


            if (userName3.isEmpty()){
                editTextUserName3.setError("Fullname is required");
                editTextUserName3.requestFocus();
                return;
            }

            if (email3.isEmpty()){
                editTextEmail3.setError("email is required");
                editTextEmail3.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email3).matches()){
                editTextEmail3.setError("Please provide a valid email");
                return;
            }
            if (password3.isEmpty()){
                editTextPassword3.setError("Password is required");
                editTextPassword3.requestFocus();
                return;
            }
            if (password3.length() <6){
                editTextPassword3.setError("Minimum password length should be 6 characters");
                editTextPassword3.requestFocus();
                return;
            }
            progressBar3.setVisibility(View.VISIBLE);
            fAuth3.createUserWithEmailAndPassword(email3, password3)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User3 user = new User3(userName3, email3);
                                FirebaseDatabase.getInstance().getReference("Users3")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(com.example.medicalapp.PatientRegister.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), PatientMainPage.class));
                                            progressBar3.setVisibility(View.GONE);

                                            //Redirect to login Page
                                        } else {
                                            Toast.makeText(com.example.medicalapp.PatientRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                            progressBar3.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }else{
                                    Toast.makeText(PatientRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                    progressBar3.setVisibility(View.GONE);
                                }
                            }
                        });

        }
    }