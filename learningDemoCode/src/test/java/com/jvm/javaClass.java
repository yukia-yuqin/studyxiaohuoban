package com.jvm;

public class javaClass {
    private int m;

    public int inc() {
        return m + 1;
    }
}

//        Classfile /D:/data/code/learnMock/src/test/java/com.jvm/javaClass.class
//Last modified 2020-11-26; size 279 bytes
//        MD5 checksum 18852bda7cab2c77544c800dc0c17bc1
//        Compiled from "javaClass.java"
//public class com.jvm.javaClass
//        minor version: 0
//        major version: 52
//        flags: ACC_PUBLIC, ACC_SUPER
//        Constant pool:
//        #1 = Methodref          #4.#15         // java/lang/Object."<init>":()V
//        #2 = Fieldref           #3.#16         // com.jvm/javaClass.m:I
//        #3 = Class              #17            // com.jvm/javaClass
//        #4 = Class              #18            // java/lang/Object
//        #5 = Utf8               m
//        #6 = Utf8               I
//        #7 = Utf8               <init>
//   #8 = Utf8               ()V
//           #9 = Utf8               Code
//           #10 = Utf8               LineNumberTable
//           #11 = Utf8               inc
//           #12 = Utf8               ()I
//           #13 = Utf8               SourceFile
//           #14 = Utf8               javaClass.java
//           #15 = NameAndType        #7:#8          // "<init>":()V
//           #16 = NameAndType        #5:#6          // m:I
//           #17 = Utf8               com.jvm/javaClass
//           #18 = Utf8               java/lang/Object
//           {
//public com.jvm.javaClass();
//        descriptor: ()V
//        flags: ACC_PUBLIC
//        Code:
//        stack=1, locals=1, args_size=1
//        0: aload_0
//        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//        4: return
//        LineNumberTable:
//        line 3: 0
//
//public int inc();
//
//        flags: ACC_PUBLIC
//        Code:
//        stack=2, locals=1, args_size=1
//        0: aload_0
//        1: getfield      #2                  // Field m:I
//        4: iconst_1
//        5: iadd
//        6: ireturn
//        LineNumberTable:
//        line 7: 0
//        }
//        SourceFile: "javaClass.java"
