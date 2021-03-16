package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class PatientsMedicines extends AppCompatActivity implements View.OnClickListener {

    private TextView shopName21, shopProducts21, shopOrders21, filterProductsRv;
    private EditText searchProduct21;
    private ImageButton shoppingCart, backMain21, filterProductBtn21;
    private RelativeLayout productsR2, ordersT1;
    private RecyclerView productsTv;

    public String deliveryFee;
    private String shopUid;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private ArrayList<PatientProductsModel> productsList2;
    private PatientProductAdapter patientProductAdapter;

    private ArrayList<ModelCartItem> cartItemsList;
    private AdapterCartItem adapterCartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_medicines);

        backMain21 = findViewById(R.id.backMain21);
        shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setOnClickListener(this);
        shopProducts21 = findViewById(R.id.shopProducts21);
        filterProductsRv = findViewById(R.id.filterProductsRv);
        searchProduct21 = findViewById(R.id.searchProduct21);
        filterProductBtn21 = findViewById(R.id.filterProductBtn21);
        productsTv = findViewById(R.id.productsTv);
        shopOrders21 = findViewById(R.id.shopOrders21);
        productsR2 = findViewById(R.id.productsR2);
        ordersT1 = findViewById(R.id.ordersT1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        loadAllProducts();

        showProductsUI();

        deleteCartData();

        searchProduct21.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    patientProductAdapter.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        backMain21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        shopProducts21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsUI();
            }
        });

        shopOrders21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersUI();
            }
        });
        filterProductBtn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientsMedicines.this);
                builder.setTitle("Choose Category:")
                        .setItems(Constants.productCategories2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected = Constants.productCategories2[which];
                                filterProductsRv.setText(selected);
                                if (selected.equals("All")) {
                                    loadAllProducts();
                                } else {
                                    loadFilteredProducts(selected);
                                }
                            }
                        })
                        .show();
            }
        });

    }


    private void deleteCartData() {
        EasyDB easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        easyDB.deleteAllDataFromTable();
    }


    public double allTotalPrice = 0.00;
    public TextView sTotalTv, sFeeTv, allTotalPriceTv;

    private void showCartDialog() {

        cartItemsList = new ArrayList<>();

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart, null);

        TextView shopNameTv = view.findViewById(R.id.shopNameTV);
        RecyclerView cartItemsRv = view.findViewById(R.id.cartItemsRv);
        sTotalTv = view.findViewById(R.id.sTotalTv);
        sFeeTv = view.findViewById(R.id.sFeeTv);
        allTotalPriceTv = view.findViewById(R.id.totalTv);
        Button checkoutBtn = view.findViewById(R.id.checkoutBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);


        EasyDB easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            String id = res.getString(1);
            String pId = res.getString(2);
            String name = res.getString(3);
            String price = res.getString(4);
            String cost = res.getString(5);
            String quantity = res.getString(6);

            allTotalPrice = allTotalPrice + Double.parseDouble(cost);

            ModelCartItem modelCartItem = new ModelCartItem(
                    "" + id,
                    "" + pId,
                    "" + name,
                    "" + price,
                    "" + cost,
                    "" + quantity
            );

            cartItemsList.add(modelCartItem);
        }

        adapterCartItem = new AdapterCartItem(this, cartItemsList);

        cartItemsRv.setAdapter(adapterCartItem);

        sFeeTv.setText("$" + deliveryFee);
        sTotalTv.setText("$" + String.format("%.2f", allTotalPrice));
        allTotalPriceTv.setText("$" + (allTotalPrice ));

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                allTotalPrice = 0.00;
            }
        });

    }

    private void loadFilteredProducts(String selected) {
        productsList2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productsList2.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            String productCategory = "" + ds.child("productCategory").getValue();

                            if (selected.equals(productCategory)) {
                                PatientProductsModel patientProductsModel = ds.getValue(PatientProductsModel.class);
                                productsList2.add(patientProductsModel);
                            }
                        }
                        patientProductAdapter = new PatientProductAdapter(PatientsMedicines.this, productsList2);

                        productsTv.setAdapter(patientProductAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadAllProducts() {
        productsList2 = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productsList2.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            PatientProductsModel patientProductsModel = ds.getValue(PatientProductsModel.class);
                            productsList2.add(patientProductsModel);
                        }
                        patientProductAdapter = new PatientProductAdapter(PatientsMedicines.this, productsList2);

                        productsTv.setAdapter(patientProductAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showProductsUI() {
        productsR2.setVisibility(View.VISIBLE);
        ordersT1.setVisibility(View.GONE);

        shopProducts21.setTextColor(getResources().getColor(R.color.colorBlack));
        shopProducts21.setBackgroundResource(R.drawable.shape_react_004);

        shopOrders21.setTextColor(getResources().getColor(R.color.colorWhite));
        shopOrders21.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }

    private void showOrdersUI() {
        productsR2.setVisibility(View.GONE);
        ordersT1.setVisibility(View.VISIBLE);

        shopProducts21.setTextColor(getResources().getColor(R.color.colorWhite));
        shopProducts21.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        shopOrders21.setTextColor(getResources().getColor(R.color.colorBlack));
        shopOrders21.setBackgroundResource(R.drawable.shape_react_004);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoppingCart:
                showCartDialog();
        }
    };
}