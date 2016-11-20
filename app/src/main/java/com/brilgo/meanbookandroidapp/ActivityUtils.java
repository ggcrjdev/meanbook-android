package com.brilgo.meanbookandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public final class ActivityUtils {

    private ActivityUtils() {
    }

    public static void addDefaultToolbar(AppCompatActivity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }
}
