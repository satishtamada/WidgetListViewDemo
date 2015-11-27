package com.example.satish.widgetlistviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.adapter.TimeLineAdapter;
import com.example.satish.widgetlistviewdemo.config.AppConfig;
import com.example.satish.widgetlistviewdemo.helper.ListViewFactory;
import com.example.satish.widgetlistviewdemo.model.TimeLine;
import com.example.satish.widgetlistviewdemo.service.WidgetService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TimeLineAdapter.ListDragDropListener {
    private ListView listView;
    public  static ArrayList<TimeLine> timeLineArrayList;
    private TimeLineAdapter timeLineAdapter;
    private final String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    private TextView lblTime;
    private Button button;
    private ImageButton imageButton;
    private RelativeLayout relativeLayout;
    private String et_task;
    private int position_value;
    private WidgetService widgetService;
    private ListViewFactory listViewFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.et_text);
        button = (Button) findViewById(R.id.btn_add);
        lblTime= (TextView) findViewById(R.id.edt_lbl_time);
        relativeLayout = (RelativeLayout) findViewById(R.id.addTagLayout);
        imageButton= (ImageButton) findViewById(R.id.btn_mic);
        //event on voice image button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ToDoNoteVoiceActivity.class);
                TimeLine timeLine=timeLineArrayList.get(position_value);
                i.putExtra("time",timeLine.getTime());
                i.putExtra("task",timeLine.getTask());
                startActivity(i);
            }
        });
        //event on add button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_task = editText.getText().toString();
                TimeLine timeLine = timeLineArrayList.get(position_value);
                timeLine.setTask(et_task);
                timeLineArrayList.remove(position_value);
                timeLineArrayList.add(position_value, timeLine);
                timeLineAdapter.notifyDataSetChanged();
                editText.setText("");
                relativeLayout.setVisibility(View.GONE);
            }
        });
        Log.d(TAG,"main activity");
        //adding timeline objects to timelinearraylist
        timeLineArrayList = new ArrayList<>();
        for (int i = 0; i < AppConfig.task.length; i++) {
            TimeLine timeLine = new TimeLine(AppConfig.task[i], AppConfig.time[i]);
            timeLineArrayList.add(timeLine);
        }
        timeLineAdapter = new TimeLineAdapter(this, timeLineArrayList);
        //set adapter to listview
        listView.setAdapter(timeLineAdapter);
        //
        timeLineAdapter.setListDragDropListener(this);
    }

    @Override
    public void onDropListItem(int position, String task) {
        TimeLine timeLine = timeLineArrayList.get(position);
        timeLine.setTask(task);
        timeLineArrayList.remove(position);
        timeLineArrayList.add(position, timeLine);
        timeLineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickListener(int position) {
        TimeLine timeLine=timeLineArrayList.get(position);
        lblTime.setText(timeLine.getTime());
       //check weather listview visible or not
        if(relativeLayout.getVisibility()!=View.VISIBLE) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else
            relativeLayout.setVisibility(View.GONE);
        position_value = position;
    }

    @Override
    public void onItemLongPressListener(int position) {

        TimeLine timeLine = timeLineArrayList.get(position);
        timeLine.setTask("");
        timeLineArrayList.remove(position);
        timeLineArrayList.add(position, timeLine);
        timeLineAdapter.notifyDataSetChanged();
    }

   }
