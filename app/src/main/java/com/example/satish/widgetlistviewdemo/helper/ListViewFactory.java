package com.example.satish.widgetlistviewdemo.helper;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.config.AppConfig;

/**
 * Created by Satish on 20-11-2015.
 */
public class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private int appWidgetId;
       private static final String TAG = ListViewFactory.class.getSimpleName();

    public ListViewFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d("oncreta","date");
    }

    @Override
    public void onDestroy() {
        Log.d("oncreta","dest");
    }

    @Override
    public int getCount() {
        return AppConfig.time.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews widgetViewRow = new RemoteViews(context.getPackageName(),
                R.layout.widget_list_item);

        //set text to list items
        widgetViewRow.setTextViewText(R.id.time, AppConfig.time[position]);
        widgetViewRow.setTextViewText(R.id.task, AppConfig.task[position]);

        Intent i = new Intent();
        Bundle extras = new Bundle();
        extras.putString(WidgetProvider.TAG, AppConfig.time[position]);

        i.putExtras(extras);
        widgetViewRow.setOnClickFillInIntent(R.id.listItemLayout,i);
        widgetViewRow.setOnClickFillInIntent(R.id.task, i);
        widgetViewRow.setOnClickFillInIntent(R.id.time, i);


        return (widgetViewRow);
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
