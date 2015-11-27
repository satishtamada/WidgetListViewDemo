package com.example.satish.widgetlistviewdemo.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.activity.MainActivity;
import com.example.satish.widgetlistviewdemo.model.TimeLine;

import java.util.ArrayList;


/**
 * Created by Satish on 09-11-2015.
 */
public class TimeLineAdapter extends BaseAdapter {
    private ListDragDropListener listDragDropListener;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<TimeLine> timeLineArrayList;
    private String data_task;
    private int itemLongPressPosition;


    public TimeLineAdapter(Activity activity, ArrayList<TimeLine> timeLineArrayList) {
        this.activity = activity;
        this.timeLineArrayList = timeLineArrayList;

    }

    public void setListDragDropListener(MainActivity listDragDropListener) {
        this.listDragDropListener = listDragDropListener;
    }

    @Override
    public int getCount() {
        return timeLineArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeLineArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            view = inflater.inflate(R.layout.list_item, null);

        final ToggleButton imgbtnCheck = (ToggleButton) view.findViewById(R.id.img_check);
        imgbtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: ON CHECKBOX BUTTON ACTIONS
            }
        });

        final TextView lblTask = (TextView) view.findViewById(R.id.txt_task);
        final TextView lblTime = (TextView) view.findViewById(R.id.txt_time);
        final TimeLine timeLine = timeLineArrayList.get(position);

        lblTime.setText(timeLine.getTime());
        lblTask.setText(timeLine.getTask());
        view.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
        //list item long click action
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongPressPosition = position;
                TimeLine timeLine = timeLineArrayList.get(position);
                if (timeLine.getTask().length() != 0) {
                    ClipData.Item item = new ClipData.Item(timeLine.getTask());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    data_task = (String) item.getText();
                    ClipData data = new ClipData(timeLine.getTask(), mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, //data to be dragged
                            shadowBuilder, //drag shadow
                            v, //local data about the drag and drop operation
                            0   //no needed flags
                    );

                    return true;
                } else return false;
            }
        });
        //list item click action
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDragDropListener.onItemClickListener(position);
            }
        });
        //list item drag action
        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        lblTask.setTextSize(25);//set zoom to item
                        lblTime.setTextSize(25);
                        v.setBackgroundColor(activity.getResources().getColor(R.color.colorSelect));//set background color to list item
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i("Drag ", "exited");   //change the shape of the view back to normal
                        lblTask.setTextSize(15);    //set original size to list item
                        lblTime.setTextSize(15);
                        v.setBackgroundColor(activity.getResources().getColor(R.color.colorWhite));
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i("Drag ", "drop");
                        lblTask.setTextSize(15);
                        lblTime.setTextSize(15);
                        listDragDropListener.onDropListItem(position, data_task);//here pass the item position and task value
                        listDragDropListener.onItemLongPressListener(itemLongPressPosition);
                        v.setBackgroundColor(activity.getResources().getColor(R.color.colorWhite));
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("Drag ", "drag ended");  //go back to normal shape/**/
                    default:
                        break;
                }
                return true;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        return view;
    }


    @Override
    public int getViewTypeCount() {
        return (1);
    }

    public interface ListDragDropListener {

        void onDropListItem(int position, String task);

        void onItemClickListener(int position);

        void onItemLongPressListener(int position);


    }
}
