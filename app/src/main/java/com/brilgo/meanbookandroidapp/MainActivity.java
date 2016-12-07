package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.brilgo.meanbookandroidapp.api.response.User;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        api().errorHandler().setErrorAlertTitle(
                getString(R.string.title_error_handler_login));

        loadCurrentUser();
    }

    private void loadCurrentUser() {
        User user = api().getCurrentUser();
        validateUserAndStartTimelineActivity(user);
    }

    public void login(View view) {
        String username = getStringFromEditText(R.id.login_field);
        User user = api().login(username);
        validateUserAndStartTimelineActivity(user);
    }

    private void validateUserAndStartTimelineActivity(User user) {
        if (user.isValid()) {
            dataStore().addUserData(getApplicationContext(), user);
            Intent intent = new Intent(this, TimelineActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
