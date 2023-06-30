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

public class SR_RecyclerViewAdapter_Recipe_Ingredients extends RecyclerView.Adapter<SR_RecyclerViewAdapter_Recipe_Ingredients.SR_ViewHolder> {
    Context context;
    ArrayList<Recipe.Ingredient> ingredients;

    public SR_RecyclerViewAdapter_Recipe_Ingredients(Context context, ArrayList<Recipe.Ingredient> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }


    @NonNull
    @Override
    public SR_RecyclerViewAdapter_Recipe_Ingredients.SR_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and style rows

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_recipe_ingredients, parent, false);

        return new SR_RecyclerViewAdapter_Recipe_Ingredients.SR_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SR_ViewHolder holder, int position) {
        //assign values to views from recycler_view_row file based on position of the recycler view

        holder.name.setText(String.valueOf(ingredients.get(position).getName()));
        holder.amount.setText(ingredients.get(position).getAmount()+" "+ ingredients.get(position).getUnit());

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class SR_ViewHolder extends RecyclerView.ViewHolder{
        //grab views from recycler_view_row file

        TextView name;
        TextView amount;

        public SR_ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.RecyclerRecipeIngredientName);
            amount = itemView.findViewById(R.id.RecyclerRecipeIngredientAmount);
        }
    }
}
