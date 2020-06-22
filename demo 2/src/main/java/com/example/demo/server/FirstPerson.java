package com.example.demo.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;


public class FirstPerson implements YapExtractAns{

    private String decodeAns;
    private String jsonFile;
    private int firstCounterSingle;
    private int firstCounterPlural;
    private int secondCounterSingle;
    private int secondCounterPlural;

    public int getFirstCounterSingle() {
        return firstCounterSingle;
    }

    public int getFirstCounterPlural() {
        return firstCounterPlural;
    }

    public int getSecondCounterSingle() {
        return secondCounterSingle;
    }

    public int getSecondCounterPlural() {
        return secondCounterPlural;
    }

    public int getThirdCounterSingle() {
        return thirdCounterSingle;
    }

    public int getThirdCounterPlural() {
        return thirdCounterPlural;
    }

    private int thirdCounterSingle;
    private int thirdCounterPlural;


    public FirstPerson(String json_file) {
        this.jsonFile = json_file;
        this.firstCounterSingle = 0;
        this.firstCounterPlural = 0;
        this.secondCounterSingle = 0;
        this.secondCounterSingle = 0;
        this.thirdCounterPlural = 0;
        this.thirdCounterSingle = 0;

    }

    public void decodingJson() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(this.jsonFile));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        String md_lattice = (String) jo.get("md_lattice");
        this.decodeAns = md_lattice;
    }

    // need to check what about the case per = A
    public int parserAns() throws IOException, ParseException {
        this.decodingJson();
        int counter = 0;
        String[] words = decodeAns.split("\n");
        String[] attribute;
        for (String word:words) {
            if(word.equals(""))continue;
            attribute = word.split("\t");
            int idx_num = attribute[6].indexOf("num");
            char number = attribute[6].split("num=")[1].charAt(0);
            int idx_person = attribute[6].indexOf("per");
            if(idx_person ==-1) continue;
            attribute = attribute[6].split("per=");
            char person_num = attribute[1].charAt(0);
            switch (person_num) {
                case '1':
                    if(number =='S') {this.firstCounterSingle ++;}
                    else this.firstCounterPlural++;
                    break;
                case '2':
                    if(number =='S') {this.secondCounterSingle ++;}
                    else this.secondCounterPlural++;
                    break;
                case '3':
                    if(number =='S') {this.thirdCounterSingle ++;}
                    else this.thirdCounterPlural++;
                    break;
                default:
                    continue;

            }
        }

        return counter;
    }


}

