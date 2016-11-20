package com.brilgo.meanbookandroidapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;

public abstract class BaseActivity extends AppCompatActivity {

    final MeanBookApi meanBookApi = MeanBookApi.getInstance();
    final UserDataStore userDataStore = new UserDataStore();

    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layoutResID) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        new ExceptionHandler(this);

        setContentView(layoutResID);
        addDefaultToolbar();
        meanBookApi.init(getApplicationContext());
        meanBookApi.setCurrentActivity(this);
    }

    private void addDefaultToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public String getStoredCurrentUsername() {
        return userDataStore.getCurrentUsername(getApplicationContext());
    }

    public void showSnack(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public void showOkAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create().show();
    }
}
