package com.dmitresoft.dx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DxComplete extends DxAbstractHandler {

    private final DxAction action;

    DxComplete(@NonNull DxAction action) {
        this.action = action;
    }

    public static DxComplete create(@NonNull DxAction action) {
        return new DxComplete(action);
    }

    public void subscribe(@NonNull DxAction result, @Nullable DxConsumer<Throwable> err) {
        DxCall.executor.submit(() -> {
            try {
                action.execute();
                post(result, err);
            } catch (Exception e) {
                error(err, e);
            }
        });
    }

    public void subscribe(@NonNull DxAction result) {
        subscribe(result, null);
    }

    public void subscribe() {
        subscribe(DxCall.EMPTY_ACTION, null);
    }

}
