package com.jvm;

import org.junit.Test;

//-ea -verbose:gc -XX:+PrintGCDetails
public class ReferenceCounting {
    public Object instance = null;
    private static final int _1MB = 1024*1024;
    private byte[] bigSize = new byte[2 * _1MB];

    @Test
    public void test(){
        ReferenceCounting objA = new ReferenceCounting();
        ReferenceCounting objB = new ReferenceCounting();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        System.gc();
    }
//    [GC (System.gc()) [PSYoungGen: 14688K->3864K(37888K)] 14688K->3872K(123904K), 0.0093610 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//    [Full GC (System.gc()) [PSYoungGen: 3864K->0K(37888K)] [ParOldGen: 8K->3640K(86016K)] 3872K->3640K
//      (123904K), [Metaspace: 5337K->5337K(1056768K)], 0.0082020 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//    Heap
//    PSYoungGen      total 37888K, used 655K [0x00000000d6100000, 0x00000000d8b00000, 0x0000000100000000)
//    eden space 32768K, 2% used [0x00000000d6100000,0x00000000d61a3d98,0x00000000d8100000)
//    from space 5120K, 0% used [0x00000000d8100000,0x00000000d8100000,0x00000000d8600000)
//    to   space 5120K, 0% used [0x00000000d8600000,0x00000000d8600000,0x00000000d8b00000)
//    ParOldGen       total 86016K, used 3640K [0x0000000082200000, 0x0000000087600000, 0x00000000d6100000)
//    object space 86016K, 4% used [0x0000000082200000,0x000000008258e278,0x0000000087600000)
//    Metaspace       used 5351K, capacity 5504K, committed 5632K, reserved 1056768K
//    class space    used 612K, capacity 659K, committed 768K, reserved 1048576K

}
