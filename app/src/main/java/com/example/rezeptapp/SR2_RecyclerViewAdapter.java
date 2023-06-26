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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SR2_RecyclerViewAdapter extends RecyclerView.Adapter<SR2_RecyclerViewAdapter.SR2_ViewHolder>{

    Context context;
    ArrayList<ShortInfo> shortInfoList;

    public SR2_RecyclerViewAdapter(Context context, ArrayList<ShortInfo> shortInfoList){
        this.context = context;
        this.shortInfoList = shortInfoList;
    }

    @NonNull
    @Override
    public SR2_RecyclerViewAdapter.SR2_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //This is where you inflate the layout(Giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recler_view_row2, parent, false);
        return new SR2_RecyclerViewAdapter.SR2_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SR2_RecyclerViewAdapter.SR2_ViewHolder holder, int position) {
        //assigning values to the view we created in the recycler_view_row layout file
        //based on the position of the recycler view

        holder.tvID.setText(String.valueOf(shortInfoList.get(position).getId()));
        holder.tvTitle.setText(shortInfoList.get(position).getTitle());
        //holder.imageView.setImageResource(shortInfoList.get(position).getImage());
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
        //the recycler view just wants to know the number of the items

        return shortInfoList.size();
    }






    public static class SR2_ViewHolder extends RecyclerView.ViewHolder{
        //grabbing the views from our recycler_view_row layout file
        //Kinda like in the onCreate method


        TextView tvID;
        TextView tvTitle;
        //TextView tvDescription;
        ImageView imageView;


        public SR2_ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.id);
            tvTitle = itemView.findViewById(R.id.title);
            //tvDescription = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageShortRecipes);
        }
    }
}
