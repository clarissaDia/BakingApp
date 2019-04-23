package com.example.android.bakingapp;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.bakingapp.Adapters.DetailsAdapter;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Steps;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.MainActivity.INDEX_RECIPE;
import static com.example.android.bakingapp.MainActivity.SELECTED_INDEX;
import static com.example.android.bakingapp.MainActivity.SELECTED_STEPS;

public class RecipeDetailActivity extends AppCompatActivity implements DetailsAdapter.ListClickListener,
StepsFragment.ListClickListener{

public ArrayList<Recipe> recipeArrayList;
String recipeName;

    static String STACK_RECIPE_DETAIL="stack_recipe_detail";
    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";
    public static String SHARED_PREFERENCE_KEY = "SHARED_PREFERENCE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if (savedInstanceState == null){
            Bundle indexRecipe = getIntent().getExtras();
            recipeArrayList = new ArrayList<>();
            assert indexRecipe != null;
            recipeArrayList = indexRecipe.getParcelableArrayList(INDEX_RECIPE);
            assert recipeArrayList != null;
            recipeName = recipeArrayList.get(0).getName();

            final DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(indexRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();
            if(findViewById(R.id.recipe_linear_layout).getTag()!= null
                    && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")){
                final StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setArguments(indexRecipe);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container1,stepsFragment)
                        .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();
            }
        }else {
            recipeName = savedInstanceState.getString("Title");
        }

        Toolbar bakingToolbar = (Toolbar) findViewById(R.id.baking_toolbar);
        setSupportActionBar(bakingToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);

        bakingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(findViewById(R.id.fragment_container1)== null){
                    if (fragmentManager.getBackStackEntryCount() > 1){
                        fragmentManager.popBackStack(STACK_RECIPE_DETAIL, 0);
                }else if (fragmentManager.getBackStackEntryCount() > 0) {
                        //go back to "Recipe" screen
                        finish();

                }

            }
            else {finish();

                }
        }
    });
    }

    @Override
    public void onItemClick(List<Steps> stepsList, int clickedIndex, String recipeName) {

        final StepsFragment stepsFragment = new StepsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setTitle(recipeName);

        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelableArrayList(SELECTED_STEPS, (ArrayList<Steps>) stepsList);
        stepsBundle.putInt(SELECTED_INDEX, clickedIndex);
        stepsBundle.putString("Title", recipeName);
        stepsFragment.setArguments(stepsBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag() != null
                && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container1, stepsFragment)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, stepsFragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        assert savedInstanceState != null;
        savedInstanceState.putString("Title",recipeName);
    }
}