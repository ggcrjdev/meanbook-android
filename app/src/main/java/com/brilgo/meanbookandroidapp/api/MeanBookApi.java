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

import retrofit2.Response;

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
        Response<User> res = responseFrom(
                endpoint().login(request));
        if (res.isSuccessful()) {
            return res.body();
        } else {
            return User.nullObject();
        }
    }

    public boolean logout(String username) {
        UsernameRequest request = new UsernameRequest(username);
        Response<UsersLogoutResponse> res = responseFrom(
                endpoint().logout(request));
        return res.isSuccessful() && res.body().logggedOut;
    }

    public User getCurrentUser() {
        Response<UsersCurrentResponse> res = responseFrom(
                endpoint().getCurrent());
        if (res.isSuccessful() && res.body().authenticated) {
            return new User(res.body().username);
        } else {
            return User.nullObject();
        }
    }

    public List<User> listOnlineUsers() {
        Response<UsersListResponse> res = responseFrom(
                endpoint().listUsers());
        if (res.isSuccessful()) {
            return res.body().users;
        } else {
            return new ArrayList<>(0);
        }
    }

    public List<Post> listPosts(String username, Integer pageNumber) {
        Response<PostsListResponse> res = responseFrom(
                endpoint().listPosts(username, pageNumber));
        if (res.isSuccessful()) {
            Log.d(TAG, MessageFormat.format("Retrieving {0} posts included by the {1} user.",
                    res.body().postsCount, username));
            return res.body().posts;
        } else {
            return new ArrayList<>(0);
        }
    }

    public boolean addPost(String text) {
        PostsAddRequest request = new PostsAddRequest();
        request.setText(text);
        return responseFrom(endpoint().addPost(request)).isSuccessful();
    }
}
