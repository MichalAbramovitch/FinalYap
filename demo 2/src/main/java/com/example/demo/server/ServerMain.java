package com.example.demo.server;


import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ServerMain {

    public static String sendCommand(String[] command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process client;
        StringBuilder output = new StringBuilder();
        try {
            client = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return output.toString();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        int exitCode;
        try {
            exitCode = client.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return output.toString();
        }
        client.destroy();
        return output.toString();
    }


    public static List<Integer> evaluateOutput(String output) throws IOException, ParseException {
        List<Integer> list = new ArrayList<>();
        CreateJson createJson = new CreateJson();
        createJson.CreatingFile("ans" , output);
        YapExtractAns answers = new FirstPerson("ans.json");
        YapExtractAns answers1 = new QWwords("ans.json");
        YapExtractAns answers2 = new NegWords("ans.json");
        int numOfFirst = answers.parserAns();
        list.add(numOfFirst);
        int numOfQWwords = answers1.parserAns();
        list.add(numOfQWwords);
        int numOfNegWord = answers2.parserAns();
        list.add(numOfNegWord);
        return  list;
    }

    public Map< String,List<Integer>> processData(List<String> lines) throws IOException, ParseException {
        Map< String,List<Integer>> evlAns = new HashMap<>();
        String[] command = {"curl", "-s", "-X", "GET", "-H", "Content-Type: application/json", "-d", "",
                "http://localhost:8000/yap/heb/joint", "|", "jq", "."};
        List<Integer> numbers = new ArrayList<>();
        numbers.clear();
        String output;
        command[7] = "{\"text\": \"מחכה שהשרת יעלה  \"}";
        System.out.println("waiting for server to connect");
        output = sendCommand(command);
        int i = 0;
        while (output.length() == 0) {
            output = sendCommand(command);
            i++;
        }
        System.out.println("connected");
        for (String line: lines) {
            System.out.println("current line is:" + line);
            command[7] = "{\"text\": \""+line+"  \"}";
            output = sendCommand(command);
            numbers = evaluateOutput(output);
            evlAns.put(line, numbers);
        }
        return evlAns;

    }

}