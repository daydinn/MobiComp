package com.example.rezeptapp;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.suggestedRecipeView);

        loadSuggestedRecipes();

        SR_RecyclerViewAdapter adapter = new SR_RecyclerViewAdapter(this.getContext(), shortInfoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    private void loadSuggestedRecipes() {
        Thread thread = new Thread() {
            public void run() {
                shortInfoList = searchManager.getTestData();
                Log.d("test", "nachricht");
            }
        };
        thread.start();
    }
}