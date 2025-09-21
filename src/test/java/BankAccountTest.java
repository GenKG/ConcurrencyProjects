import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safonov.atmTransfer.withLocks.BankAccount;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountTest {

    private BankAccount account1;
    private BankAccount account2;

    @BeforeEach
    void setUp() {
        account1 = new BankAccount(1);
        account2 = new BankAccount(2);
    }

    @Test
    void testDepositIncreasesBalance() {
        account1.deposit(100);

        assertTrue(account1.withDraw(100), "Balance after deposit should allow withdraw");
    }

    @Test
    void testWithdrawDecreasesBalance() {
        account1.deposit(200);
        assertTrue(account1.withDraw(150));

        assertFalse(account1.withDraw(100), "Should not allow overdraft");
    }

    @Test
    void testTransferMovesMoneyBetweenAccounts() {
        account1.deposit(300);
        BankAccount.transfer(account1, account2, 200);


        assertTrue(account1.withDraw(100));
        assertFalse(account1.withDraw(1), "Account1 should now be empty");


        assertTrue(account2.withDraw(200));
    }

    @Test
    void testTransferInsufficientFunds() {
        account1.deposit(50);
        BankAccount.transfer(account1, account2, 100);


        assertTrue(account1.withDraw(50), "Balance should remain unchanged");
        assertFalse(account2.withDraw(1), "Account2 should remain empty");
    }

    @Test
    void testConcurrentTransfersNoDeadlock() throws InterruptedException {
        account1.deposit(1000);
        account2.deposit(1000);

        Thread t1 = new Thread(() -> BankAccount.transfer(account1, account2, 100));
        Thread t2 = new Thread(() -> BankAccount.transfer(account2, account1, 100));

        t1.start();
        t2.start();

        t1.join(2000);
        t2.join(2000);

        assertFalse(t1.isAlive() || t2.isAlive(), "Threads should not deadlock");
    }
}
