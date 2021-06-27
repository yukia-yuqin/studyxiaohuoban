
//Java中的线程优先级是在Thread类中定义的常量
//        NORM_PRIORITY:值为5
//        MAX_PRIORITY:值为10
//        MIN_PRIORITY:值为1
//
//        缺省优先级为:NORM_PRIORITY
//
//        有关优先级的方法有两个:


class PriorityThread extends Thread {
    private long count = 0; //计数器,统计线程执行的次数
    private boolean isPass = true; //定义一个标志,用来终止循环
    PriorityThread(String name) {
        super(name);
    }
    public void run() {
        while (isPass) { //isPass为假时将中止循环,否则count不断的加1
            count++;
        }
    }
    public long result() { //返回count的值
        return count;
    }
    public void stopThread() { //中止线程
        isPass = false;
    }
}


public class MultiThreadDemo4 {
    public static void main(String[] args) {
        PriorityThread t1 = new PriorityThread("Thread 1");
        PriorityThread t2 = new PriorityThread("Thread 2");
        t1.setPriority(Thread.NORM_PRIORITY + 1); // 设置优先级为6
        t2.setPriority(Thread.NORM_PRIORITY + 3); // 设置优先级为8
        t1.start(); // 启动线程t1
        t2.start(); // 启动线程t2
        try {
            Thread.sleep(500); // 主线程睡眠500毫秒
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Thread 1：Priority is " + t1.getPriority()
                + " Result of Count is: " + t1.result());
        System.out.println("Thread 2：Priority is " + t2.getPriority()
                + " Result of Count is: " + t2.result());
        t1.setPriority(Thread.MAX_PRIORITY); // 重新设置t1的优先级为最大
        try {
            Thread.sleep(500); // 主线程睡眠500毫秒
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        t1.stopThread();//中止线程t1
        t2.stopThread();//中止线程t2
        System.out.println("After the priority of Thread 1 is changed: ");
        System.out.println("Thread 1：Priority is " + t1.getPriority()
                + " Result of Count is: " + t1.result());
        System.out.println("Thread 2：Priority is " + t2.getPriority()
                + " Result of Count is: " + t2.result());
    }
}
