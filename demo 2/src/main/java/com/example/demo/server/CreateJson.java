package com.example.demo.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateJson {

    public CreateJson() {

    }
    public void CreatingFile(String fieName , String content) {
        fieName =  fieName+ ".json";
        try {
            FileWriter myWriter = new FileWriter(fieName);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
