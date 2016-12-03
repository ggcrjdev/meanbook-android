package com.brilgo.meanbookandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.brilgo.meanbookandroidapp.api.RequestApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = ExceptionHandler.class.getSimpleName();
    public static final String EXTRA_EXCEPTION_HANDLER = "EXTRA_EXCEPTION_HANDLER";

    private final Activity context;
    private final Thread.UncaughtExceptionHandler rootHandler;

    ExceptionHandler(Activity context) {
        this.context = context;
        this.rootHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        List<String> acceptedExceptions = new ArrayList<>();
        acceptedExceptions.add(RequestApiException.class.getName());
        if (acceptToHandleException(ex, acceptedExceptions)) {
            Log.e(TAG, ex.getMessage(), ex);

            Intent registerActivity = new Intent(context, ApiActivity.class);
            registerActivity.putExtra(EXTRA_EXCEPTION_HANDLER, ExceptionHandler.class.getName());
            registerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(registerActivity);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {
            rootHandler.uncaughtException(thread, ex);
        }
    }

    private boolean acceptToHandleException(Throwable ex, List<String> acceptedExceptions) {
        boolean handleException = false;
        Throwable nextEx = ex;
        while (nextEx != null) {
            if (acceptedExceptions.contains(nextEx.getClass().getName())) {
                handleException = true;
            }
            if (nextEx instanceof InvocationTargetException) {
                nextEx = ((InvocationTargetException) nextEx).getTargetException();
            } else {
                nextEx = nextEx.getCause();
            }
        }
        return handleException;
    }
}
