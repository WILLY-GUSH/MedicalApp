package com.example.medicalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PharmacyMainPage extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout pharmacyPage;
    private Button ProductsAndOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_main_page);

        pharmacyPage = findViewById(R.id.Pharmacy_Page);
        ProductsAndOrders = findViewById(R.id.ProductsAndOrders12);
        ProductsAndOrders.setOnClickListener(this);


    }

    public void ClickMenu(View view) {

        openDrawer(pharmacyPage);
    }

    private static void openDrawer(DrawerLayout pharmacyPage) {

        pharmacyPage.openDrawer(GravityCompat.START);
    }

    public void ClickAddCart(View view){

        redirectActivity(this, PharmacyAddProducts.class);
    }

    public void ClickLogo(View view) {

        closeDrawer(pharmacyPage);
    }

    private static void closeDrawer(DrawerLayout pharmacyPage) {

        if (pharmacyPage.isDrawerOpen(GravityCompat.START)) {

            pharmacyPage.closeDrawer(GravityCompat.START);
        }
    }

        public void ClickHome(View view){

        recreate();
        }

    public void ClickProfile(View view){

        redirectActivity(this, PharmacyProfileDetails.class);
    }

    public void ClickAboutUs(View view){

        redirectActivity(this, AboutUs.class);

    }


    public void ClickLogout(View view){

        logout(this, PharmacyLogin.class);

    }

    private static void logout(Activity activity, Class<PharmacyLogin> pharmacyLoginClass) {

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


    private static void redirectActivity(Activity activity,Class aClass) {

        Intent intent = new Intent(activity, aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(pharmacyPage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ProductsAndOrders12:
                redirectActivity(this, PharmacyProfileDetails.class);
        }
    }
}


