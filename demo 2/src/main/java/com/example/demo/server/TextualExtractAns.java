package com.example.demo.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextualExtractAns {

    private String sentences;


    public TextualExtractAns(String sentences) {
        this.sentences = sentences;
    }

    public int NumOfWords() {
        int num = 0;
        num = sentences.split(" ").length;
        return num;
    }

    public Map<Character, Integer> NumOfChar() {

        return null;
    }

}
