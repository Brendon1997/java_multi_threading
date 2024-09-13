package com.bshuke.java_multi_threading.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAdderNoReturn implements Runnable{

    private String inFileName;
    private String outFileName;

    public FileAdderNoReturn(String inFileName, String outFileName) {
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
