package com.wangyuqin.mockito.lesson03;

import com.wangyuqin.mockito.Account;
import com.wangyuqin.mockito.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MockByRunnerTest {
    @Test
    public void testMock() {
        AccountDao accountDao = mock(AccountDao.class);
        Account account = accountDao.findAccount("x","x");
        System.out.println(account);
    }
}
