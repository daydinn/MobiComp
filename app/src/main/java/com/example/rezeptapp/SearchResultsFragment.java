package com.example.rezeptapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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



    ImageView recipeImage2;
    TextView recipeTitel2;
    ImageView recipeImage3;
    TextView recipeTitel3;
    ImageView recipeImage4;
    TextView recipeTitel4;
    ImageView recipeImage5;
    TextView recipeTitel5;
    ImageView recipeImage6;
    TextView recipeTitel6;
    ImageView recipeImage7;
    TextView recipeTitel7;
    ImageView recipeImage8;
    TextView recipeTitel8;
    ImageView recipeImage9;
    TextView recipeTitel9;
    ImageView recipeImage10;
    TextView recipeTitel10;







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
        backButton = view.findViewById(R.id.backButton);
        reloadButton = view.findViewById(R.id.reloadButton);
        recipeMinutes = view.findViewById(R.id.recipeMinutes);
        recipeTitel = view.findViewById(R.id.recipeTitel);
        recipeHealthScore = view.findViewById(R.id.recipeHealthScore);
        recipeIngredients = view.findViewById(R.id.recipeIngredients);
        recipeImage = view.findViewById(R.id.recipeImage);
        spaceNewRecipe = view.findViewById(R.id.spaceNewRecipe);
        result = view.findViewById(R.id.result);
        ofresults = view.findViewById(R.id.ofResults);
        recipeTitel2 = view.findViewById(R.id.recipeTitel2);
        recipeImage2 = view.findViewById(R.id.recipeImage2);
        recipeTitel3 = view.findViewById(R.id.recipeTitel3);
        recipeImage3 = view.findViewById(R.id.recipeImage3);
        recipeTitel4 = view.findViewById(R.id.recipeTitel4);
        recipeImage4 = view.findViewById(R.id.recipeImage4);
        recipeTitel5 = view.findViewById(R.id.recipeTitel5);
        recipeImage5 = view.findViewById(R.id.recipeImage5);
        recipeTitel6 = view.findViewById(R.id.recipeTitel6);
        recipeImage6 = view.findViewById(R.id.recipeImage6);
        recipeTitel7 = view.findViewById(R.id.recipeTitel7);
        recipeImage7 = view.findViewById(R.id.recipeImage7);
        recipeTitel8 = view.findViewById(R.id.recipeTitel8);
        recipeImage8 = view.findViewById(R.id.recipeImage8);
        recipeTitel9 = view.findViewById(R.id.recipeTitel9);
        recipeImage9 = view.findViewById(R.id.recipeImage9);
        recipeTitel10 = view.findViewById(R.id.recipeTitel10);
        recipeImage10 = view.findViewById(R.id.recipeImage10);


        Bundle bundle = getArguments();
        if(bundle!=null){
            searchResults = (ArrayList<ShortInfo>) bundle.getSerializable("searchResults");
            for(int i=0; i<searchResults.size();i++){
                Log.d("result5", searchResults.get(i).getTitle());
                Log.d("result5", String.valueOf(searchResults.get(i).getId()));
                Log.d("result5", searchResults.get(i).getImage());
            }
        }

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foundRecipe", String.valueOf(shortInfoList.get(0).getId()));
                RecipePageFragment recipePageFragment = new RecipePageFragment();
                recipePageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, recipePageFragment).addToBackStack(null).commit();
            }
        });



        loadFoundRecipe();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });


        reloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                loadFoundRecipe();
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
                                Picasso.get().load(shortinfo.getImage()).into(recipeImage);

                                //recipeIdTest.setText(String.valueOf(shortinfo.getId())); no need?

                                //recipeImage  ?

                                //noch nicht fertig
                                String id = String.valueOf(shortinfo.getId());
                                recipeMinutes.setText(id);

                                //recipeHealthScore.setText(shortinfo.getTitle());
                                recipeIngredients.setText(shortinfo.getImage());

                                recipeTitel.setText(shortinfo.getTitle());
                                //recipeHealthScore.setText(shortinfo.getImage());


                                //chard1
                               ShortInfo shortinfo1 = shortInfoList.get(1);
                               Picasso.get().load(shortinfo1.getImage()).into(recipeImage);
                               recipeTitel2.setText(shortinfo1.getTitle());

                              //chard2
                              ShortInfo shortinfo2 = shortInfoList.get(2);
                              Picasso.get().load(shortinfo2.getImage()).into(recipeImage2);
                              recipeTitel3.setText(shortinfo2.getTitle());

                              //chard3
                            ShortInfo shortinfo3 = shortInfoList.get(3);
                            Picasso.get().load(shortinfo3.getImage()).into(recipeImage3);
                            recipeTitel4.setText(shortinfo3.getTitle());

                            //chard4
                            ShortInfo shortinfo4 = shortInfoList.get(4);
                            Picasso.get().load(shortinfo4.getImage()).into(recipeImage4);
                            recipeTitel5.setText(shortinfo4.getTitle());

                            //chard5
                            ShortInfo shortinfo5 = shortInfoList.get(5);
                            Picasso.get().load(shortinfo5.getImage()).into(recipeImage5);
                            recipeTitel6.setText(shortinfo5.getTitle());

                            //chard6
                            ShortInfo shortinfo6 = shortInfoList.get(6);
                            Picasso.get().load(shortinfo6.getImage()).into(recipeImage6);
                            recipeTitel7.setText(shortinfo6.getTitle());

                            //chard7
                            ShortInfo shortinfo7 = shortInfoList.get(7);
                            Picasso.get().load(shortinfo7.getImage()).into(recipeImage7);
                            recipeTitel8.setText(shortinfo7.getTitle());

                            //chard8
                            ShortInfo shortinfo8 = shortInfoList.get(8);
                            Picasso.get().load(shortinfo8.getImage()).into(recipeImage8);
                            recipeTitel9.setText(shortinfo8.getTitle());


                            //chard9
                            ShortInfo shortinfo9 = shortInfoList.get(9);
                            Picasso.get().load(shortinfo9.getImage()).into(recipeImage9);
                            recipeTitel10.setText(shortinfo9.getTitle());
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





                            result.setText(id);

                                String sizeofList= String.valueOf(shortInfoList.size());
                                ofresults.setText(sizeofList);


                            }


                    });
                    // } catch (InterruptedException e) {
                    //    throw new RuntimeException(e);
                    // }
                }
            };
            thread.start();


        }


    }
