package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;
import com.brilgo.meanbookandroidapp.api.response.User;

public class MainActivity extends AppCompatActivity {

    private MeanBookApi meanBookApi = MeanBookApi.getInstance();
    private UserDataStore userDataStore = new UserDataStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        setContentView(R.layout.activity_main);
        ActivityUtils.addDefaultToolbar(this);

        meanBookApi.init(getApplicationContext());
    }

    public void login(View view) {
        EditText loginField = (EditText) findViewById(R.id.login_field);
        String username = loginField.getText().toString();
        User user = meanBookApi.login(username);
        if (isUserAuthenticated(user)) {
            userDataStore.addUserData(getApplicationContext(), user);
            Intent intent = new Intent(this, TimelineActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(view, "Authentication failed!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isUserAuthenticated(User user) {
        return user != null && !"".equals(user.username);
    }
}
