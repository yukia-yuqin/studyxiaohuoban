package com.wangyuqin.mockito.lesson08;

import jdk.internal.instrumentation.InstrumentationTarget;

import java.io.Serializable;
import java.util.Collection;

public class SimpleService {
    public int method1(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }

    public int method2(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }
}
