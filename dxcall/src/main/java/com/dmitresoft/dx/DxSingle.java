package com.dmitresoft.dx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @param <T>
 */
public class DxSingle<T> extends DxAbstractHandler {

    private final DxSupplier<T> supplier;
    private T defValue = null;

    DxSingle(DxSupplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> DxSingle<T> create(@NonNull DxSupplier<T> supplier) {
        return new DxSingle<>(supplier);
    }

    public DxSingle<T> setDefault(T value) {
        this.defValue = value;
        return this;
    }

    public void subscribe(@NonNull DxConsumer<T> result, @Nullable DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                T value = supplier.get();
                if (value == null && defValue != null) {
                    post(defValue, result, err);
                } else {
                    post(value, result, err);
                }
            } catch (Exception e) {
                error(err, e);
            }
        });
    }

    public void subscribe(@NonNull DxConsumer<T> result) {
        subscribe(result, null);
    }

}
