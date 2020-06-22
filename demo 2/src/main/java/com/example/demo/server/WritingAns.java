package com.example.demo.server;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class WritingAns {

    public void editFile(Map< String,List<Integer>> evlAns, String fileName)
    {
        //Blank workbook

        XSSFWorkbook workbook = new XSSFWorkbook();
        String[] headers = {"Stories","First Person", "Question words", "Negative words", "Number of Words"};
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("ans");

        //This data needs to be written (Object[])
        Map<Integer, List<Object>> data = new TreeMap<Integer , List<Object>>();
        int k = 0;
        for (String i : evlAns.keySet()) {
            List<Object> list = new ArrayList<>();
            list.add(i);
            list.add(evlAns.get(i));
            System.out.println(list);
            data.put(k, list);
            k++;
        }
        //Iterate over data and write to sheet
        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        int cellnum = 0;
        Row rowHeader = sheet.createRow(rownum++);
        for (String head: headers){
            Cell cellHeader = rowHeader.createCell(cellnum++);
            cellHeader.setCellValue(head);
        }

        for (Integer key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            List<Object> objArr = data.get(key);
            System.out.println(objArr);
            cellnum = 0;
            for (Object obj : objArr) {
                // this line creates a cell in the next column of that row
                if (obj instanceof String) {
                    System.out.println(obj);
                    Cell cell= row.createCell(cellnum++);
                    cell.setCellValue((String) obj);
                }
                else if (obj instanceof List) {
                    for (Object ans : (List)obj ){
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue((Integer)ans);
                    }
                }

            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}