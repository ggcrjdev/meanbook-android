package com.brilgo.meanbookandroidapp.api;

import android.content.Context;
import android.util.Log;

import com.brilgo.meanbookandroidapp.api.cookie.PersistentCookieStore;
import com.brilgo.meanbookandroidapp.api.request.PostsAddRequest;
import com.brilgo.meanbookandroidapp.api.request.UsernameRequest;
import com.brilgo.meanbookandroidapp.api.response.Post;
import com.brilgo.meanbookandroidapp.api.response.PostsListResponse;
import com.brilgo.meanbookandroidapp.api.response.User;
import com.brilgo.meanbookandroidapp.api.response.UsersCurrentResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersListResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersLogoutResponse;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class MeanBookApi {

    private static final String TAG = MeanBookApi.class.getSimpleName();
    private static final String API_BASE_URL = "http://10.0.2.2:3000/api/1.0/";

    private static MeanBookApi instance;

    private Retrofit retrofit;
    private MeanBookApiEndpoint meanBookApiEndpoint;

    private MeanBookApi() {
    }

    public static MeanBookApi getInstance() {
        if (instance == null) {
            instance = new MeanBookApi();
        }
        return instance;
    }

    public void init(Context context) {
        if (meanBookApiEndpoint == null) {
            createRetrofit(context);
            meanBookApiEndpoint = retrofit.create(MeanBookApiEndpoint.class);
        }
    }

    private void createRetrofit(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            CookieHandler cookieHandler = new CookieManager(
                    new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    private <RES> RES executeRequest(Call<RES> apiCall) {
        try {
            return apiCall.execute().body();
        } catch (IOException e) {
            throw new RequestApiException("Error during the request execution.", e);
        }
    }

    public User login(String username) {
        UsernameRequest request = new UsernameRequest(username);
        return executeRequest(meanBookApiEndpoint.login(request));
    }

    public boolean logout(String username) {
        UsernameRequest request = new UsernameRequest(username);
        UsersLogoutResponse response = executeRequest(meanBookApiEndpoint.logout(request));
        return response.logggedOut;
    }

    public User getCurrentUser() {
        UsersCurrentResponse response = executeRequest(meanBookApiEndpoint.getCurrent());
        if (response != null && response.authenticated) {
            return new User(response.username);
        } else {
            return null;
        }
    }

    public List<User> listOnlineUsers() {
        UsersListResponse response = executeRequest(meanBookApiEndpoint.listUsers());
        if (response != null) {
            return response.users;
        } else {
            return new ArrayList<>(0);
        }
    }

    public List<Post> listPosts(String username) {
        PostsListResponse response = executeRequest(meanBookApiEndpoint.listPosts(username, 1));
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
        executeRequest(meanBookApiEndpoint.addPost(request));
    }
}
