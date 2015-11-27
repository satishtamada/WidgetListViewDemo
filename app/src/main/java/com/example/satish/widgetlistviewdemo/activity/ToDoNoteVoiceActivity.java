package com.example.satish.widgetlistviewdemo.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satish.widgetlistviewdemo.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Satish on 12-11-2015.
 */
public class ToDoNoteVoiceActivity extends AppCompatActivity {
    private TextView lbl_time,lbl_task;
    private EditText txt_note;
    private ImageButton btn_voice;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_voice);
        lbl_time = (TextView) findViewById(R.id.lbl_time);
        lbl_task = (TextView) findViewById(R.id.lbl_task);
        btn_voice = (ImageButton) findViewById(R.id.btnSpeak);
        txt_note = (EditText) findViewById(R.id.txt_note);
        Intent intent=getIntent();
        lbl_time.setText(intent.getStringExtra("time"));
        lbl_task.setText(intent.getStringExtra("task"));
        txt_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_voice.setVisibility(View.VISIBLE);
            }
        });
        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechToText();
            }
        });
    }
    private void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt_note.setText(result.get(0));
                }
                break;
            }

        }
    }
}
