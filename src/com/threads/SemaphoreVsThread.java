package com.threads;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Rajesh on 2/23/2018.
 */
public class SemaphoreVsThread {

    static Lock myLock = new ReentrantLock();
    static Semaphore mySem = new Semaphore(1);

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Acquire Sem " + Thread.currentThread().getName());
                    mySem.acquire();
                    System.out.println("Acquired Sem " + Thread.currentThread().getName());
                    Thread.sleep(1000*15*1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Acquire Sem " + Thread.currentThread().getName());
                try {
                    boolean b = mySem.tryAcquire(10, TimeUnit.SECONDS);
                    System.out.println("Acquired Sem " + b + " " + Thread.currentThread().getName());
                    mySem.release();
                    b = mySem.tryAcquire(10, TimeUnit.SECONDS);
                    System.out.println("Acquired Sem " + b + " " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
