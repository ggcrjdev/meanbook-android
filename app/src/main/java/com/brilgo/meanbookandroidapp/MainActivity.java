package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.brilgo.meanbookandroidapp.api.response.User;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        loadCurrentUser();
    }

    private void loadCurrentUser() {
        User user = meanBookApi.getCurrentUser();
        validateUserAndStartTimelineActivity(user, null);
    }

    public void login(View view) {
        EditText loginField = (EditText) findViewById(R.id.login_field);
        String username = loginField.getText().toString();
        User user = meanBookApi.login(username);
        validateUserAndStartTimelineActivity(user, view);
    }

    private void validateUserAndStartTimelineActivity(User user, View view) {
        if (isUserValid(user)) {
            userDataStore.addUserData(getApplicationContext(), user);
            Intent intent = new Intent(this, TimelineActivity.class);
            startActivity(intent);
            finish();
        } else if (view != null) {
            showSnack("Authentication failed!", view);
        }
    }

    private boolean isUserValid(User user) {
        return user != null && !"".equals(user.username);
    }
}
