package com.example.rezeptapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public Toolbar myToolbar;

    public static boolean isOnline = true;


/*
    ImageView homeButton;
    ImageView recipiesButton;
    ImageView favoritesButton;
    ImageView searchButton;

    
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Setup for the top ActionBar           René Wentzel
        myToolbar = findViewById(R.id.myToolbar);
        myToolbar.setOverflowIcon(getDrawable(R.drawable.baseline_menu_24));
        myToolbar.setNavigationIcon(getDrawable(R.drawable.baseline_arrow_24));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).addToBackStack(null).commit(); //erste Page muss immer unten sein

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.recipes) {
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, recipesFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new CookingBookFragment()).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.favorites) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFavorite", true);
                    CookingBookFragment cookingBookFragment = new CookingBookFragment();
                    cookingBookFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, cookingBookFragment).addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });

    }


}