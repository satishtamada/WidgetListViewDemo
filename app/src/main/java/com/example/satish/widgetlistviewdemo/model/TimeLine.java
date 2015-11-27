package com.example.satish.widgetlistviewdemo.model;

/**
 * Created by Satish on 09-11-2015.
 */
public class TimeLine {
    private String task,time;

    public TimeLine() {
    }

    public TimeLine( String task ,String time) {

        this.task = task;
        this.time= time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
