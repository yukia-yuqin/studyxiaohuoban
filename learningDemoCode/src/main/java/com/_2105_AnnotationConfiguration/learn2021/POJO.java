package com._2105_AnnotationConfiguration.learn2021;

import org.springframework.stereotype.Component;

@Component("user")
public class POJO {
    String name;
    int age;

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
