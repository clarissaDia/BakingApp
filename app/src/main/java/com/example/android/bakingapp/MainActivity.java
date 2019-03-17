package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.Models.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ClickListenter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_main);
    }

    @Override
    public void onItemiClick(Recipe index) {
        Bundle indexBundle = new Bundle();
        ArrayList<Recipe> indexRecipe = new ArrayList<>();
        indexRecipe.add(index);
        indexBundle.putParcelableArrayList("index_Recipe", indexRecipe);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
