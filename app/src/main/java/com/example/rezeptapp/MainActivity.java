package com.example.rezeptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment= new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    SearchFragment searchResultsFragment = new SearchFragment();

    RecipesFragment recipesFragment = new RecipesFragment();
    FavoritesFragment favoritesFragment= new FavoritesFragment();
    CookingBookFragment cookingBookFragment = new CookingBookFragment();



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
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //Setup for the top ActionBar           René Wentzel
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);





        //getSupportFragmentManager().beginTransaction().add(R.id.container,searchFragment).addToBackStack(null).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,searchResultsFragment).addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container,homeFragment).commit(); //erste Page muss immer unten sein
        //helps to replace the container without the homeFragment while we open the App
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

/*
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                        return true;
                    case R.id.recipes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, recipesFragment).commit();
                        return true;
                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, favoritesFragment).commit();
                        return true;
                }

                return false;
            }
        });

*/


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.recipes) {
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, recipesFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new CookingBookFragment()).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.favorites) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoritesFragment()).addToBackStack(null).commit();
                    return true;
                }

                return false;
            }
        });




/*        //Nur für Testzwecke
        goToRandomRecipe = findViewById(R.id.buttonGoToRandomRecipeActivity);
        goToRandomRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RandomRecipeDemoActivity.class));
            }
        });
*/
/*
        //navigate to HomePage
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });


        //navigate to SearchPage
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });



        //navigate to RecipiesPage
        recipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecipiesActivity.class));
            }
        });


        //navigate to FavoritesPage
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FavoritesActivity.class));
            }
        });

        */

    }

}
