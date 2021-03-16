package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientsShowShops extends AppCompatActivity {

    private TextView patientName, shops, patientOrders;
    private ImageButton editPatientProfile, backPatientMain;
    private RelativeLayout shopRl, ordersRl;
    private RecyclerView shopRV;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<ModelShop> shopList;
    private AdapterShop adapterShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_show_shops);

        patientName = findViewById(R.id.patientName);
        shops = findViewById(R.id.shops);
        patientOrders = findViewById(R.id.patientOrders);
        editPatientProfile = findViewById(R.id.editPatientProfile);
        backPatientMain = findViewById(R.id.backPatientMain);
        shopRl = findViewById(R.id.shopRl);
        ordersRl = findViewById(R.id.ordersRl);
        shopRV = findViewById(R.id.shopRV);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        checkUser();

        showShopsUI();

        showOrdersUI();

        backPatientMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showShopsUI();
            }
        });

        patientOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOrdersUI();
            }
        });

    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(PatientsShowShops.this, PatientLogin.class));
            finish();
        }
        else {
            loadMyInfo();

        }
    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("userName").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            String name = ""+ds.child("name").getValue();
                            String myCity = ""+ds.child("city").getValue();

                            patientName.setText(name);
                            try {

                            }
                            catch (Exception e){

                            }

                            loadShops();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadShops() {

        shopList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("accountType").equalTo("Seller")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        shopList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ModelShop modelShop = ds.getValue(ModelShop.class);
                            shopList.add(modelShop);
                        }

                        adapterShop = new AdapterShop(PatientsShowShops.this, shopList);
                        shopRV.setAdapter(adapterShop);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showShopsUI() {
        shopRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        shops.setTextColor(getResources().getColor(R.color.colorBlack));
        shops.setBackgroundResource(R.drawable.shape_react_004);

        patientOrders.setTextColor(getResources().getColor(R.color.colorWhite));
        patientOrders.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showOrdersUI() {
        shopRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);

        shops.setTextColor(getResources().getColor(R.color.colorWhite));
        shops.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        patientOrders.setTextColor(getResources().getColor(R.color.colorBlack));
        patientOrders.setBackgroundResource(R.drawable.shape_react_004);
    }
}