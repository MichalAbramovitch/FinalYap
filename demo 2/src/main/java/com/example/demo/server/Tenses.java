package com.example.demo.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Tenses implements YapExtractAns {
    private String decodeAns;
    private  String jsonFile;

    private int pastCounter;
    private int beinoniCounter;

    public int getPastCounter() {
        return pastCounter;
    }

    public int getBeinoniCounter() {
        return beinoniCounter;
    }

    public int getFutureCounter() {
        return futureCounter;
    }

    public int getImperativeCounter() {
        return imperativeCounter;
    }

    private int futureCounter;
    private int imperativeCounter;


    public Tenses(String json_file) {
        this.jsonFile = json_file;
    }

    public void decodingJson() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(this.jsonFile));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        String md_lattice = (String) jo.get("md_lattice");
        this.decodeAns = md_lattice;
    }

    public int parserAns() throws IOException, ParseException {
        this.decodingJson();
        int counter = 0;
        String[] words = decodeAns.split("\n");
        String[] attribute;
        for (String word:words) {
            if (word.equals("")) continue;
            int idx_question = word.indexOf("tense");
            if (idx_question == -1) continue;
            attribute = word.split("tense=");
            String tense = attribute[1].split("\t")[0];
            switch (tense) {
                case "PAST":
                    this.pastCounter++;
                    break;
                case "BEINONI":
                    this.beinoniCounter++;
                    break;
                case "FUTURE":
                    this.futureCounter++;
                    break;
                case "IMPERATIVE":
                    this.imperativeCounter++;
                    break;
                default:
                    continue;

            }
        }
        return counter;
    }
}
