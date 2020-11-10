package com.example.guessthenumber;

import android.app.Application;

public class MyApplication extends Application {

    public String start_lim;
    public String end_lim;

    public String getStart_lim() {
        return start_lim;
    }

    public void setStart_lim(String start_lim) {
        this.start_lim = start_lim;
    }

    public String getEnd_lim() {
        return end_lim;
    }

    public void setEnd_lim(String end_lim) {
        this.end_lim = end_lim;
    }
}
