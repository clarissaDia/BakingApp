package com.example.android.bakingapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Adapters.DetailsAdapter;
import com.example.android.bakingapp.Models.Ingredients;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Widget.UpdateBakingWidgetService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.android.bakingapp.MainActivity.INDEX_RECIPE;


public class DetailFragment extends Fragment {
    ArrayList<Recipe> recipeArrayList;
    String recipeName;
    public static final String SHARED_PREFS_KEY_INGRED = "SHARED_PREFS_KEY";



    public DetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView detailsRecyclerView;
        final TextView detailTextView;
        recipeArrayList = new ArrayList<>();
        if (savedInstanceState != null){
            recipeArrayList = savedInstanceState.getParcelableArrayList(INDEX_RECIPE);
        }else {
            recipeArrayList = Objects.requireNonNull(getArguments()).getParcelableArrayList(INDEX_RECIPE);
        }

        assert recipeArrayList != null;
        recipeName = recipeArrayList.get(0).getName();
        View rootView = inflater.inflate(R.layout.fragment_detail,container,false);
        detailTextView = (TextView) rootView.findViewById(R.id.tv_recipe_ingredients);
        ArrayList<String> ingredientsForWidget = new ArrayList<>();
        for (Ingredients i : recipeArrayList.get(0).getIngredients()) {
            detailTextView.append("\u2022 " + i.getIngredient() + "\n");
            detailTextView.append("\t\t\t Quantity: " + i.getQuantity().toString() + "\n");
            detailTextView.append("\t\t\t Measure: " + i.getMeasure() + "\n\n");

            ingredientsForWidget.add(i.getIngredient()+"\n"+
                    "Quantity: "+i.getQuantity().toString()+"\n"+
                    "Measure: "+i.getMeasure()+"\n");
            Gson widgetGson = new Gson();
            String widgetJson = widgetGson.toJson(ingredientsForWidget);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SHARED_PREFS_KEY_INGRED,widgetJson).apply();
        }


        detailsRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        detailsRecyclerView.setLayoutManager(linearLayoutManager);

        DetailsAdapter detailsAdapter = new DetailsAdapter((RecipeDetailActivity)getActivity());
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsAdapter.setRecipeDetails(recipeArrayList,getContext());

        UpdateBakingWidgetService.startBakingWidgetService(getContext(),ingredientsForWidget);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(INDEX_RECIPE, recipeArrayList);
        currentState.putString("title", recipeName);
    }
}
