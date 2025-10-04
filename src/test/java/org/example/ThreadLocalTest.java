package org.example;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void test() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        new Thread(() -> {
            threadLocal.set("111");
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }, "thread1").start();
        new Thread(() -> {
            threadLocal.set("222");
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }, "thread2").start();
    }
}
