package org.safonov.CyclicBarrierExample;

// Java program to demonstrate Execution on Cyclic Barrier

// Importing required classes

import java.util.concurrent.BrokenBarrierException;

// Class 1
// Class implementing Runnable interface
class Computation1 implements Runnable {

    public static int product = 0;

    public void run() {
        product = 2 * 3;
        try {
            // thread1 awaits for other threads
            Tester.newBarrier.await();
        } catch (InterruptedException
                 | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

