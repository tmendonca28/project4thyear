package com.tmend.firebaseauth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tmend on 12/3/2016.
 */

public class ProteinAlternativesFoodAdapter extends RecyclerView.Adapter<ProteinAlternativesFoodAdapter.FoodHolder> {

    ArrayList<Foods> filteredFoods;
    Context mContext;

    public ProteinAlternativesFoodAdapter(Context context, ArrayList<Foods> foods){
        this.filteredFoods = foods;
        this.mContext = context;
    }


    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alternative,parent,false);

        FoodHolder foodHolder = new FoodHolder(view);
        return foodHolder;
    }

    @Override
    public void onBindViewHolder(ProteinAlternativesFoodAdapter.FoodHolder holder, int position) {
        Foods indexedFoods = filteredFoods.get(position);

        holder.tvFoodName.setText(" " + indexedFoods.getFood_name());
        holder.tvBenefits.setText("Benefits:" + " " + indexedFoods.getBenefits());

    }

    @Override
    public int getItemCount() {

        return filteredFoods.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {
        public TextView tvFoodName, tvBenefits;

        public FoodHolder(View itemView){
            super(itemView);
            tvFoodName = (TextView) itemView.findViewById(R.id.altenativeFoodName);
            tvBenefits = (TextView) itemView.findViewById(R.id.alternativeBenefits);
        }
    }
}
