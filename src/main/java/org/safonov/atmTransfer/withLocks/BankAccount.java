package org.safonov.atmTransfer.withLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance;
    private final Lock lock;
    private final long id;

    public BankAccount(int id) {
        this.id = id;
        this.balance = 0.0;
        this.lock = new ReentrantLock();
    }

    public static void transfer(BankAccount from, BankAccount to, int amount) {
        BankAccount first = from.getId() < to.getId() ? from : to;
        BankAccount second = from.getId() < to.getId() ? to : from;
        first.lock.lock();
        try {
            try {
                Thread.sleep(100);  // Simulating some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            second.lock.lock();
            try {
                if (from.withDraw(amount)) {
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from Account " + from.getId() + " to Account " + to.getId());
                } else {
                    System.out.println("Insufficient funds in Account " + from.getId());
                }
            } finally {
                second.lock.unlock();
            }

        } finally {
            first.lock.unlock();
        }

    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("deposit: " + amount);
            System.out.println("Balance after deposit " + balance);
        } finally {
            lock.unlock();
        }
    }

    public boolean withDraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdraw: " + amount);
                System.out.println("Balance after withdrawal: " + balance);
                return true;
            }
            System.out.println("Try to withdraw: " + amount);
            System.out.println("Insuffieient funds. Withdrawal cancelled.");
            return false;
        } finally {
            lock.unlock();
        }
    }

    public long getId() {
        return id;
    }
}
