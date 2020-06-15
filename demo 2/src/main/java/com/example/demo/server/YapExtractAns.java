package com.example.demo.server;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface YapExtractAns {

    void decodingJson() throws IOException, ParseException;
    int parserAns() throws IOException, ParseException;
}
