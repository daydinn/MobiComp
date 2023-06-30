package com.example.rezeptapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SR_RecyclerViewAdapter_Recipe_Nutrients extends RecyclerView.Adapter<SR_RecyclerViewAdapter_Recipe_Nutrients.SR_ViewHolder> {
    Context context;
    ArrayList<Recipe.Nutrition.Nutrient> nutrients;

    public SR_RecyclerViewAdapter_Recipe_Nutrients(Context context, ArrayList<Recipe.Nutrition.Nutrient> nutrients){
        this.context = context;
        this.nutrients = nutrients;
    }


    @NonNull
    @Override
    public SR_RecyclerViewAdapter_Recipe_Nutrients.SR_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and style rows

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_recipe_nutrients, parent, false);

        return new SR_RecyclerViewAdapter_Recipe_Nutrients.SR_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SR_ViewHolder holder, int position) {
        //assign values to views from recycler_view_row file based on position of the recycler view

        holder.name.setText(String.valueOf(nutrients.get(position).getName()));
        holder.amount.setText(nutrients.get(position).getAmount()+" "+nutrients.get(position).getUnit());

    }

    @Override
    public int getItemCount() {
        return nutrients.size();
    }

    public static class SR_ViewHolder extends RecyclerView.ViewHolder{
        //grab views from recycler_view_row file

        TextView name;
        TextView amount;

        public SR_ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.RecyclerRecipeName);
            amount = itemView.findViewById(R.id.RecyclerRecipeAmount);
        }
    }
}
