package com.example.rezeptapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private ArrayList<Recipe> randomList = new ArrayList<>();
    private ArrayList<ShortInfo> latestList = new ArrayList<>();
    private SearchManager searchManager = new SearchManager();
    DBHandler dbHandler;
    SearchView searchView;

    /**
     * Requests random recipes from SearchManager, if in Online mode
     * Loads list of recently added Recipes depending on Offline/Online mode.
     * @Author Rene Wentzel
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new DBHandler(getContext());
        if(MainActivity.isOnline){
            try {
                randomList = searchManager.getRandomRecipe(10);
            } catch (InterruptedException e) {

                throw new RuntimeException(e);
            }
            latestList = dbHandler.getLatestShortInfos(10);
        }
        else{
            latestList = dbHandler.getLatestOfflineRecipes(10);
        }


    }

    /**
     * Sets Toolbar color depending on Online/Offline mode.
     * Creates recyclerview of random recipes, if in Online mode.
     * Creates recyclerview of recently added recipes depending on Online/Offline mode.
     * Creates a searchview listener, if in Online mode.
     * @Author Rene Wentzel
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //Change Toolbar                              Rene Wentzel
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Recipe Rhapsody");
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(false);
        if(MainActivity.isOnline)
            getActivity().findViewById(R.id.myToolbar).setBackgroundColor(Color.parseColor("#40CA15"));
        else
            getActivity().findViewById(R.id.myToolbar).setBackgroundColor(Color.parseColor("#C4DD56"));

        RecyclerView randomView = view.findViewById(R.id.randomRecipeView);
        RecyclerView myView = view.findViewById(R.id.myRecipeView);
        searchView = view.findViewById(R.id.simpleSearchView);
        TextView randomTitle = view.findViewById(R.id.RandomTitel);
        TextView latestTitle = view.findViewById(R.id.LatestRecipieTitel);
        if(!MainActivity.isOnline){
            randomTitle.setVisibility(View.INVISIBLE);
            searchView.setVisibility(View.INVISIBLE);
        }
        if(latestList.isEmpty()){
            latestTitle.setVisibility(View.INVISIBLE);
        }

        setUpOptionMenu();
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


    /**
     * Sets up an Option Menu in the top Toolbar.
     * Includes options to switch between offline and online mode.
     * @Author Rene Wentzel
     */
    private void setUpOptionMenu() {

        requireActivity().addMenuProvider(new MenuProvider() {
              @Override
              public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                  menu.clear();
                  menuInflater.inflate(R.menu.recipe_menu, menu);
                  if(MainActivity.isOnline){
                      menu.findItem(R.id.onlineModeItem).setVisible(false);
                      menu.findItem(R.id.offlineModeItem).setVisible(true);
                  }
                  else{
                      menu.findItem(R.id.onlineModeItem).setVisible(true);
                      menu.findItem(R.id.offlineModeItem).setVisible(false);
                  }
                  menu.findItem(R.id.recipeSaveItem).setVisible(false);
                  menu.findItem(R.id.recipeDeleteItem).setVisible(false);
                  menu.findItem(R.id.recipeSaveOfflineItem).setVisible(false);
                  menu.findItem(R.id.recipeDeleteOfflineItem).setVisible(false);
                  menu.findItem(R.id.recipeShareItem).setVisible(false);
                  menu.findItem(R.id.favoriteItem).setVisible(false);
              }

              @Override
              public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                  //Switch to Offline mode
                  if(menuItem.getItemId()==R.id.offlineModeItem){
                      MainActivity.isOnline=false;
                      Snackbar.make(getView(), "You are in Offline Mode now", Snackbar.LENGTH_SHORT).show();
                  }
                  else if(menuItem.getItemId()==R.id.onlineModeItem){
                      //Switch to Online mode
                      MainActivity.isOnline=true;
                      Snackbar.make(getView(), "You are in Online Mode now", Snackbar.LENGTH_SHORT).show();
                  }
                  //Updates the toolbar, pops the backstack and navigates to the home page
                  requireActivity().invalidateOptionsMenu();
                  getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

                  return true;
              }
          }, getViewLifecycleOwner()
        );
    }
    /**
     * returns random recipes for Testing purposes
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