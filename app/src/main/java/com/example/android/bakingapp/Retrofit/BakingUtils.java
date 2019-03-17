package com.example.android.bakingapp.Retrofit;

import com.example.android.bakingapp.Models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingUtils {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getAllRecipes();
}
