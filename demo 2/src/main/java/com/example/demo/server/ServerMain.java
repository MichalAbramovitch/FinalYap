package com.example.demo.server;


import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ServerMain {
    private List<String> lines =  new ArrayList<>();
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


    public static List<Integer> evaluateOutput(String output , String stroy) throws IOException, ParseException {
        List<Integer> list = new ArrayList<>();
        CreateJson createJson = new CreateJson();
        createJson.CreatingFile("ans" , output);
        FirstPerson answers = new FirstPerson("ans.json");
        YapExtractAns answers1 = new QWwords("ans.json");
        YapExtractAns answers2 = new NegWords("ans.json");
        Tenses answers4 =  new Tenses("ans.json");
        TextualExtractAns answers3 = new TextualExtractAns(stroy);

        answers.parserAns();
        int numOfFirstSingle = answers.getFirstCounterSingle();
        list.add(numOfFirstSingle);
        int numOfFirstPlural = answers.getFirstCounterPlural();
        list.add(numOfFirstPlural);
        int numOfSecondSingle = answers.getSecondCounterSingle();
        list.add(numOfSecondSingle);
        int numOfSecondPlural = answers.getSecondCounterPlural();
        list.add(numOfSecondPlural);
        int numOfThirdSingle = answers.getThirdCounterSingle();
        list.add(numOfThirdSingle);
        int numOffThirdPlural = answers.getThirdCounterPlural();
        list.add(numOffThirdPlural);
        int numOfQWwords = answers1.parserAns();
        list.add(numOfQWwords);
        int numOfNegWord = answers2.parserAns();
        list.add(numOfNegWord);
        answers4.parserAns();
        int past = answers4.getPastCounter();
        list.add(past);
        int beinoni = answers4.getBeinoniCounter();
        list.add(beinoni);
        int future = answers4.getFutureCounter();
        list.add(future);
        int imperative = answers4.getImperativeCounter();
        list.add(imperative);
        int numOfwords = answers3.NumOfWords();
        list.add(numOfwords);
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
            numbers = evaluateOutput(output , line);
            evlAns.put(line, numbers);
        }
        return evlAns;

    }

}