package com.example.medicalapp;

import android.widget.Filter;

import java.util.ArrayList;

public class PharmacyFilterProduct extends Filter {

    private PharmacyProductAdapter adapter;
    private ArrayList<PharmacyProductsModel> filterList;

    public PharmacyFilterProduct(PharmacyProductAdapter adapter, ArrayList<PharmacyProductsModel> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){

            constraint = constraint.toString().toUpperCase();

            ArrayList<PharmacyProductsModel> filteredModels = new ArrayList<>();
            for (int i =0; i<filterList.size(); i++){
                if (filterList.get(i).getProductTitle().toUpperCase().contains(constraint) ||
                        filterList.get(i).getProductCategory().toUpperCase().contains(constraint)){

                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }


    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.productsList = (ArrayList<PharmacyProductsModel>) results.values;
        adapter.notifyDataSetChanged();
    }
}
