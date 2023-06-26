package com.example.rezeptapp;

import android.os.Bundle;

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
import java.util.Random;


public class SearchResultsFragment extends Fragment {

    ImageView recipeImage;
    ImageButton backButton;

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

        loadSuggestedRecipes();// muss zuerst aufgerufen werden
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.myRecyclerview);
        SR2_RecyclerViewAdapter adapter = new SR2_RecyclerViewAdapter(this.getContext(), shortInfoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));










        backButton = view.findViewById(R.id.backButton);
        //reloadButton = view.findViewById(R.id.reloadButton);

        //recipeMinutes = view.findViewById(R.id.recipeMinutes);
       // recipeTitel = view.findViewById(R.id.recipeTitel);
        //recipeHealthScore = view.findViewById(R.id.recipeHealthScore);
        //recipeIngredients = view.findViewById(R.id.recipeIngredients);
        //recipeImage = view.findViewById(R.id.recipeImage);


        //spaceNewRecipe = view.findViewById(R.id.spaceNewRecipe);
        //result = view.findViewById(R.id.result);
       // ofresults = view.findViewById(R.id.ofResults);


        /*ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(recipeImage);
        imageViewList.add(recipeImage2);
        imageViewList.add(recipeImage3);
        imageViewList.add(recipeImage4);
        imageViewList.add(recipeImage5);
        imageViewList.add(recipeImage6);
        imageViewList.add(recipeImage7);
        imageViewList.add(recipeImage8);
        imageViewList.add(recipeImage9);
        imageViewList.add(recipeImage10);

        ArrayList<TextView> textViewList = new ArrayList<>();
        textViewList.add(recipeTitel);
        textViewList.add(recipeTitel2);
        textViewList.add(recipeTitel3);
        textViewList.add(recipeTitel4);
        textViewList.add(recipeTitel5);
        textViewList.add(recipeTitel6);
        textViewList.add(recipeTitel7);
        textViewList.add(recipeTitel8);
        textViewList.add(recipeTitel9);
        textViewList.add(recipeTitel10);*/





        /*Bundle bundle = getArguments();
        if(bundle!=null){
            searchResults = (ArrayList<ShortInfo>) bundle.getSerializable("searchResults");
            for(int i=0; i<searchResults.size();i++){
                textViewList.get(i).setText(searchResults.get(i).getTitle());
                Picasso.get().load(searchResults.get(i).getImage()).into(imageViewList.get(i));

                Log.d("result5", searchResults.get(i).getTitle());
                Log.d("result5", String.valueOf(searchResults.get(i).getId()));
                Log.d("result5", searchResults.get(i).getImage());
            }
        }*/
/*
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(0).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(1).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(2).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(3).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(4).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(5).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(6).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(7).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(8).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });
        recipeImage10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(searchResults.get(9).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });*/



        loadFoundRecipe();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });





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
