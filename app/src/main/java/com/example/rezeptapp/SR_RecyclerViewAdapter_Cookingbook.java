package com.example.rezeptapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class SR_RecyclerViewAdapter_Cookingbook extends RecyclerView.Adapter<SR_RecyclerViewAdapter_Cookingbook.SR_ViewHolder> {
    Context context;
    ArrayList<ShortInfo> shortInfoList;

    public SR_RecyclerViewAdapter_Cookingbook(Context context, ArrayList<ShortInfo> shortInfoList){
        this.context = context;
        this.shortInfoList = shortInfoList;
    }


    @NonNull
    @Override
    public SR_RecyclerViewAdapter_Cookingbook.SR_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and style rows

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_cookingbook, parent, false);

        return new SR_RecyclerViewAdapter_Cookingbook.SR_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SR_ViewHolder holder, int position) {
        //assign values to views from recycler_view_row file based on position of the recycler view

        holder.tvID.setText(String.valueOf(shortInfoList.get(position).getId()));
        holder.tvTitle.setText(shortInfoList.get(position).getTitle());
        Picasso.get().load(shortInfoList.get(position).getImage()).into(holder.imageView);

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

            tvID = itemView.findViewById(R.id.CookingBookId);
            tvTitle = itemView.findViewById(R.id.CookingBookTitle);
            imageView = itemView.findViewById(R.id.CookingBookImage);
        }
    }
}
