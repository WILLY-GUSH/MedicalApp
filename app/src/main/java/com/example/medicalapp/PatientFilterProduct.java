package com.example.medicalapp;

import android.widget.Filter;

import java.util.ArrayList;

public class PatientFilterProduct extends Filter {

    private PatientProductAdapter adapter2;
    private ArrayList<PatientProductsModel> filterList2;

    public PatientFilterProduct(PatientProductAdapter adapter, ArrayList<PatientProductsModel> filterList) {
        this.adapter2 = adapter;
        this.filterList2 = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){

            constraint = constraint.toString().toUpperCase();

            ArrayList<PatientProductsModel> filteredModels = new ArrayList<>();
            for (int i =0; i<filterList2.size(); i++){
                if (filterList2.get(i).getProductTitle().toUpperCase().contains(constraint) ||
                        filterList2.get(i).getProductCategory().toUpperCase().contains(constraint)){

                    filteredModels.add(filterList2.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList2.size();
            results.values = filterList2;
        }
        return results;
    }


    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter2.productsList2 = (ArrayList<PatientProductsModel>) results.values;
        adapter2.notifyDataSetChanged();
    }
}
