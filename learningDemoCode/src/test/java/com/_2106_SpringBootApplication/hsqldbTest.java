package com._2106_SpringBootApplication;

import com._2106_SpringBootApplication.yqSpringBoot.AutoConfiguration;
import com._2106_SpringBootApplication.yqSpringBoot.User;
import com._2106_SpringBootApplication.yqSpringBoot.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoConfiguration.class)
public class hsqldbTest {
    public hsqldbTest() {
        return ;
    }
    @PostConstruct
    void po(){
        return;
    }
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindingCustomerById_thenCorrect() {
        userRepository.save(new User("John", "john@domain.com"));
        assertTrue(userRepository.findById(1L).isPresent());
    }

    @Test
    public void whenSavingCustomer_thenCorrect() {
        userRepository.save(new User("Bob", "bob@domain.com"));
        User user = userRepository.findById(1L).orElseGet(()
                -> new User("john", "john@domain.com"));
        assertTrue(user.getName().equals("Bob"));
    }
}