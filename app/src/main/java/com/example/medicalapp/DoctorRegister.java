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

public class DoctorRegister extends AppCompatActivity implements View.OnClickListener{

    private Button registerUser;
    private TextView backToLogin;
    private EditText editTextUserName, editTextRegister, editTextPassword, editTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        registerUser = (Button) findViewById(R.id.btnLogin);
        registerUser.setOnClickListener(this);

        editTextUserName = (EditText) findViewById(R.id.userName);
        editTextRegister = (EditText) findViewById(R.id.registerNumber);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);

        backToLogin = (TextView) findViewById(R.id.backLogin);
        backToLogin.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backLogin:
                    startActivity(new Intent(this, DoctorLogin.class));
                    break;
                case R.id.btnLogin:
                    btnLogin();
            }
        }

        private void btnLogin(){
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String userName = editTextUserName.getText().toString().trim();
            String registerNumber = editTextRegister.getText().toString().trim();

            if (userName.isEmpty()){
                editTextUserName.setError("Fullname is required");
                editTextUserName.requestFocus();
                return;
            }

            if (registerNumber.isEmpty()){
                editTextRegister.setError("Register number required");
                editTextRegister.requestFocus();
                return;
            }
            if (email.isEmpty()){
                editTextEmail.setError("email is required");
                editTextEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please provide a valid email");
                return;
            }
            if (password.isEmpty()){
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
                return;
            }
            if (password.length() <6){
                editTextPassword.setError("Minimum password length should be 6 characters");
                editTextPassword.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                User user = new User(userName, registerNumber, email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(DoctorRegister.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), DoctorMainPage.class));
                                            progressBar.setVisibility(View.GONE);

                                            //Redirect to login Page
                                        }
                                        else{
                                            Toast.makeText(DoctorRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });


                            }
                            else{
                                Toast.makeText(DoctorRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

        }
    }