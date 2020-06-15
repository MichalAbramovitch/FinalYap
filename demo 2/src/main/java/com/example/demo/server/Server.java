package com.example.demo.server;

import java.io.File;
import java.io.IOException;

public class Server extends Thread {

    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder("bash server.sh".split(" "));
        Process server;
        int exitCode;
        try {
            server = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            exitCode = server.waitFor();
        } catch (InterruptedException e) {
            System.out.println("server exit now\n");
            return;
        }
        server.destroy();
    }
}