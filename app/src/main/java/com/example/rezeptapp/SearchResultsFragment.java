package com.example.rezeptapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchResultsFragment extends Fragment {

    ImageButton backButton;
    TextView recipeIdTest;
    TextView recipeNameTest;
    TextView recipeCategoryTest;
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
         recipeIdTest= view.findViewById(R.id.recipeIdTest);
         recipeNameTest= view.findViewById(R.id.recipeNameTest);
         recipeCategoryTest= view.findViewById(R.id.recipeCategoryTest);
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
                            recipeIdTest.setText(String.valueOf(shortinfo.getId()));
                            recipeNameTest.setText(shortinfo.getTitle());
                            recipeCategoryTest.setText(shortinfo.getImage());
                        }
                    });
               // } catch (InterruptedException e) {
                //    throw new RuntimeException(e);
               // }
            }
        };thread.start();




    }



}