package com.dmitresoft.dx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @param <T>
 */
public final class DxMaybe<T> extends DxAbstractHandler {

    private final DxSupplier<T> supplier;

    DxMaybe(DxSupplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> DxMaybe<T> create(DxSupplier<T> call) {
        return new DxMaybe<>(call);
    }

    public void subscribe(@NonNull DxConsumer<T> result, @NonNull DxAction empty, @Nullable DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                T value = supplier.get();
                if (result != null) {
                    post(value, result, err);
                } else {
                    post(empty, err);
                }
            } catch (Exception e) {
                error(err, e);
            }
        });
    }

    public void subscribe(@NonNull DxConsumer<T> result, @NonNull DxAction empty) {
        subscribe(result, empty, null);
    }

    public void subscribe(@NonNull DxConsumer<T> result, @NonNull DxConsumer<Throwable> err) {
        subscribe(result, DxCall.EMPTY_ACTION, err);
    }

    public void subscribe(@NonNull DxConsumer<T> result) {
        subscribe(result, DxCall.EMPTY_ACTION, null);
    }

}
