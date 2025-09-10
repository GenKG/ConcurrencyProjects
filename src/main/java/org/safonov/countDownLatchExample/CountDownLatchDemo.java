package org.safonov.countDownLatchExample;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {


    public static void main(String[] args)
            throws InterruptedException
    {
        CountDownLatch latch = new CountDownLatch(4);

        Worker first = new Worker(1000, latch, "WORKER-1");
        Worker second = new Worker(2000, latch, "WORKER-2");
        Worker third = new Worker(3000, latch, "WORKER-3");
        Worker fourth = new Worker(4000, latch, "WORKER-4");


        first.start();
        second.start();
        third.start();
        fourth.start();


        latch.await();


        System.out.println(Thread.currentThread().getName()
                + " has finished");
    }
}

