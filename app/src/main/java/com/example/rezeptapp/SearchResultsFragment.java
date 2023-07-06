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

    TextView NoRecipeFound;

    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private ArrayList<ShortInfo> searchResults = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * Sets up toolbar title
     * Takes the given ShortInfo objects and displays them in recycler view.
     * @Author Rene Wentzel Diyar Aydin
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        //Takes the given bundle. If bundle contains data, casts them into searchResult object.             Rene Wentzel
        Bundle bundle = getArguments();
        if (bundle != null) {
            searchResults = (ArrayList<ShortInfo>) bundle.getSerializable("searchResults");
        }
        //Set Optionbar Title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Found Recipes");
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        // creates a RcyclerView object to display recipes dynamically
        RecyclerView recyclerView = view.findViewById(R.id.myRecyclerview);
        //attaches recipes to the RcyclerView Adapter
        SR2_RecyclerViewAdapter adapter = new SR2_RecyclerViewAdapter(this.getContext(), searchResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // text if recipes are not found
        NoRecipeFound = view.findViewById(R.id.NoRecipeFound);


        loadFoundRecipe();


        return view;


    }

    /**
     * a method for loading random recipes and updating the text view for testing purposes
     *
     * @return random number in range [0.. size of shortInfoList]
     * @Author Diyar Aydin
     */
    public int RandInt() {


        Random rand = new Random();
        int i = rand.nextInt(shortInfoList.size());
        return i;

    }

    /**
     * a method for loading the found recipes and updating the text view
     *
     * @Author Diyar Aydin
     */
    private void loadFoundRecipe() {


        Thread thread = new Thread() {

            public void run() {


                shortInfoList = searchManager.getTestData();
                Log.d("test", "nachricht");


                getActivity().runOnUiThread(new Runnable() {


                    public void run() {

                        ShortInfo shortinfo = shortInfoList.get(RandInt());
                        String id = String.valueOf(shortinfo.getId());
                        //recipeMinutes.setText(id);


                        String sizeofList = String.valueOf(shortInfoList.size());


                        if (searchResults.size() == 0) {
                            NoRecipeFound.setVisibility(View.VISIBLE);
                        }

                    }


                });

            }
        };
        thread.start();


    }

    /**
     * a method for loading random recipes and updating the text view for testing purposes
     *
     * @Author Diyar Aydin
     */
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
