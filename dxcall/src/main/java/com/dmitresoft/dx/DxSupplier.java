package com.dmitresoft.dx;

@FunctionalInterface
public interface DxSupplier<T> {
    T get() throws Exception;
}
