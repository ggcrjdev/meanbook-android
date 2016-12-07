package com.brilgo.meanbookandroidapp.api;

import android.support.annotation.NonNull;

import com.brilgo.meanbookandroidapp.BaseActivity;

public interface ResponseErrorHandler {

    void disableErrorAlert();

    void enableErrorAlert();

    void setErrorAlertTitle(@NonNull String title);

    void setCurrentActivity(@NonNull BaseActivity activity);
}
