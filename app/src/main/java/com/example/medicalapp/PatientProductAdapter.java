package com.example.medicalapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class PatientProductAdapter extends RecyclerView.Adapter<PatientProductAdapter.HolderProductsPatients> implements Filterable {

    private Context context2;
    public ArrayList<PatientProductsModel> productsList2, filterList2;
    private Filterable filter2;


    public PatientProductAdapter(Context context, ArrayList<PatientProductsModel> productsList2) {
        this.context2 = context;
        this.productsList2 = productsList2;
        this.filterList2 = productsList2;
    }


    @NonNull
    @Override
    public HolderProductsPatients onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context2).inflate(R.layout.patient_product_row, parent, false);
        return new HolderProductsPatients(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderProductsPatients holder, int position) {
        final PatientProductsModel patientProductsModel = productsList2.get(position);
        String id = patientProductsModel.getProductId();
        String uid = patientProductsModel.getUid();
        String discountAvailable = patientProductsModel.getDiscountAvailable();
        String discountNote = patientProductsModel.getDiscountNote();
        String discountPrice = patientProductsModel.getDiscountPrice();
        String productCategory = patientProductsModel.getProductCategory();
        String productDescription = patientProductsModel.getProductDescription();
        String originalPrice = patientProductsModel.getOriginalPrice();
        String icon = patientProductsModel.getProductIcon();
        String quantity = patientProductsModel.getProductQuantity();
        String title = patientProductsModel.getProductTitle();
        String timestamp = patientProductsModel.getTimestamp();

        holder.titleTV2.setText(title);
        holder.quantityTv2.setText(quantity);
        holder.discountedNoteEt2.setText("OFF%"+discountNote);
        holder.discountedPriceTv2.setText("$"+discountPrice);
        holder.originalPriceTv.setText("$"+originalPrice);
        if (discountAvailable.equals("true")){
            holder.discountedPriceTv2.setVisibility(View.VISIBLE);
            holder.discountedNoteEt2.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.discountedPriceTv2.setVisibility(View.GONE);
            holder.discountedNoteEt2.setVisibility(View.GONE);
        }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.ic_blue_add_shopping_cart_24).into(holder.productIconIv2);
        }
        catch (Exception e){
            holder.productIconIv2.setImageResource(R.drawable.ic_blue_add_shopping_cart_24);
        }

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuantityDialog(patientProductsModel);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBottomSheet(patientProductsModel);
            }
        });

    }

    private double cost = 0;
    private double finalCost = 0;
    private int quantity = 0;

    private void showQuantityDialog(PatientProductsModel patientProductsModel) {

        View view = LayoutInflater.from(context2).inflate(R.layout.dialog_quantity, null);

        ImageView productIconIv3 = view.findViewById(R.id.productIconIv3);
        TextView titleTv3 = view.findViewById(R.id.titleTv3);
        TextView pQuantity = view.findViewById(R.id.pQuantity);
        TextView descriptionTV3 = view.findViewById(R.id.descriptionTV3);
        TextView discountedNoteTv3 = view.findViewById(R.id.discountedNoteTv3);
        TextView originalPriceTv3 = view.findViewById(R.id.originalPriceTv3);
        TextView discountedPriceTv3 = view.findViewById(R.id.discountedPriceTv3);
        TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
        TextView quantityTv3 = view.findViewById(R.id.quantityTv3);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
        ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        final String productId = patientProductsModel.getProductId();
        String title1 = patientProductsModel.getProductTitle();
        String description = patientProductsModel.getProductDescription();
        String productQuantity = patientProductsModel.getProductQuantity();
        String discountNote = patientProductsModel.getDiscountNote();
        String image = patientProductsModel.getProductIcon();


        String price;
        if (patientProductsModel.getDiscountAvailable().equals("true")){
            price = patientProductsModel.getDiscountPrice();
            discountedNoteTv3.setVisibility(View.VISIBLE);
            originalPriceTv3.setPaintFlags(originalPriceTv3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            discountedNoteTv3.setVisibility(View.GONE);
            discountedPriceTv3.setVisibility(View.GONE);
            price = patientProductsModel.getOriginalPrice();
        }

        cost = Double.parseDouble(price.replaceAll("$",""));
        finalCost = Double.parseDouble(price.replaceAll("$",""));
        quantity = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(context2);
        builder.setView(view);



        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_blue_add_shopping_cart_24).into(productIconIv3);
        }catch (Exception e){
            productIconIv3.setImageResource(R.drawable.ic_blue_add_shopping_cart_24);
        }
        titleTv3.setText(""+title1);
        pQuantity.setText(""+productQuantity);
        descriptionTV3.setText(""+description);
        discountedNoteTv3.setText(""+discountNote);
        quantityTv3.setText(""+quantity);
        originalPriceTv3.setText("$"+patientProductsModel.getOriginalPrice());
        discountedPriceTv3.setText("$"+patientProductsModel.getDiscountPrice());
        finalPriceTv.setText("$"+finalCost);

        AlertDialog dialog = builder.create();
        dialog.show();

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + cost;
                quantity++;

                finalPriceTv.setText("$"+finalCost);
                quantityTv3.setText(""+quantity);
            }
        });
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    finalCost = finalCost - cost;
                    quantity --;

                    finalPriceTv.setText("$"+finalCost);
                    quantityTv3.setText(""+quantity);
                }
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = titleTv3.getText().toString().trim();
                String priceEach = price;
                String totalPrice = finalPriceTv.getText().toString().trim();
                String quantity = quantityTv3.getText().toString().trim();

                addCart(productId, title1,priceEach, totalPrice, quantity);
                dialog.dismiss();

            }
        });
    }

    private int itemId = 1;
    private void addCart(String productId, String title1, String priceEach, String price, String quantity) {
        itemId++;

        EasyDB easyDB = EasyDB.init(context2, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        Boolean b = easyDB.addData("Item_Id", itemId)
                .addData("Item_PID",productId)
                .addData("Item_Name",title1)
                .addData("Item_Price_Each",priceEach)
                .addData("Item_Price",price)
                .addData("Item_Quantity",quantity)
                .doneDataAdding();

        Toast.makeText(context2, "Added to cart...", Toast.LENGTH_SHORT).show();

    }


    private void detailsBottomSheet(PatientProductsModel patientProductsModel) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context2);

        View view = LayoutInflater.from(context2).inflate(R.layout.patient_product_detail, null);

        bottomSheetDialog.setContentView(view);

        ImageButton backPage23 = view.findViewById(R.id.backPage23);
        ImageButton addBTn23 = view.findViewById(R.id.addBtn23);
        TextView nameTV23 = view.findViewById(R.id.nameTV23);
        ImageView productIconIv23 = view.findViewById(R.id.productIconIv23);
        TextView discountedNoteTv23 = view.findViewById(R.id.discountedNoteTv23);
        TextView titleTv23 = view.findViewById(R.id.titleTv23);
        TextView descriptionTV23 = view.findViewById(R.id.descriptionTV23);
        TextView categoryIv23 = view.findViewById(R.id.categoryIv23);
        TextView quantityIv23 = view.findViewById(R.id.quantityIv23);
        TextView discountedPriceTv23 = view.findViewById(R.id.discountedPriceTv23);
        TextView originalPriceTv23 = view.findViewById(R.id.originalPriceTv23);

        String id = patientProductsModel.getProductId();
        String uid = patientProductsModel.getUid();
        String discountAvailable = patientProductsModel.getDiscountAvailable();
        String discountNote = patientProductsModel.getDiscountNote();
        String discountPrice = patientProductsModel.getDiscountPrice();
        String productCategory = patientProductsModel.getProductCategory();
        String productDescription = patientProductsModel.getProductDescription();
        String originalPrice = patientProductsModel.getOriginalPrice();
        String icon = patientProductsModel.getProductIcon();
        String quantity = patientProductsModel.getProductQuantity();
        String title = patientProductsModel.getProductTitle();
        String timestamp = patientProductsModel.getTimestamp();

        titleTv23.setText(title);
        descriptionTV23.setText(productDescription);
        categoryIv23.setText(productCategory);
        quantityIv23.setText(quantity);
        discountedNoteTv23.setText(discountNote);
        discountedPriceTv23.setText("$"+discountPrice);
        originalPriceTv23.setText("$"+originalPrice);

        if (discountAvailable.equals("true")){
            discountedPriceTv23.setVisibility(View.VISIBLE);
            discountedNoteTv23.setVisibility(View.VISIBLE);
            originalPriceTv23.setPaintFlags(originalPriceTv23.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            discountedPriceTv23.setVisibility(View.GONE);
            discountedNoteTv23.setVisibility(View.GONE);
        }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.ic_blue_add_shopping_cart_24).into(productIconIv23);
        }
        catch (Exception e){
            productIconIv23.setImageResource(R.drawable.ic_blue_add_shopping_cart_24);
        }

        bottomSheetDialog.show();

        addBTn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();

            }
        });



        backPage23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private String getProductId() {
        return null;
    }



    @Override
    public int getItemCount() {
        return productsList2.size();
    }


    @Override
    public Filter getFilter() {
        if (filter2==null){
            filter2 = (Filterable) new PatientFilterProduct(this, filterList2);
        }
        return (Filter) filter2;
    }

    class HolderProductsPatients extends RecyclerView.ViewHolder{

        private ImageView productIconIv2, nextIv;
        private TextView titleTV2, quantityTv2, discountedNoteEt2, discountedPriceTv2, originalPriceTv;
        private Button addBtn;

        public HolderProductsPatients(@NonNull View itemView) {
            super(itemView);

            productIconIv2 = itemView.findViewById(R.id.productIconIv2);
            addBtn = itemView.findViewById(R.id.addBtn);
            titleTV2 = itemView.findViewById(R.id.titleTV2);
            quantityTv2 = itemView.findViewById(R.id.quantityTv2);
            discountedNoteEt2 = itemView.findViewById(R.id.discountedNoteEt2);
            discountedPriceTv2 = itemView.findViewById(R.id.discountedPriceTv2);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
        }
    }


}
