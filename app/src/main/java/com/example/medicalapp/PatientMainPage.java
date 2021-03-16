package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class PatientMainPage extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    private Button AvailableChemistry, Medicines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        AvailableChemistry = findViewById(R.id.AvailableChemistry);
        AvailableChemistry.setOnClickListener(this);
        Medicines = findViewById(R.id.Medicines);
        Medicines.setOnClickListener(this);


    }


    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {

        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {

        recreate();
    }

    public void ClickProfile(View view) {

        redirectActivity(this, PatientProfileDetails.class);

    }

    public void ClickAboutUs(View view) {

        redirectActivity(this, AboutUs.class);

    }

    public void ClickLogout(View view) {

        logout(this, PatientLogin.class);

    }

    private static void logout(Activity activity, Class<PatientLogin> patientLoginClass) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("LogOut");

        builder.setMessage("Are you sure You want to Logout");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finishAffinity();

                System.exit(0);

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();

    }

    private static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity, aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AvailableChemistry:
                startActivity(new Intent(this, PatientsShowShops.class));
                break;
            case R.id.Medicines:
                startActivity(new Intent(this, PatientsMedicines.class));
        }
    }
}

