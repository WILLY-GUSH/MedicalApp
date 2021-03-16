package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PharmacyProfileDetails extends AppCompatActivity  {

    private TextView shopName, shopProducts, shopOrders, filterProductsTv;
    private EditText searchProduct;
    private ImageButton editProfile, backMain, filterProductBtn;
    private RelativeLayout productsR1, ordersR1;
    private RecyclerView productsRv;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private ArrayList<PharmacyProductsModel> productsList;
    private PharmacyProductAdapter pharmacyProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_profile_details);


        backMain = findViewById(R.id.backMain);
        editProfile = findViewById(R.id.editProfile);
        shopProducts = findViewById(R.id.shopProducts);
        filterProductsTv = findViewById(R.id.filterProductsTv);
        searchProduct = findViewById(R.id.searchProduct);
        filterProductBtn = findViewById(R.id.filterProductBtn);
        productsRv = findViewById(R.id.productsRv);
        shopOrders = findViewById(R.id.shopOrders);
        productsR1 = findViewById(R.id.productsR1);
        ordersR1 = findViewById(R.id.ordersR1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        loadAllProducts();

        showProductsUI();



        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    pharmacyProductAdapter.getFilter().filter(s);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PharmacyProfileDetails.this, EditPharmacyProfile.class));

            }
        });

        shopProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsUI();
            }
        });

        shopOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersUI();
            }
        });
        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyProfileDetails.this);
                builder.setTitle("Choose Category:")
                        .setItems(Constants.productCategories2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected = Constants.productCategories2[which];
                                filterProductsTv.setText(selected);
                                if (selected.equals("All")){
                                    loadAllProducts();
                                }
                                else{
                                    loadFilteredProducts(selected);
                                }
                            }
                        })
                .show();
            }
        });

    }

    private void loadFilteredProducts(String selected) {
        productsList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productsList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){

                            String productCategory = ""+ds.child("productCategory").getValue();

                            if (selected.equals(productCategory)){
                                PharmacyProductsModel pharmacyProductsModel = ds.getValue(PharmacyProductsModel.class);
                                productsList.add(pharmacyProductsModel);
                            }
                        }
                        pharmacyProductAdapter = new PharmacyProductAdapter(PharmacyProfileDetails.this, productsList);

                        productsRv.setAdapter(pharmacyProductAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadAllProducts() {
        productsList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productsList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            PharmacyProductsModel pharmacyProductsModel = ds.getValue(PharmacyProductsModel.class);
                            productsList.add(pharmacyProductsModel);
                        }
                        pharmacyProductAdapter = new PharmacyProductAdapter(PharmacyProfileDetails.this, productsList);

                        productsRv.setAdapter(pharmacyProductAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showProductsUI() {
        productsR1.setVisibility(View.VISIBLE);
        ordersR1.setVisibility(View.GONE);

        shopProducts.setTextColor(getResources().getColor(R.color.colorBlack));
        shopProducts.setBackgroundResource(R.drawable.shape_react_004);

        shopOrders.setTextColor(getResources().getColor(R.color.colorWhite));
        shopOrders.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }

    private void showOrdersUI() {
        productsR1.setVisibility(View.GONE);
        ordersR1.setVisibility(View.VISIBLE);

        shopProducts.setTextColor(getResources().getColor(R.color.colorWhite));
        shopProducts.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        shopOrders.setTextColor(getResources().getColor(R.color.colorBlack));
        shopOrders.setBackgroundResource(R.drawable.shape_react_004);
    }




}