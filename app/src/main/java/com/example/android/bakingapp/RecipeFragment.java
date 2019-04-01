package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Retrofit.BakingUtils;
import com.example.android.bakingapp.Retrofit.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeFragment extends Fragment {

    public static final String SHARED_PREFERENCE_KEY_INGREDIENTS = "SHARED_PREFERENCE_KEY_INGREDIENTS";
    public RecipeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView mRecyclerView;

        View rootView = inflater.inflate(R.layout.fragment_recipe, container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe);
        final RecipeAdapter recipeAdapter = new RecipeAdapter((MainActivity)getActivity());
        mRecyclerView.setAdapter(recipeAdapter);

        if (rootView.getTag()!= null && rootView.getTag().equals("phone-land")){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }else {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);}

        BakingUtils bakingUtils = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipes = bakingUtils.getAllRecipes();


recipes.enqueue(new Callback<ArrayList<Recipe>>() {
    @Override
    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
        Integer statusCode = response.code();
        Log.v("status code: ", statusCode.toString());

        ArrayList<Recipe> recipeArrayList = response.body();
        Bundle recipesBundle = new Bundle();
        recipesBundle.putParcelableArrayList("All_Recipes", recipeArrayList);
        recipeAdapter.setRecipes(recipeArrayList,getContext());
    }

    @Override
    public void onFailure(Call<ArrayList<Recipe>> call, Throwable throwable) {
        Log.v("http fail: ", throwable.getMessage());

    }
});
        return rootView;
    }

}
