package com.brilgo.meanbookandroidapp.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.brilgo.meanbookandroidapp.BaseActivity;
import com.brilgo.meanbookandroidapp.api.response.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;

import okhttp3.Interceptor;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class ResponseErrorHandlerInterceptor implements Interceptor, ResponseErrorHandler {

    private static final String TAG = "ApiErrorHandler";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Gson GSON = new Gson();

    private BaseActivity currentActivity;
    private boolean showErrorAlert = true;
    private String errorAlertTitle = "Alert";

    public void disableErrorAlert() {
        showErrorAlert = false;
    }

    public void enableErrorAlert() {
        showErrorAlert = true;
    }

    public void setErrorAlertTitle(@NonNull String title) {
        errorAlertTitle = title;
    }

    public void setCurrentActivity(@NonNull BaseActivity activity) {
        currentActivity = activity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        try {
            response = chain.proceed(chain.request());
        } catch (IOException e) {
            throw new RequestApiException("Error during the request execution.", e);
        }

        if (!response.isSuccessful()) {
            try {
                String jsonString = readBodyString(response);
                ErrorResponse error = GSON.fromJson(jsonString, ErrorResponse.class);

                String msg = MessageFormat.format("Error reported by the API service with " +
                        "status code {0} and JSON message {1}.", response.code(), jsonString);
                Log.e(TAG, msg);
                if (showErrorAlert && currentActivity != null) {
                    currentActivity.showOkAlert(errorAlertTitle, error.detail);
                }
            } catch (IOException | JsonSyntaxException e) {
                String msg = MessageFormat.format("Error reported by the API service when trying " +
                        "to handle HTTP response with status code {0}.", response.code());
                Log.e(TAG, msg);
                throw new RequestApiException(msg);
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
