package com.example.android.bakingapp.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class UpdateBakingWidgetService extends IntentService {
    public static String ACTION_BAKING_INGREDIENTS =
            "FROM_ACTIVITY_INGREDIENTS_LIST";
    public UpdateBakingWidgetService(){
        super("UpdateBakingWidgetService");
    }

    public static void startBakingWidgetService (Context context, ArrayList<String> ingredientsList){
        Intent intent = new Intent(context, UpdateBakingWidgetService.class);
        intent.putExtra(ACTION_BAKING_INGREDIENTS, ingredientsList);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent !=  null){
            ArrayList<String> ingredientsList = intent.getExtras().getStringArrayList(ACTION_BAKING_INGREDIENTS);
actionUpdateBakingWidgetService(ingredientsList);
        }

    }

    private void actionUpdateBakingWidgetService (ArrayList<String> ingredientsList){
       //* Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
       //* intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
       //* intent.putExtra("INGREDIENTS_LIST", ingredientsList);
       //* sendBroadcast(intent);

        Intent intent = new Intent(this, BakingWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(ACTION_BAKING_INGREDIENTS,ingredientsList);
        sendBroadcast(intent);
    }
}
