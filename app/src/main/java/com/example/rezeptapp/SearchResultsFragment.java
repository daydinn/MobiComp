package com.example.rezeptapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class SearchResultsFragment extends Fragment {

    ImageView recipeImage;

    TextView recipeMinutes;
    TextView recipeHealthScore;

    TextView recipeIngredients;

    TextView recipeTitel;

    ImageButton reloadButton;

    Space spaceNewRecipe;

    TextView result;

    TextView ofresults;

    TextView NoRecipeFound;

    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private ArrayList<ShortInfo> searchResults = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        //Takes the given bundle. If bundle contains data, casts them into searchResult object.             Rene Wentzel
        Bundle bundle = getArguments();
        if(bundle!=null){
            searchResults = (ArrayList<ShortInfo>) bundle.getSerializable("searchResults");
        }
        //Set Optionbar Title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Found Recipes");
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = view.findViewById(R.id.myRecyclerview);
        SR2_RecyclerViewAdapter adapter = new SR2_RecyclerViewAdapter(this.getContext(), searchResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        NoRecipeFound = view.findViewById(R.id.NoRecipeFound);





        loadFoundRecipe();


        return view;


    }

    //random zahl generator
    public int RandInt() {




        Random rand = new Random();
        int i = rand.nextInt(shortInfoList.size()); //gives random in range [0..9]
        return i;

    }



    private void loadFoundRecipe() {




        Thread thread = new Thread() {

                public void run() {

                    //try {

                    shortInfoList = searchManager.getTestData();
                    Log.d("test", "nachricht");
                    //Write first recipe into activity


                    getActivity().runOnUiThread(new Runnable() {


                        public void run() {

                                ShortInfo shortinfo = shortInfoList.get(RandInt());
                                //noch nicht fertig
                                String id = String.valueOf(shortinfo.getId());
                                //recipeMinutes.setText(id);



                                String sizeofList= String.valueOf(shortInfoList.size());
                                //ofresults.setText(sizeofList);

                             if(searchResults.size() == 0){
                                 NoRecipeFound.setVisibility(View.VISIBLE);
                             }

                            }


                    });
                    // } catch (InterruptedException e) {
                    //    throw new RuntimeException(e);
                    // }
                }
            };
            thread.start();


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
