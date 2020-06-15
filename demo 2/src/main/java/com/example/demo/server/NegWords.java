package com.example.demo.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class NegWords implements YapExtractAns {

    private String decodeAns;
    private  String JsonFile;

    public NegWords(String json_file) {
        this.JsonFile = json_file;

    }

    public void decodingJson() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(this.JsonFile));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        String dep_tree = (String) jo.get("dep_tree");
        this.decodeAns = dep_tree;
    }

    // need to check what about the case per = A
    public int parserAns() throws IOException, ParseException {
        this.decodingJson();
        System.out.println(decodeAns);
        int counter = 0;
        String[] words = decodeAns.split("\n");
        String[] attribute;
        for (String word:words) {
            if(word.equals(""))continue;
            //attribute = word.split("\t");
            int idx_neg = word.indexOf("neg");
            if(idx_neg ==-1) continue;
            counter++;
        }

        return counter;
    }
}
