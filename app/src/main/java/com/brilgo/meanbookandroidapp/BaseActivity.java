package com.brilgo.meanbookandroidapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;

public abstract class BaseActivity extends AppCompatActivity {

    private final MeanBookApi meanBookApi = MeanBookApi.getInstance();
    private final UserDataStore userDataStore = new UserDataStore();

    protected final MeanBookApi api() {
        return meanBookApi;
    }

    protected final UserDataStore dataStore() {
        return userDataStore;
    }

    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layoutResID) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        new ExceptionHandler(this);

        setContentView(layoutResID);
        addDefaultToolbar();
        api().init(getApplicationContext());
        api().errorHandler().setCurrentActivity(this);
    }

    private void addDefaultToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public String getStoredCurrentUsername() {
        return dataStore().getCurrentUsername(getApplicationContext());
    }

    public final void showSnack(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public final void showOkAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create().show();
    }

    public String getStringFromEditText(int viewId) {
        EditText field = (EditText)  findViewById(viewId);
        return field.getText().toString();
    }
}
