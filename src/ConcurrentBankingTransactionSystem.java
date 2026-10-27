import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConcurrentBankingTransactionSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Opening balance: ");
        double openingBalance = input.nextDouble();
        System.out.print("Number of users (2-10): ");
        int users = input.nextInt();
        System.out.print("Transactions per user: ");
        int operations = input.nextInt();
        System.out.print("Amount per transaction: ");
        double amount = input.nextDouble();

        if (openingBalance < 0 || users < 2 || users > 10
                || operations < 1 || amount <= 0) {
            System.out.println("Invalid input. Please run again with valid values.");
            input.close();
            return;
        }

        BankAccount account = new BankAccount(openingBalance);
        List<Thread> userThreads = new ArrayList<>();

        for (int i = 1; i <= users; i++) {
            Thread thread;
            if (i % 2 == 1) {
                thread = new DepositThread(account, amount, operations);
            } else {
                thread = new WithdrawalThread(account, amount, operations);
            }
            thread.setName("User-" + i);
            userThreads.add(thread);
            thread.start();
        }

        for (Thread thread : userThreads) {
            try {
                thread.join();
            } catch (InterruptedException exception) {
                System.out.println("Simulation interrupted.");
                input.close();
                return;
            }
        }

        System.out.println("Final balance: INR " + account.getBalance());
        input.close();
    }
}

class BankAccount {
    private double balance;

    BankAccount(double openingBalance) {
        balance = openingBalance;
    }

    public synchronized void deposit(double amount) {
        balance = balance + amount;
        System.out.println(Thread.currentThread().getName()
                + " deposited INR " + amount + ". Balance: INR " + balance);
    }

    public synchronized boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println(Thread.currentThread().getName()
                    + " withdrawal declined. Balance: INR " + balance);
            return false;
        }
        balance = balance - amount;
        System.out.println(Thread.currentThread().getName()
                + " withdrew INR " + amount + ". Balance: INR " + balance);
        return true;
    }

    public synchronized double getBalance() {
        return balance;
    }
}

abstract class TransactionThread extends Thread {
    protected BankAccount account;
    protected double amount;
    protected int operations;

    TransactionThread(BankAccount account, double amount, int operations) {
        this.account = account;
        this.amount = amount;
        this.operations = operations;
    }

    @Override
    public void run() {
        for (int i = 0; i < operations; i++) {
            makeTransaction();
        }
    }

    protected abstract void makeTransaction();
}

class DepositThread extends TransactionThread {
    DepositThread(BankAccount account, double amount, int operations) {
        super(account, amount, operations);
    }

    @Override
    protected void makeTransaction() {
        account.deposit(amount);
    }
}

class WithdrawalThread extends TransactionThread {
    WithdrawalThread(BankAccount account, double amount, int operations) {
        super(account, amount, operations);
    }

    @Override
    protected void makeTransaction() {
        account.withdraw(amount);
    }
}
