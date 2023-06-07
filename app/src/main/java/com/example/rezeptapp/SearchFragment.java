package com.example.rezeptapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SearchFragment extends Fragment {

    Button searchButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.buttonSearch);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchResultsFragment).commit();            }
        });



        return view;


    }

}


/*


     SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchResultsFragment).commit();
 */