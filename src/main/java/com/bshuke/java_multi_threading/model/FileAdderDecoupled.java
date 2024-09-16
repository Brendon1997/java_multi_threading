package com.bshuke.java_multi_threading.model;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@Setter
public class FileAdderDecoupled implements Runnable{

    private String inFileName;
    private String outFileName;

    public FileAdderDecoupled(String inFileName, String outFileName) {
        this.inFileName = inFileName;
        this.outFileName = outFileName;
    }


    private void process() throws IOException {
        int total = 0;
        String line;

        try(BufferedReader br = Files.newBufferedReader(Paths.get(inFileName))){
            while((line = br.readLine()) != null) {
                total = total + Integer.parseInt(line);
            }
        }
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(outFileName))){
            bw.write("Total: " + total);
        }

    }


    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
