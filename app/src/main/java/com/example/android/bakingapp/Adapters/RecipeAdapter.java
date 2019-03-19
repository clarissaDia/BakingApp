package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {
    ArrayList<Recipe> mRecipes;
    Context mContext;
    final private ClickListenter clickListenter;


    public interface ClickListenter {
        void onItemiClick (Recipe index);
    }

    public RecipeAdapter (ClickListenter listenter){
        clickListenter = listenter;
    }

    public void setRecipes(ArrayList<Recipe> recipes, Context context){
        mRecipes = recipes;
        mContext= context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(R.layout.recipe_list_item, viewGroup, attachToParentImmediately);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.titleTextView.setText(mRecipes.get(position).getName());
        String imageUrl = mRecipes.get(position).getImage();

if(imageUrl!=""){
    Uri uri = Uri.parse(imageUrl).buildUpon().build();
    Picasso.get().load(uri).into(recipeViewHolder.imageView);
}

    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();

    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleTextView;
        ImageView imageView;

        public RecipeViewHolder(View view){
super(view);
titleTextView = (TextView) view.findViewById(R.id.tv_title);
imageView = (ImageView) view.findViewById(R.id.iv_recipe);
view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListenter.onItemiClick(mRecipes.get(clickedPosition));

        }
    }
}