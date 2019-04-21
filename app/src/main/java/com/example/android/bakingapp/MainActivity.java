package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingapp.Models.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ClickListener {
    static String INDEX_RECIPE = "index_recipe";
    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";

    @Nullable
    private SimpleIdlingResource mSimpleIdlingResource;
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource (){
        if (mSimpleIdlingResource == null){
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_main);
        Toolbar bakingToolbar = (Toolbar) findViewById(R.id.baking_toolbar);
        setSupportActionBar(bakingToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking Time");
        getIdlingResource();
    }

    @Override
    public void onItemClick(Recipe index) {
        Bundle indexBundle = new Bundle();
        ArrayList<Recipe> indexRecipe = new ArrayList<>();
        indexRecipe.add(index);
        indexBundle.putParcelableArrayList(INDEX_RECIPE, indexRecipe);
        final Intent detailsIntent = new Intent(this,RecipeDetailActivity.class);
        detailsIntent.putExtras(indexBundle);
        startActivity(detailsIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
