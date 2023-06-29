package com.example.rezeptapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {


    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private ArrayList<Recipe> randomList = new ArrayList<>();
    private ArrayList<ShortInfo> latestList = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();
    DBHandler dbHandler;
    SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            randomList = searchManager.getRandomRecipe(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        dbHandler = new DBHandler(getContext());
        latestList = dbHandler.getLatestShortInfos(10);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Change Optionbar                              Rene Wentzel
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Recipe Rhapsody");
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(false);

        RecyclerView randomView = view.findViewById(R.id.randomRecipeView);
        RecyclerView myView = view.findViewById(R.id.myRecipeView);
        searchView = view.findViewById(R.id.simpleSearchView);

        //loadSuggestedRecipes();
        view.post(new Runnable() {
            @Override
            public void run() {
                SR_RecyclerViewAdapter randomAdapter = new SR_RecyclerViewAdapter(getContext(), randomList);
                randomView.setAdapter(randomAdapter);
                LinearLayoutManager horizontalLayoutManagerRandom = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                randomView.setLayoutManager(horizontalLayoutManagerRandom);

                SR_RecyclerViewAdapter_Latest myAdapter = new SR_RecyclerViewAdapter_Latest(getContext(), latestList);
                myView.setAdapter(myAdapter);
                LinearLayoutManager horizontalLayoutManagerMy = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                myView.setLayoutManager(horizontalLayoutManagerMy);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        try {
                            ArrayList<ShortInfo> searchResult = searchManager.quickSearch(query);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("searchResults", searchResult);
                            SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                            searchResultsFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).addToBackStack(null).commit();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

            }
        });



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