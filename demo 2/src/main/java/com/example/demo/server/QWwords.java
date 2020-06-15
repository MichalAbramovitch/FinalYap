package com.example.demo.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class QWwords implements YapExtractAns {
    private String decodeAns;
    private  String jsonFile;

    public QWwords(String json_file) {
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
            if(word.equals(""))continue;
            int idx_question = word.indexOf("QW");
            if(idx_question ==-1) continue;
            counter++;
        }

        return counter;
    }
}
