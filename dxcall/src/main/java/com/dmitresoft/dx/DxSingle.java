package com.dmitresoft.dx;

public class DxSingle<T> extends DxAbstractHandler {

    private final DxSupplier<T> supplier;

    DxSingle(DxSupplier<T> supplier) {
        this.supplier = supplier;
    }

    public void subscribe(DxConsumer<T> answer, DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                T result = supplier.get();
                post(result, answer, err);
            } catch (Exception e) {
                error(err, e);
            }
        });
    }

    public void subscribe(DxConsumer<T> result) {
        subscribe(result, null);
    }

}
