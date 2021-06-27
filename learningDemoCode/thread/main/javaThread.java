public class javaThread extends Thread {
    public static void main(String[] args) {
        // 获取当前线程对象,这里是主线程对象
        Thread t = Thread.currentThread();
        System.out.println("主线程是：" + t);
        // 创建子线程并启动
        javaThread mt = new javaThread();
        mt.start();
    }

    // 重写父类的run()方法
    public void run() {
        // 获取当前线程对象,这里是子线程对象
        System.out.println("子线程是：" + Thread.currentThread());
        // 完成游戏的倒计时功能
    }
}