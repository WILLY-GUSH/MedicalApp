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

public class PharmacyRegister extends AppCompatActivity implements View.OnClickListener{

    private Button registerUser2;
    private TextView backToLogin2;
    private EditText editTextUserName2, editTextRegister2, editTextPassword2, editTextEmail2;
    private ProgressBar progressBar2;
    private FirebaseAuth mAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_register);

        registerUser2 = (Button) findViewById(R.id.btnLogin2);
        registerUser2.setOnClickListener(this);

        editTextUserName2 = (EditText) findViewById(R.id.userName2);
        editTextRegister2 = (EditText) findViewById(R.id.registerNumber2);
        editTextPassword2 = (EditText) findViewById(R.id.password2);
        editTextEmail2 = (EditText) findViewById(R.id.email2);

        backToLogin2 = (TextView) findViewById(R.id.backLogin2);
        backToLogin2.setOnClickListener(this);


        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mAuth2 = FirebaseAuth.getInstance();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backLogin2:
                startActivity(new Intent(this, PharmacyLogin.class));
                break;
            case R.id.btnLogin2:
                btnLogin2();

        }
    }

    private void btnLogin2() {
        String email2 = editTextEmail2.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        String userName2 = editTextUserName2.getText().toString().trim();
        String registerNumber2 = editTextRegister2.getText().toString().trim();

        if (userName2.isEmpty()){
            editTextUserName2.setError("Fullname is required");
            editTextUserName2.requestFocus();
            return;
        }

        if (registerNumber2.isEmpty()){
            editTextRegister2.setError("Register number required");
            editTextRegister2.requestFocus();
            return;
        }
        if (email2.isEmpty()){
            editTextEmail2.setError("email is required");
            editTextEmail2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
            editTextEmail2.setError("Please provide a valid email");
            return;
        }
        if (password2.isEmpty()){
            editTextPassword2.setError("Password is required");
            editTextPassword2.requestFocus();
            return;
        }
        if (password2.length() <6){
            editTextPassword2.setError("Minimum password length should be 6 characters");
            editTextPassword2.requestFocus();
            return;
        }
        progressBar2.setVisibility(View.VISIBLE);
        mAuth2.createUserWithEmailAndPassword(email2, password2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User2 user = new User2(userName2, registerNumber2, email2);
                            FirebaseDatabase.getInstance().getReference("Users2")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(PharmacyRegister.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), PharmacyMainPage.class));
                                        progressBar2.setVisibility(View.GONE);

                                        //Redirect to login Page
                                    }
                                    else{
                                        Toast.makeText(PharmacyRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(PharmacyRegister.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                        }

                });

    }
}