package com.brilgo.meanbookandroidapp.api;

import android.util.Log;

import com.brilgo.meanbookandroidapp.api.request.UsernameRequest;
import com.brilgo.meanbookandroidapp.api.response.Post;
import com.brilgo.meanbookandroidapp.api.response.PostsListResponse;
import com.brilgo.meanbookandroidapp.api.response.User;
import com.brilgo.meanbookandroidapp.api.response.UsersLogoutResponse;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeanBookApi {

    private static final String BASE_URL_API = "http://10.0.2.2:3000/api/1.0/";

    private static Retrofit retrofit;
    private static MeanBookApiClient meanBookApiClient;

    public MeanBookApi() {
        init();
    }

    public static void init() {
        if (meanBookApiClient == null) {
            createRetrofit();
            meanBookApiClient = retrofit.create(MeanBookApiClient.class);
        }
    }

    private static void createRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
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
        User response = executeRequest(meanBookApiClient.login(request));
        return response;
    }

    public boolean logout(String username) {
        UsernameRequest request = new UsernameRequest(username);
        UsersLogoutResponse response = executeRequest(meanBookApiClient.logout(request));
        return response.logggedOut;
    }

    public List<Post> listPosts(String username) {
        PostsListResponse response = executeRequest(meanBookApiClient.listPosts(username, 1));
        if (response != null) {
            Log.d(getClass().getName(), MessageFormat.format(
                    "Retrieving {0} posts included by the {1} user.",
                    response.postsCount, username));
            return response.posts;
        } else {
            return new ArrayList<>(0);
        }
    }
}
