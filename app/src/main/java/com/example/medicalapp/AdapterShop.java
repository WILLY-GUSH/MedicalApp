package com.example.medicalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.HolderShop>{

    private Context context;
    public ArrayList<ModelShop> shopList;

    public AdapterShop(Context context, ArrayList<ModelShop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }


    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_shop, parent, false);
        return new HolderShop(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        ModelShop modelShop = shopList.get(position);
        String address = modelShop.getAddress();
        String accountType = modelShop.getAccountType();
        String city = modelShop.getCity();
        String country = modelShop.getCountry();
        String deliveryFee = modelShop.getDeliveryFee();
        String email = modelShop.getEmail();
        String latitude = modelShop.getLatitude();
        String longitude = modelShop.getLongitude();
        String shopOpen = modelShop.getShopOpen();
        String name = modelShop.getName();
        String phone = modelShop.getPhone();
        String online = modelShop.getOnline();
        String uid = modelShop.getUid();
        String timestamp = modelShop.getTimestamp();
        String state = modelShop.getState();
        String imageShop = modelShop.getImageShop();
        String shopName = modelShop.getShopName();

        holder.shopNameTV.setText(shopName);
        holder.phoneTv.setText(phone);
        holder.addressTv.setText(address);

        if (online.equals("true")){
            holder.onlineIv.setVisibility(View.VISIBLE);
        }
        else {
            holder.onlineIv.setVisibility(View.GONE);
        }
        if (shopOpen.equals("true")){

            holder.shopClosedTv.setVisibility(View.GONE);
        }
        else {
            holder.shopClosedTv.setVisibility(View.VISIBLE);
        }
        try {
            Picasso.get().load(imageShop).placeholder(R.drawable.ic_store_24).into(holder.shopIv);
        }
        catch (Exception e){
            holder.shopIv.setImageResource(R.drawable.ic_store_24);
        }
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class HolderShop extends RecyclerView.ViewHolder {

        private ImageView shopIv, onlineIv, nextTv;
        private TextView shopClosedTv, shopNameTV, phoneTv, addressTv;
        private RatingBar ratingBar;

        public HolderShop(@NonNull View itemView) {
            super(itemView);

            shopIv = itemView.findViewById(R.id.shopIv);
            onlineIv = itemView.findViewById(R.id.onlineIv);
            nextTv = itemView.findViewById(R.id.nextTv);
            shopClosedTv = itemView.findViewById(R.id.shopClosedTv);
            shopNameTV = itemView.findViewById(R.id.shopNameTV);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            addressTv = itemView.findViewById(R.id.addressTv);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

    }

}
