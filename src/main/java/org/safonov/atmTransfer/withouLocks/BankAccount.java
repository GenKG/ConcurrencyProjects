package org.safonov.atmTransfer.withouLocks;

public class BankAccount {
    private final int id;
    private int balance;

    public BankAccount(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public static void transfer(BankAccount from, BankAccount to, int amount) {
        BankAccount first = from.getId() < to.getId() ? from : to;
        BankAccount second = from.getId() < to.getId() ? to : from;
        synchronized (first) {

            try {
                Thread.sleep(100);  // Simulating some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (second) {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from Account " + from.getId() + " to Account " + to.getId());
                } else {
                    System.out.println("Insufficient funds in Account " + from.getId());
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public boolean withdraw(int amount) {
        if (balance < amount) return false;
        balance -= amount;
        return true;
    }
}
