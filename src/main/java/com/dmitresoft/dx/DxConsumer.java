package com.dmitresoft.dx;

@FunctionalInterface
public interface DxConsumer<T> {
    void accept(T value) throws Exception;
}
