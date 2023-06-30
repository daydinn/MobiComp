package com.example.rezeptapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class CookingBookFragment extends Fragment {

    ArrayList<CardView> recipeCardList = new ArrayList<>();
    ArrayList<ShortInfo> dbResults = new ArrayList<>();
    DBHandler dbHandler;
    RecyclerView recyclerView;

    LinearLayout bookSitesLayout;
    boolean isFavorite=false;
    public CookingBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler(getContext());

        Bundle bundle = getArguments();
        if(bundle!=null){
            isFavorite = bundle.getBoolean("isFavorite");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cooking_book, container, false);

        //Set Optionbar Title
        if(!isFavorite)
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("My Cooking Book");
        else
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("My Favorite Recipes");
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = view.findViewById(R.id.CookingBookRecycler);

        bookSitesLayout = view.findViewById(R.id.BookSitesLayout);

        //Setting up recipe cards

        view.post(new Runnable() {
            @Override
            public void run() {
                if(isFavorite)
                    dbResults = dbHandler.getAllFavoriteShortInfo();
                else
                    dbResults=dbHandler.getAllShortInfo();
                SR_RecyclerViewAdapter_Cookingbook myAdapter = new SR_RecyclerViewAdapter_Cookingbook(getContext(), dbResults);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }



}