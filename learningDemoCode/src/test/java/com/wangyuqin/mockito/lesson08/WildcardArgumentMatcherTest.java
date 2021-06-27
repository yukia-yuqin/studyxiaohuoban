package com.wangyuqin.mockito.lesson08;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WildcardArgumentMatcherTest {
    @Mock
    private SimpleService simpleServcie;

    @Test
    public void wildcardMethod1() {
        Collections.emptyList();
        when(simpleServcie.method1(anyInt(), anyString(), anyCollection(), isA(Serializable.class))).thenReturn(100);
        int result = simpleServcie.method1(1,"Alex", Collections.emptyList(),"Mockito");
        assertThat(result, equalTo(100));
    }


    @After
    public void destory() {
        reset(simpleServcie);
    }
}
