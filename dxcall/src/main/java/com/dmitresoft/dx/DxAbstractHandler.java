package com.dmitresoft.dx;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


abstract class DxAbstractHandler {

    protected void error(@Nullable DxConsumer<Throwable> err, @Nullable Exception e) {
        if (err != null && e != null) {
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

    protected void post(@NonNull DxAction action, DxConsumer<Throwable> err) {
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
