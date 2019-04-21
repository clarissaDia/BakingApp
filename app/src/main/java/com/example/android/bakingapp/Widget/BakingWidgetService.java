package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;

import java.util.List;

import static com.example.android.bakingapp.Widget.BakingWidgetProvider.widgetIngredientsList;

public class BakingWidgetService extends RemoteViewsService {

    List<String> remoteIngredients;

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
            remoteIngredients = widgetIngredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.baking_widget_grid_item);
            remoteViews.setTextViewText(R.id.grid_item,remoteIngredients.get(position));
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
