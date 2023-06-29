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


    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private ArrayList<ShortInfo> searchResults = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //loadSuggestedRecipes();// muss zuerst aufgerufen werden
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));






        loadFoundRecipe();


        return view;


    }

    //random zahl generator
    public int RandInt() {




        Random rand = new Random();
        int i = rand.nextInt(shortInfoList.size()); //gives random in range [0..9]
        return i;

    }

    /*
    public int counter {

        int currentNumber = 0;
        for (int i = 1; i <y ; i++) {
            currentNumber = counter(i);

        }
        return counter(currentNumber);
    }

    public static int counter(int number) {
        return number;
    }

*/




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
                                //Picasso.get().load(shortinfo.getImage()).into(recipeImage);

                                //recipeIdTest.setText(String.valueOf(shortinfo.getId())); no need?

                                //recipeImage  ?

                                //noch nicht fertig
                                String id = String.valueOf(shortinfo.getId());
                                //recipeMinutes.setText(id);

                                //recipeHealthScore.setText(shortinfo.getTitle());
                                //recipeIngredients.setText(shortinfo.getImage());

                                //recipeTitel.setText(shortinfo.getTitle());
                                //recipeHealthScore.setText(shortinfo.getImage());



   /*
                            //chard10
                            ShortInfo shortinfo10 = shortInfoList.get(10);
                            Picasso.get().load(shortinfo10.getImage()).into(recipeImage10);



                            recipeTitel11.setText(shortinfo10.getTitle());

                            //chard11
                            ShortInfo shortinfo11 = shortInfoList.get(10);
                            Picasso.get().load(shortinfo11.getImage()).into(recipeImage11);
                            recipeTitel11.setText(shortinfo11.getTitle());


*/





                            //result.setText(id);

                                String sizeofList= String.valueOf(shortInfoList.size());
                                //ofresults.setText(sizeofList);


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
