package com.example.rezeptapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class CookingBookFragment extends Fragment {
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
        //Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = view.findViewById(R.id.CookingBookRecycler);
        bookSitesLayout = view.findViewById(R.id.BookSitesLayout);

        setUpOptionMenu();

        //Setting up recipe cards (card views) after fragment is created
        /**
         * Loads saved recipes from database depending on Online/Offline mode.
         * Sets up a recycler view with card views for each recipe.
         * Is executed after other pending tasks are finished.
         * @Author Rene Wentzel
         */
        view.post(new Runnable() {
            @Override
            public void run() {
                //Gets list of saved recipes depending on online/offline mode
                if(MainActivity.isOnline){
                    if(isFavorite)
                        dbResults = dbHandler.getAllFavoriteShortInfo();
                    else
                        dbResults=dbHandler.getAllShortInfo();
                }
                else{
                    if(isFavorite)
                        dbResults = dbHandler.getAllFavoriteOfflineRecipes();
                    else
                        dbResults=dbHandler.getAllOfflineRecipes();
                }
                //Sets recycler view for recipe cards
                SR_RecyclerViewAdapter_Cookingbook myAdapter = new SR_RecyclerViewAdapter_Cookingbook(getContext(), dbResults);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        // Inflate the layout for this fragment
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
                  //Switch to Offline Mode
                  if(menuItem.getItemId()==R.id.offlineModeItem){
                      MainActivity.isOnline=false;
                      Snackbar.make(getView(), "You are in Offline Mode now", Snackbar.LENGTH_SHORT).show();
                  }
                  //Switch to Online Mode
                  else if(menuItem.getItemId()==R.id.onlineModeItem){
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


}