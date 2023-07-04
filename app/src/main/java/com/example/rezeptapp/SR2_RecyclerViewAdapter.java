package com.example.rezeptapp;

import android.content.Context;
import android.os.Bundle;
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

public class SR2_RecyclerViewAdapter extends RecyclerView.Adapter<SR2_RecyclerViewAdapter.SR2_ViewHolder> {

    Context context;
    ArrayList<ShortInfo> shortInfoList;

    public SR2_RecyclerViewAdapter(Context context, ArrayList<ShortInfo> shortInfoList) {
        this.context = context;
        this.shortInfoList = shortInfoList;
    }

    /**
     * a method for inflating the layout
     *
     * @return new RecyclerViewAdapter
     * @Author Diyar Aydin
     */
    @NonNull
    @Override
    public SR2_RecyclerViewAdapter.SR2_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recler_view_row2, parent, false);
        return new SR2_RecyclerViewAdapter.SR2_ViewHolder(view);
    }

    /**
     * a method for assigning the values based on the position of recycler view, which has been created in the recycler_view_row layout file
     *
     * @Author Diyar Aydin, Rene Wentzel
     */
    @Override
    public void onBindViewHolder(@NonNull SR2_RecyclerViewAdapter.SR2_ViewHolder holder, int position) {


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

    /**
     * returns the number of recipes to Recyclerview
     *
     * @return int
     * @Author Diyar Aydin
     */
    @Override
    public int getItemCount() {
        //the recycler view just wants to know the number of the items

        return shortInfoList.size();
    }

    /**
     * grabbing the views from recycler_view_row layout file, kinda like in the onCreate method
     *
     * @Author Diyar Aydin
     */
    public static class SR2_ViewHolder extends RecyclerView.ViewHolder {

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
