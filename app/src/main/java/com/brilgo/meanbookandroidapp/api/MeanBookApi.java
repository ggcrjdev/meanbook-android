package com.brilgo.meanbookandroidapp.api;

import android.util.Log;

import com.brilgo.meanbookandroidapp.api.request.PostsAddRequest;
import com.brilgo.meanbookandroidapp.api.request.UsernameRequest;
import com.brilgo.meanbookandroidapp.api.response.Post;
import com.brilgo.meanbookandroidapp.api.response.PostsListResponse;
import com.brilgo.meanbookandroidapp.api.response.User;
import com.brilgo.meanbookandroidapp.api.response.UsersCurrentResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersListResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersLogoutResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public final class MeanBookApi extends BaseRetrofitApi<MeanBookApiEndpoint> {

    private static final String TAG = MeanBookApi.class.getSimpleName();
    private static final String API_BASE_URL = "http://10.0.2.2:3000/api/1.0/";

    private static MeanBookApi instance;

    private MeanBookApi() {
        super(API_BASE_URL, MeanBookApiEndpoint.class);
    }

    public static MeanBookApi getInstance() {
        if (instance == null) {
            instance = new MeanBookApi();
        }
        return instance;
    }

    public User login(String username) {
        UsernameRequest request = new UsernameRequest(username);
        return executeRequest(endpoint().login(request));
    }

    public boolean logout(String username) {
        UsernameRequest request = new UsernameRequest(username);
        UsersLogoutResponse response = executeRequest(endpoint().logout(request));
        return response.logggedOut;
    }

    public User getCurrentUser() {
        UsersCurrentResponse response = executeRequest(endpoint().getCurrent());
        if (response != null && response.authenticated) {
            return new User(response.username);
        } else {
            return null;
        }
    }

    public List<User> listOnlineUsers() {
        UsersListResponse response = executeRequest(endpoint().listUsers());
        if (response != null) {
            return response.users;
        } else {
            return new ArrayList<>(0);
        }
    }

    public List<Post> listPosts(String username, Integer pageNumber) {
        PostsListResponse response = executeRequest(endpoint().listPosts(username, pageNumber));
        if (response != null) {
            Log.d(TAG, MessageFormat.format("Retrieving {0} posts included by the {1} user.",
                    response.postsCount, username));
            return response.posts;
        } else {
            return new ArrayList<>(0);
        }
    }

    public void addPost(String text) {
        PostsAddRequest request = new PostsAddRequest();
        request.setText(text);
        executeRequest(endpoint().addPost(request));
    }
}
