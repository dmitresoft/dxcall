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

    public static <T> DxSingle<T> call(DxSupplier<T> supplier) {
        return new DxSingle<>(supplier);
    }

    public static <T> DxSingle<T> call(DxSupplier<T> supplier, T def) {
        return new DxSingleDef<>(supplier, def);
    }

    public static DxComplete call(DxAction action) {
        return new DxComplete(action);
    }

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
