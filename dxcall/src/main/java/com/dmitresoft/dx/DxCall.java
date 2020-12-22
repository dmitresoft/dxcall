package com.dmitresoft.dx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DxCall {

    final static ExecutorService executor;
    public static boolean debug = false;

    static {
        executor = Executors.newFixedThreadPool(2);
    }

    static DxAction EMPTY_ACTION = () -> {
    };


    public static void terminate() {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (Exception ignore) {

        }
    }
}
