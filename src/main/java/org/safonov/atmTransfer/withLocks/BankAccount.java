package org.safonov.atmTransfer.withLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance;
    private final Lock lock;

    public BankAccount() {
        this.balance = 0.0;
        this.lock = new ReentrantLock();
    }

    public void deposit(double amount){
        lock.lock();
        try {
            balance += amount;
            System.out.println("deposit: " + amount);
            System.out.println("Balance after deposit " + balance);
        }finally {
            lock.unlock();
        }
    }
    public void withDraw(double amount){
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdraw: " + amount);
                System.out.println("Balance after withdrawal: " + balance);
            }
            else {
                System.out.println("Try to withdraw: " + amount);
                System.out.println("Insuffieient funds. Withdrawal cancelled.");
            }
        }finally {
            lock.unlock();
        }
    }
}
