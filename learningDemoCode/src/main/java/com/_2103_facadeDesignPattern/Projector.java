package com._2103_facadeDesignPattern;

public class Projector {
    //   使用单例模式。饿汉式
    private static Projector instance = new Projector();
    public static Projector getInstance() {
        return instance;
    }
    public void on(){
        System.out.println("Projector on");
    }
    public void off(){
        System.out.println("Projector off");
    }
    public void focus(){
        System.out.println("Projector is focusing");
    }

}
