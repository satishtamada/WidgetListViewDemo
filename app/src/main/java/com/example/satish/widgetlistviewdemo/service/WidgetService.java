package com.example.satish.widgetlistviewdemo.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.satish.widgetlistviewdemo.helper.ListViewFactory;

/**
 * Created by Satish on 16-11-2015.
 */
public class WidgetService extends RemoteViewsService {

       @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
           Log.d("widgetService","widget Service");
        return (new ListViewFactory(this.getApplicationContext(), intent));
    }

}