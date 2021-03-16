package com.example.medicalapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class PharmacyProductAdapter extends RecyclerView.Adapter<PharmacyProductAdapter.HolderProductsPharmacy> implements Filterable {

    private Context context;
    public ArrayList<PharmacyProductsModel> productsList, filterList;
    private Filterable filter;


    public PharmacyProductAdapter(Context context, ArrayList<PharmacyProductsModel> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.filterList = productsList;
    }


    @NonNull
    @Override
    public HolderProductsPharmacy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_product_row, parent, false);
        return new HolderProductsPharmacy(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductsPharmacy holder, int position) {
        PharmacyProductsModel pharmacyProductsModel = productsList.get(position);
        String id = pharmacyProductsModel.getProductId();
        String uid = pharmacyProductsModel.getUid();
        String discountAvailable = pharmacyProductsModel.getDiscountAvailable();
        String discountNote = pharmacyProductsModel.getDiscountNote();
        String discountPrice = pharmacyProductsModel.getDiscountPrice();
        String productCategory = pharmacyProductsModel.getProductCategory();
        String productDescription = pharmacyProductsModel.getProductDescription();
        String originalPrice = pharmacyProductsModel.getOriginalPrice();
        String icon = pharmacyProductsModel.getProductIcon();
        String quantity = pharmacyProductsModel.getProductQuantity();
        String title = pharmacyProductsModel.getProductTitle();
        String timestamp = pharmacyProductsModel.getTimestamp();

        holder.titleTV.setText(title);
        holder.quantityTv.setText(quantity);
        holder.discountedNoteEt.setText("OFF%"+discountNote);
        holder.discountedPriceTv.setText("$"+discountPrice);
        holder.originalPriceTv.setText("$"+originalPrice);
        if (discountAvailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteEt.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.discountedPriceTv.setVisibility(View.GONE);
            holder.discountedNoteEt.setVisibility(View.GONE);
    }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.ic_blue_add_shopping_cart_24).into(holder.productIconIv);
        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_blue_add_shopping_cart_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBottomSheet(pharmacyProductsModel);
            }
        });
    }

    private void detailsBottomSheet(PharmacyProductsModel pharmacyProductsModel) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_product_detail, null);

        bottomSheetDialog.setContentView(view);

        ImageButton backPage = view.findViewById(R.id.backPage);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        ImageButton editBtn = view.findViewById(R.id.editBtn);
        TextView nameTV = view.findViewById(R.id.nameTV);
        ImageView productIconIv = view.findViewById(R.id.productIconIv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTV = view.findViewById(R.id.descriptionTV);
        TextView categoryIv = view.findViewById(R.id.categoryIv);
        TextView quantityIv = view.findViewById(R.id.quantityIv);
        TextView discountedPriceTv = view.findViewById(R.id.discountedPriceTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);

        String id = pharmacyProductsModel.getProductId();
        String uid = pharmacyProductsModel.getUid();
        String discountAvailable = pharmacyProductsModel.getDiscountAvailable();
        String discountNote = pharmacyProductsModel.getDiscountNote();
        String discountPrice = pharmacyProductsModel.getDiscountPrice();
        String productCategory = pharmacyProductsModel.getProductCategory();
        String productDescription = pharmacyProductsModel.getProductDescription();
        String originalPrice = pharmacyProductsModel.getOriginalPrice();
        String icon = pharmacyProductsModel.getProductIcon();
        String quantity = pharmacyProductsModel.getProductQuantity();
        String title = pharmacyProductsModel.getProductTitle();
        String timestamp = pharmacyProductsModel.getTimestamp();

        titleTv.setText(title);
        descriptionTV.setText(productDescription);
        categoryIv.setText(productCategory);
        quantityIv.setText(quantity);
        discountedNoteTv.setText(discountNote);
        discountedPriceTv.setText("$"+discountPrice);
        originalPriceTv.setText("$"+originalPrice);

        if (discountAvailable.equals("true")){
            discountedPriceTv.setVisibility(View.VISIBLE);
            discountedNoteTv.setVisibility(View.VISIBLE);
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            discountedPriceTv.setVisibility(View.GONE);
            discountedNoteTv.setVisibility(View.GONE);
        }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.ic_blue_add_shopping_cart_24).into(productIconIv);
        }
        catch (Exception e){
            productIconIv.setImageResource(R.drawable.ic_blue_add_shopping_cart_24);
        }

        bottomSheetDialog.show();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(context, PharmacyEditActivity.class);
                intent.putExtra("productId", id);
                context.startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                    .setMessage("Are you sure you want to delete product" +title+"?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteProduct(id);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void deleteProduct(String id) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicine");
        reference.child(firebaseAuth.getUid()).child("products").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Product deleted....", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    @Override
    public Filter getFilter() {
        if (filter==null){
            filter = (Filterable) new PharmacyFilterProduct(this, filterList);
        }
        return (Filter) filter;
    }

    class HolderProductsPharmacy extends RecyclerView.ViewHolder{

        private ImageView productIconIv, nextIv;
        private TextView titleTV, quantityTv, discountedNoteEt, discountedPriceTv, originalPriceTv;

        public HolderProductsPharmacy(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            titleTV = itemView.findViewById(R.id.titleTV);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            discountedNoteEt = itemView.findViewById(R.id.discountedNoteEt);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
        }
    }
}
