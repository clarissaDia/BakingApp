package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Adapters.DetailsAdapter;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements DetailsAdapter.ListClickListener {
private ArrayList<Recipe> recipeArrayList;
String recipeName;
    static String INDEX_RECIPE = "index_recipe";
    static String STACK_RECIPE_DETAIL="stack_recipe_detail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if (savedInstanceState == null){
            Bundle indexRecipe = getIntent().getExtras();
            recipeArrayList = new ArrayList<>();
            recipeArrayList = indexRecipe.getParcelableArrayList(INDEX_RECIPE);
            recipeName = recipeArrayList.get(0).getName();

            final DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(indexRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();
        }else {
            recipeName = savedInstanceState.getString("title");
        }
    }

    @Override
    public void onItemClick(List<Steps> stepsList, int clickedIndex, String recipeName) {

    }
}
