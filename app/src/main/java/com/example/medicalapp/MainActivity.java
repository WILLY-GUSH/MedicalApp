package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button pharmacyLogin2, doctorLogin2, patientLogin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pharmacyLogin2 = (Button) findViewById(R.id.pharmacyLogin);
        pharmacyLogin2.setOnClickListener(this);

        doctorLogin2 = (Button) findViewById(R.id.doctorLogin);
        doctorLogin2.setOnClickListener(this);

        patientLogin2 = (Button) findViewById(R.id.patientLogin);
        patientLogin2.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pharmacyLogin:
                startActivity(new Intent(this, PharmacyLogin.class));
                break;
            case R.id.doctorLogin:
                startActivity(new Intent(this, DoctorLogin.class));
                break;
            case R.id.patientLogin:
                startActivity(new Intent(this, PatientLogin.class));

        }
    }
}