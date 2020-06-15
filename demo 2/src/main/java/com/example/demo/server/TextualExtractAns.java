package com.example.demo.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextualExtractAns {

    private List<String> sentences = new ArrayList<String>();


    public TextualExtractAns(List<String> sentences) {
        this.sentences = sentences;
    }

    public int NumOfWords() {
        int num = 0;
        for (String sentence : sentences ){
            num +=sentence.split(" ").length;
        }
        return num;
    }

    public Map<Character, Integer> NumOfChar() {

        return null;
    }

}
