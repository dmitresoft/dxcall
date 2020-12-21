package com.dmitresoft.dx;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


abstract class DxAbstractHandler {

    protected void error(DxConsumer<Throwable> err, Exception e) {
        if (err != null) {
            new Handler(Looper.getMainLooper()).post(
                    () -> {
                        try {
                            err.accept(e);
                        } catch (Exception ignore) {
                            if (DxCall.debug) {
                                Log.e("dx", "falied to process error", e);
                            }
                        }
                    }
            );
        }
    }

    protected void post(DxAction action, DxConsumer<Throwable> err) {
        new Handler(Looper.getMainLooper()).post(
                () -> {
                    try {
                        action.execute();
                    } catch (Exception e) {
                        error(err, e);
                    }
                }
        );
    }

    protected <T> void post(T value, DxConsumer<T> answer, DxConsumer<Throwable> err) {
        new Handler(Looper.getMainLooper()).post(
                () -> {
                    try {
                        answer.accept(value);
                    } catch (Exception e) {
                        error(err, e);
                    }
                }
        );
    }

}
