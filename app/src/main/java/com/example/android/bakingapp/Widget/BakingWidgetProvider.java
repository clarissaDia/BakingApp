package com.example.android.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    public static String REMOTE_INGREDIENTS ="REMOTE_INGREDIENTS";
    public static String REMOTE_BUNDLE ="REMOTE_BUNDLE";
    static ArrayList<String> widgetIngredientsList = new ArrayList<>();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_gridview);
       /** Bundle indexRecipe = new Bundle();**/

        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        appIntent.putExtra("indexRecipe",widgetIngredientsList);

        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.baking_widget_gridview,pendingIntent);
        Intent intent = new Intent(context,BakingWidgetService.class);
        views.setRemoteAdapter(R.id.baking_widget_gridview,intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //for (int appWidgetId : appWidgetIds) {
        //updateAppWidget(context, appWidgetManager, appWidgetId);
        //}

    }

    public static void updateBakingWidget (Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,BakingWidgetProvider.class));
        final String action = intent.getAction();
        assert action != null;
        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")){
            widgetIngredientsList = Objects.requireNonNull(intent.getExtras()).getStringArrayList("INGREDIENTS_LIST");
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.baking_widget_gridview);
            BakingWidgetProvider.updateBakingWidget(context,appWidgetManager,appWidgetIds);
            super.onReceive(context, intent);
        }
    }
}
