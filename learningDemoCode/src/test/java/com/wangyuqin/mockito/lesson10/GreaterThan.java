package com.wangyuqin.mockito.lesson10;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

import java.util.concurrent.atomic.AtomicInteger;

public class GreaterThan<T extends Number> extends BaseMatcher<T> {
    private final T value;
    public GreaterThan(T value) {
        this.value = value;
    }

    @Override
    public boolean matches(Object actual) {
        Class<?> clazz = actual.getClass();
        if (clazz == Integer.class) {
            return (Integer) actual > (Integer) value;
        } else if (clazz == Short.class) {
            return (Short) actual > (Short) value;
        } else if (clazz == Byte.class) {
            return (Byte) actual > (Byte) value;
        }else if (clazz == Double.class) {
            return (Double) actual > (Double) value;
        }else if (clazz == Double.class) {
            return (Double) actual > (Double) value;
        } else {
            throw new AssertionError("The number type " + clazz + " not supported");
        }
    }

    @Factory
    public static<T extends Number> GreaterThan<T> gt(T value) {
        return new GreaterThan<>(value);
    }
    @Override
    public void describeTo(Description description) {
        description.appendText("compare two number failed");
    }
}
