package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.android.bakingapp.Widget.BakingWidgetProvider.ingredientsList;

public class BakingWidgetService extends RemoteViewsService {

    ArrayList<String> widgetIngredientsList = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        Context mContext = null;

        public BakingRemoteViewsFactory (Context context, Intent intent){
            mContext = context;
        }


        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = sharedPreferences.getString(MainActivity.SHARED_PREFERENCE_KEY, "");
            if (json.equals("")){
                Gson gson = new Gson();
                ingredientsList = gson.fromJson(json, new TypeToken<ArrayList<String>>(){

                }.getType());
            }
SharedPreferences retrievePreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String retieveJson = retrievePreferences.getString(RecipeFragment.SHARED_PREFERENCE_KEY_INGREDIENTS,"");
            if (retieveJson.equals("")){
                Gson retrieveGson = new Gson();
                widgetIngredientsList = retrieveGson.fromJson(json,new TypeToken<ArrayList<String>>(){

                }.getType());
            }


        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.baking_widget_grid_item);
            remoteViews.setTextViewText(R.id.grid_item,ingredientsList.get(position));
            Intent fillIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.grid_item, fillIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
