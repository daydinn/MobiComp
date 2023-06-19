package com.example.rezeptapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchResultsFragment extends Fragment {


    ImageView recipeImage;
    ImageButton backButton;

    TextView recipeMinutes;
    TextView recipeHealthScore;

    TextView recipeIngredients;

    TextView recipeTitel;

    Button reloadButton;




    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
         backButton = view.findViewById(R.id.backButton);
         reloadButton = view.findViewById(R.id.reloadButton);
         recipeMinutes= view.findViewById(R.id.recipeMinutes);
         recipeTitel= view.findViewById(R.id.recipeTitel);
         recipeHealthScore= view.findViewById(R.id.recipeHealthScore);
         recipeIngredients= view.findViewById(R.id.recipeIngredients);
         recipeImage= view.findViewById(R.id.recipeImage);
        loadFoundRecipe();






        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();            }
        });



        reloadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                loadFoundRecipe();
            }
        });

        return view;


    }



    private void loadFoundRecipe(){
        Thread thread = new Thread(){
            public void run(){

                //try {

                    shortInfoList = searchManager.getTestData();
                    Log.d("test", "nachricht");
                    //Write first recipe into activity

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ShortInfo shortinfo = shortInfoList.get(5);

                            Picasso.get().load(shortinfo.getImage()).into(recipeImage);

                            //recipeIdTest.setText(String.valueOf(shortinfo.getId())); no need?

                            //recipeImage  ?

                            //noch nicht fertig
                            recipeMinutes.setText(shortinfo.getTitle());
                            recipeHealthScore.setText(shortinfo.getTitle());
                            recipeIngredients.setText(shortinfo.getTitle());
                            recipeTitel.setText(shortinfo.getTitle());
                            recipeHealthScore.setText(shortinfo.getImage());
                        }
                    });
               // } catch (InterruptedException e) {
                //    throw new RuntimeException(e);
               // }
            }
        };thread.start();




    }



}