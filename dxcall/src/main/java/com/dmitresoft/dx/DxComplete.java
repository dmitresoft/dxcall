package com.dmitresoft.dx;

public class DxComplete extends DxAbstractHandler {

    private final DxAction action;

    DxComplete(DxAction action) {
        this.action = action;
    }

    public void subscribe(DxAction result, DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                action.execute();
                post(result, err);
            } catch (Exception e) {
                error(err, e);
            }
        });
    }

    public void subscribe(DxAction result) {
        subscribe(result, null);
    }

}
