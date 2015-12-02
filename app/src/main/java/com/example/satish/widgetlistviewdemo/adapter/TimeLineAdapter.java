package com.example.satish.widgetlistviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.model.TimeLine;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Satish on 09-11-2015.
 */
public class TimeLineAdapter extends BaseAdapter {

    private String TAG  = TimeLineAdapter.class.getSimpleName();
    private Context mContext;
    final int INVALID_ID = -1;
    private ArrayList<TimeLine> timeLineArrayList;

    HashMap<Integer, Integer> mIdMap = new HashMap<Integer, Integer>();

    public TimeLineAdapter(Context context, ArrayList<TimeLine> timeLineArrayList) {
        super();
        mContext = context;
        this.timeLineArrayList = timeLineArrayList;

        for (int i = 0; i < timeLineArrayList.size(); ++i) {
            mIdMap.put(i, i);
        }

    }

    @Override
    public long getItemId(int position) {

        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        //Log.e(TAG, "getItemId: " + position + ", item: " + item);
        return mIdMap.get(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public int getCount() {
        return mIdMap.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item, null);

        // get the reference of textViews
        TextView textViewConatctNumber = (TextView) view.findViewById(R.id.txt_task);
        TextView txtTime = (TextView) view.findViewById(R.id.txt_time);

        // Set the Sender number and smsBody to respective TextViews
        textViewConatctNumber.setText(timeLineArrayList.get(position).getTask());
        txtTime.setText(timeLineArrayList.get(position).getTime());

        return view;
    }

    public String getItem(int position) {
        return timeLineArrayList.get(position).getTask();
    }
}
