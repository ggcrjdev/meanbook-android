package com.brilgo.meanbookandroidapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.brilgo.meanbookandroidapp.api.response.User;

public class UserDataStore {

    private static final String USER_DATA = "userData";
    private static final String USERNAME_KEY = "USERNAME_KEY";

    public void addUserData(Context context, User user) {
        SharedPreferences.Editor editor = getUserDataStore(context).edit();
        editor.putString(USERNAME_KEY, user.username).apply();
    }

    public void removeUserData(Context context) {
        SharedPreferences.Editor editor = getUserDataStore(context).edit();
        editor.remove(USERNAME_KEY).apply();
    }

    public String getCurrentUsername(Context context) {
        return getUserDataStore(context).getString(USERNAME_KEY, null);
    }

    private SharedPreferences getUserDataStore(Context context) {
        return context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
    }
}
