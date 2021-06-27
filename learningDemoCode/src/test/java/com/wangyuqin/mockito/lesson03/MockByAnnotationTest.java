package com.wangyuqin.mockito.lesson03;

import com.wangyuqin.mockito.Account;
import com.wangyuqin.mockito.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MockByAnnotationTest {
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private AccountDao accoutnDao;

    @Test
    public void testMock() {
        Account account = accoutnDao.findAccount("x", "x");
        System.out.println(account);
    }
}
