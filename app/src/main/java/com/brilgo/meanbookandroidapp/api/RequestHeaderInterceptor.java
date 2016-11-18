package com.brilgo.meanbookandroidapp.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "MeanBookAndroidApp");
        return chain.proceed(builder.build());
    }
}
