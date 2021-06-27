package com.wangyuqin.mockito.lesson10;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleTest {

    @Test
    public void test(){
        boolean great = false;
        Integer actual = 10;
        Integer value = 12;
        System.out.println( great ? (Integer) actual > (Integer) value : (Integer) actual <(Integer) value);
        assertThat(10, GreaterThan.gt(3));
        assertThat(10, GreaterThan.gt(12));
//        assertThat(12, both(get(10)).and(13));
    }
}
