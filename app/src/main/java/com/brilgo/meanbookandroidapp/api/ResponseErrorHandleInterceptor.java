package com.brilgo.meanbookandroidapp.api;

import android.support.annotation.NonNull;

import com.brilgo.meanbookandroidapp.BaseActivity;
import com.brilgo.meanbookandroidapp.api.response.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class ResponseErrorHandleInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Gson GSON = new Gson();

    private BaseActivity currentActivity;

    public void setCurrentActivity(BaseActivity activity) {
        currentActivity = activity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() >= 500) {
            try {
                String jsonString = readBodyString(response);
                ErrorResponse error = GSON.fromJson(jsonString, ErrorResponse.class);
                if (currentActivity != null && !currentActivity.isFinishing()) {
                    currentActivity.showOkAlert("Alert", error.detail);
                }
            } catch (JsonSyntaxException e) {
                throw new RequestApiException("Error reported by the API service.");
            }
        }
        return response;
    }

    @NonNull
    private String readBodyString(Response response) throws IOException {
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        return buffer.clone().readString(UTF8);
    }
}
