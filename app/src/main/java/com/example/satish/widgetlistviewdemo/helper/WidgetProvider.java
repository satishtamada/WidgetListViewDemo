package com.example.satish.widgetlistviewdemo.helper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.activity.AddActivity;
import com.example.satish.widgetlistviewdemo.activity.MainActivity;
import com.example.satish.widgetlistviewdemo.service.WidgetService;

/**
 * Created by Satish on 18-11-2015.
 */
public class WidgetProvider extends AppWidgetProvider {
    public static String TAG = WidgetProvider.class.getSimpleName();
    public static String ACTION_WIDGET_SEARCH = "SearchWidget";
    public static String ACTION_WIDGET_ADD = "AddWidget";
    private ComponentName componentName;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews view = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            componentName = new ComponentName(context, WidgetProvider.class);
            view.setRemoteAdapter(appWidgetIds[i], R.id.widget_list,
                    serviceIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            view.setPendingIntentTemplate(R.id.widget_list, clickPI);

            view.setOnClickPendingIntent(R.id.imgBtnSearch, getPendingSelfIntent(context, ACTION_WIDGET_SEARCH));


            //set add intent on buttons
            Intent addIntent = new Intent(context, AddActivity.class);
            addIntent.setAction(ACTION_WIDGET_ADD);
            addIntent.putExtra("msg", "Message for Button 1");

            //set search intent on button
           // Intent searchIntent = new Intent(context, SearchActivity.class);
           //searchIntent.setAction(ACTION_WIDGET_SEARCH);
           // searchIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);


            PendingIntent actionAddPendingIntent = PendingIntent.getActivity(context, 0, addIntent, 0);
           // PendingIntent actionSearchPendingIntent = PendingIntent.getActivity(context, 0, searchIntent, 0);


            //set event on buttons
            view.setOnClickPendingIntent(R.id.imgBtnAdd, actionAddPendingIntent);
            //view.setOnClickPendingIntent(R.id.imgBtnSearch, actionSearchPendingIntent);


            appWidgetManager.updateAppWidget(componentName, view);
            super.onUpdate(context, appWidgetManager, appWidgetIds);

        }//end of for loop
    }//end of onUpdate

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (ACTION_WIDGET_SEARCH.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews;
            ComponentName watchWidget;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            watchWidget = new ComponentName(context, WidgetProvider.class);
            remoteViews.setImageViewResource(R.id.imgBtnSearch, R.drawable.ic_menu);
            Toast.makeText(context, "Search on view", Toast.LENGTH_LONG).show();
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }
}
