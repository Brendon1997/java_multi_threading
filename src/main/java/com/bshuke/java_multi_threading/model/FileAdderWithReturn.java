package com.bshuke.java_multi_threading.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAdderWithReturn implements Runnable{

    private String inFileName;
    private String outFileName;

    public FileAdderWithReturn(String inFileName, String outFileName) {
        this.inFileName = inFileName;
        this.outFileName = outFileName;
    }


    private void process(){
        // TODO Process file
    }


    @Override
    public void run() {
        process();
    }
}
