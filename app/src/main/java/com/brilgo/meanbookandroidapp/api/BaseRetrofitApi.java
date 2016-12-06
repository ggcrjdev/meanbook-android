package com.brilgo.meanbookandroidapp.api;

import android.content.Context;

import com.brilgo.meanbookandroidapp.BaseActivity;
import com.brilgo.meanbookandroidapp.api.cookie.PersistentCookieStore;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofitApi<E> {

    private Retrofit retrofit;
    private ResponseErrorHandleInterceptor responseErrorHandle;

    private String apiBaseUrl;
    private Class<E> endpointClass;
    private E apiEndpoint;

    BaseRetrofitApi(String apiBaseUrl, Class<E> endpointClass) {
        this.apiBaseUrl = apiBaseUrl;
        this.endpointClass = endpointClass;
    }

    E endpoint() {
        return apiEndpoint;
    }

    <RES> Response<RES> getResponse(Call<RES> apiCall) {
        try {
            return apiCall.execute();
        } catch (IOException e) {
            throw new RequestApiException("Error during the request execution.", e);
        }
    }

    <RES> RES getResponseBody(Call<RES> apiCall) {
        return getResponse(apiCall).body();
    }

    public void init(Context context) {
        if (apiEndpoint == null) {
            createRetrofit(context);
            apiEndpoint = retrofit.create(endpointClass);
        }
    }

    private void createRetrofit(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            responseErrorHandle = new ResponseErrorHandleInterceptor();
            CookieHandler cookieHandler = new CookieManager(
                    new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(responseErrorHandle)
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(apiBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public void setCurrentActivity(BaseActivity baseActivity) {
        responseErrorHandle.setCurrentActivity(baseActivity);
    }
}
