package com.bshuke.java_multi_threading.model;

public class BankAccountWorker implements Runnable {
    private BankAccount bankAccount;

    public BankAccountWorker(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
/*          Alternatively to sychronising the methods of the object we can use synchronised blocks.
            synchronized (bankAccount) {

            }*/
            int startBalance = bankAccount.getBalance();
            bankAccount.deposit(10);
            int endBalance = bankAccount.getBalance();
            System.out.println(Thread.currentThread().getName() + " startbalance " + startBalance + " endBalance " + endBalance);
        }
    }
}
