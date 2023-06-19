package com.example.rezeptapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SR_RecyclerViewAdapter extends RecyclerView.Adapter<SR_RecyclerViewAdapter.SR_ViewHolder> {
    Context context;
    ArrayList<ShortInfo> shortInfoList;

    public SR_RecyclerViewAdapter(Context context, ArrayList<ShortInfo> shortInfoList){
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
        //holder.imageView.setImageResource(shortInfoList.get(position).getImage());
        //TODO: proper image format
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

            tvID = itemView.findViewById(R.id.textView);
            tvTitle = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
