package com.bshuke.java_multi_threading.threading;

import com.bshuke.java_multi_threading.model.FileAdderCoupled;
import com.bshuke.java_multi_threading.model.FileAdderDecoupled;
import org.springframework.boot.autoconfigure.gson.GsonProperties;

import java.util.concurrent.*;

public class FileAdderThreading {

    public static void main(String[] args) {
        do_threading_decoupled();
        do_threading_coupled();
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
