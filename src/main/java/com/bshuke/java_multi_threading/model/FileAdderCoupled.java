package com.bshuke.java_multi_threading.model;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Getter
@Setter
public class FileAdderCoupled implements Callable<Integer> {

    private String inFileName;

    public FileAdderCoupled(String inFileName) {
        this.inFileName = inFileName;
    }


    private int process() throws IOException {
        int total = 0;
        String line;

        try(BufferedReader br = Files.newBufferedReader(Paths.get(inFileName))){
            while((line = br.readLine()) != null) {
                total = total + Integer.parseInt(line);
            }
        }
        return total;

    }


    @Override
    public Integer call() throws IOException {
        return process();
    }
}
