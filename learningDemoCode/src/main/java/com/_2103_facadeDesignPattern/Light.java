package com._2103_facadeDesignPattern;

public class Light {
    //   使用单例模式。饿汉式
    private static Light instance = new Light();
    public static Light getInstance() {
        return instance;
    }
    public void on(){
        System.out.println("Light on");
    }
    public void off(){
        System.out.println("Light off");
    }
    public void bright(){
        System.out.println("Light is bright");
    }
    public void dim(){
        System.out.println("Light dim");
    }
}
