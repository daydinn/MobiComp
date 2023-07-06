package com.example.rezeptapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

public class SR_RecyclerViewAdapter extends RecyclerView.Adapter<SR_RecyclerViewAdapter.SR_ViewHolder> {
    Context context;
    ArrayList<Recipe> shortInfoList;

    public SR_RecyclerViewAdapter(Context context, ArrayList<Recipe> shortInfoList){
        this.context = context;
        this.shortInfoList = shortInfoList;
    }


    @NonNull
    @Override
    public SR_RecyclerViewAdapter.SR_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and style rows

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new SR_RecyclerViewAdapter.SR_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SR_ViewHolder holder, int position) {
        //assign values to views from recycler_view_row file based on position of the recycler view

        holder.tvID.setText(String.valueOf(shortInfoList.get(position).getId()));
        holder.tvTitle.setText(shortInfoList.get(position).getTitle());
        Picasso.get().load(shortInfoList.get(position).getImage()).into(holder.imageView);

        /**
         * Navigates to the chosen recipe.
         * @Author Rene Wentzel
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Puts id of the choosen recipe into a bundle and gives it to a new started RecipePageFragment.         Rene Wentzel
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(holder.tvID.getText()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, recipePageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shortInfoList.size();
    }

    public static class SR_ViewHolder extends RecyclerView.ViewHolder{
        //grab views from recycler_view_row file

        TextView tvID;
        TextView tvTitle;
        ImageView imageView;

        public SR_ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.startPageID);
            tvTitle = itemView.findViewById(R.id.startPageTitleText);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
