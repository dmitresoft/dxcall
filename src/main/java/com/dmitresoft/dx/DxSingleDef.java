package com.dmitresoft.dx;

public class DxSingleDef<T> extends DxSingle<T> {

    private final DxSupplier<T> supplier;
    private final T def;

    DxSingleDef(DxSupplier<T> supplier, T def) {
        super(supplier);
        this.supplier = supplier;
        this.def = def;
    }

    @Override
    public void subscribe(DxConsumer<T> answer, DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                T result = supplier.get();
                post(result, answer, err);
            } catch (Exception e) {
                post(def, answer, err);
            }
        });
    }

}
