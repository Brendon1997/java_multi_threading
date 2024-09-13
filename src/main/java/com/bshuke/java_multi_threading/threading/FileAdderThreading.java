package com.bshuke.java_multi_threading.threading;

import com.bshuke.java_multi_threading.model.FileAdderNoReturn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileAdderThreading {

    public static void main(String[] args) {
        String[] inFiles = {"file.txt", "filePath2.txt", "filePath3.txt"};
        String[] outFiles = {"file1out.txt", "file2out.txt", "file3out.txt"};

        ExecutorService es = Executors.newFixedThreadPool(3);

        for(int i=0; i<inFiles.length; i++) {
            Thread adder = new Thread(new FileAdderNoReturn(inFiles[i], outFiles[i]));
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
