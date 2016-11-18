package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;
import com.brilgo.meanbookandroidapp.api.response.User;

public class MainActivity extends AppCompatActivity {

    public static final String USER_KEY = "MainActivity.USER";

    private MeanBookApi meanBookApi = MeanBookApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        meanBookApi.init(getApplicationContext());

        setContentView(R.layout.activity_main);
        addToolbar();
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void login(View view) {
        EditText loginField = (EditText) findViewById(R.id.login);
        String username = loginField.getText().toString();
        User user = meanBookApi.login(username);
        if (isUserAuthenticated(user)) {
            Intent intent = new Intent(this, TimelineActivity.class);
            intent.putExtra(USER_KEY, user);
            startActivity(intent);
        } else {
            // TODO: Implement an alert to the user.
        }
    }

    private boolean isUserAuthenticated(User user) {
        return user != null && !"".equals(user.username);
    }
}
