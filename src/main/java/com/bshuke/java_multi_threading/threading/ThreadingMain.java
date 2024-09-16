package com.bshuke.java_multi_threading.threading;

import com.bshuke.java_multi_threading.model.BankAccount;
import com.bshuke.java_multi_threading.model.BankAccountWorker;
import com.bshuke.java_multi_threading.model.FileAdderCoupled;
import com.bshuke.java_multi_threading.model.FileAdderDecoupled;

import java.util.concurrent.*;

public class ThreadingMain {

    public static void main(String[] args) {
//        do_threading_decoupled();
//        do_threading_coupled();
        demonstrateThreadingConcurrency();
    }

    private static void demonstrateThreadingConcurrency() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        BankAccount bankAccount = new BankAccount(100);

/*      1)
        We have 5 workers working on the same bank account at the same time.
        To update the value of the deposit we take the value from memory, update it and save it.
        But when we take the value from memory another thread may take the same value from memory, increment it and
        update the value based on the old update, and we end up with a random total that is unexpected.

        2)
        We can fix this by using synchronised in the deposit and getBalance methods that are called within the run method of the Worker.
        We can also fix this by using a synchronised block on the instance.

        3)
        Synchronised methods only protect the body of the method from being accessed by multiple threads concurrently.
        Synchronised blocks allow you to protect multiple methods being called as a block and not allowing other threads to access
        the object at the same time the block is being run.
        */

        for (int i = 0; i < 5; i++) {
        BankAccountWorker worker = new BankAccountWorker(bankAccount);
        executorService.submit(worker);
        }
    }

    private static void do_threading_coupled() {
        String[] inFiles = {"file.txt", "file2.txt", "file3.txt"};

        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer>[] results = new Future[inFiles.length];
        for(int i=0; i<inFiles.length; i++) {
            FileAdderCoupled adder = new FileAdderCoupled(inFiles[i]);
            results[i] = es.submit(adder);
        }

        for (Future<Integer> future : results) {
            try {
                int value = future.get();
                System.out.println("Total: " + value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        }

        es.shutdown();
    }

    private static void do_threading_decoupled() {
        String[] inFiles = {"file.txt", "file.txt", "file.txt"};
        String[] outFiles = {"file1out.txt", "file2out.txt", "file3out.txt"};

        ExecutorService es = Executors.newFixedThreadPool(3);

        for(int i=0; i<inFiles.length; i++) {
            Thread adder = new Thread(new FileAdderDecoupled(inFiles[i], outFiles[i]));
            es.submit(adder);
        }

        es.shutdown();
        try {
            boolean didTerminate = es.awaitTermination(60, TimeUnit.SECONDS); // In order for the main thread to wait for all spawned threads to finish execution.
            if(!didTerminate){
                throw new RuntimeException("Timeout occured");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
