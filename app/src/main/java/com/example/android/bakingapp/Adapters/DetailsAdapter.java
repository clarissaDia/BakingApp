package com.example.android.bakingapp.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Steps;
import com.example.android.bakingapp.R;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private List<Steps> mSteps;
    private String mRecipeName;
    final private ListClickListener mListClickListener;

    public interface ListClickListener {
        void onItemClick (List<Steps> stepsList, int clickedIndex, String recipeName);

    }

    public DetailsAdapter(ListClickListener itemClicked){
        mListClickListener = itemClicked;
    }

    public void setRecipeDetails (List<Recipe> selectedRecipe, Context context){
        mSteps = selectedRecipe.get(0).getSteps();
        mRecipeName = selectedRecipe.get(0).getName();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.detail_list_item, viewGroup,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int position) {
        detailsViewHolder.description.setText(mSteps.get(position).getId()+". "+
                mSteps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description;

        public DetailsViewHolder (View view){
            super(view);
            description = (TextView) view.findViewById(R.id.tv_shortDescription);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedIndex  = getAdapterPosition();
            mListClickListener.onItemClick(mSteps,clickedIndex,mRecipeName);

        }
    }
}
