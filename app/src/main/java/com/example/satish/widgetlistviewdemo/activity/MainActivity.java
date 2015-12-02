package com.example.satish.widgetlistviewdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.satish.widgetlistviewdemo.R;
import com.example.satish.widgetlistviewdemo.adapter.DynamicListView;
import com.example.satish.widgetlistviewdemo.adapter.TimeLineAdapter;
import com.example.satish.widgetlistviewdemo.config.AppConfig;
import com.example.satish.widgetlistviewdemo.model.TimeLine;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DynamicListView.DynamicListViewListener, AdapterView.OnItemClickListener {

    private String TAG = MainActivity.class.getSimpleName();
    private DynamicListView listView;
    public static ArrayList<TimeLine> timeLineArrayList;
    private EditText editText;
    private TextView lblTime;
    private Button button;
    private ImageButton imageButton;
    private RelativeLayout relativeLayout;
    private String et_task;
    private int position_value;
    private TimeLineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (DynamicListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.et_text);
        button = (Button) findViewById(R.id.btn_add);
        lblTime = (TextView) findViewById(R.id.edt_lbl_time);
        relativeLayout = (RelativeLayout) findViewById(R.id.addTagLayout);
        imageButton = (ImageButton) findViewById(R.id.btn_mic);
        //event on voice image button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ToDoNoteVoiceActivity.class);
                TimeLine timeLine = timeLineArrayList.get(position_value);
                i.putExtra("time", timeLine.getTime());
                i.putExtra("task", timeLine.getTask());
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
                timeLineArrayList.set(position_value, timeLine);
                mAdapter.notifyDataSetChanged();
                editText.setText("");
                relativeLayout.setVisibility(View.GONE);

                // hide the keyboard
                hideSoftKeyboard();
            }
        });

        timeLineArrayList = new ArrayList<>();
        for (int i = 0; i < AppConfig.task.length; i++) {
            TimeLine timeLine = new TimeLine(AppConfig.task[i], AppConfig.time[i]);
            timeLineArrayList.add(timeLine);
        }

        mAdapter = new TimeLineAdapter(this, timeLineArrayList);

        listView.setDynamicListViewListener(this);
        listView.setCheeseList(timeLineArrayList);
        listView.setAdapter(mAdapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(this);
    }


    @Override
    public void onRowSwap(int fromPosition, int toPosition) {
        Log.e(TAG, "onRowSwap: " + fromPosition + ", " + toPosition);

        TimeLine fromTask = timeLineArrayList.get(fromPosition);

        TimeLine toTask = timeLineArrayList.get(toPosition);
        toTask.setTask(fromTask.getTask());

        fromTask.setTask("");

        timeLineArrayList.set(fromPosition, fromTask);
        timeLineArrayList.set(toPosition, toTask);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TimeLine timeLine = timeLineArrayList.get(position);
        lblTime.setText(timeLine.getTime());
        //check weather list view visible or not
        if (relativeLayout.getVisibility() != View.VISIBLE) {
            relativeLayout.setVisibility(View.VISIBLE);

            // show keyboard
            if(editText.requestFocus()){
                showSoftKeyboard();
            }

        } else {
            relativeLayout.setVisibility(View.GONE);
            hideSoftKeyboard();
        }

        position_value = position;
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
}
