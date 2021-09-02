package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logwriter {

    private File file;

    public Logwriter(String nameOfFile){

        this.file= new File (nameOfFile);
    }

    public void writeToFile(String textLine){

    PrintWriter writer= null;

    if (this.file.exists()) {

        try {
            FileOutputStream output = new FileOutputStream(file, true);
            writer = new PrintWriter(output);
            writer.println(textLine);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }else{

        try{
            writer= new PrintWriter(this.file);
            writer.println("TOP SECRET WHITE ELEPHANT LIST");

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
    writer.flush();
    writer.close();

}

}
