package com.example.demo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFileDemo {

    private List<String> sentences = new ArrayList<String>();
    private String path;


    public ReadExcelFileDemo(String path) {
        this.path = path;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public String getPath() {
        return path;
    }
    public String reveseRow(String row){
        StringBuilder sb=new StringBuilder(row);
        sb.reverse();
        String strNew = sb.toString();
        strNew = strNew.substring(1, strNew.length());
        return strNew;
    }

    public void parser() throws IOException {
        FileInputStream fis = new FileInputStream(new File(this.path));
        //creating workbook instance that refers to .xls file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        System.out.print(wb.getNumberOfSheets());
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getLastCellNum()== 1 && (row.getCell(0).getStringCellValue() == ""))continue;

            String sentence = "";
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        sentence +=(cell.getStringCellValue()) + " ";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        sentence +=(cell.getNumericCellValue()) + " ";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        sentence +=(cell.getBooleanCellValue()) + " ";
                        break;
                    default :
                }
            }
            //xsentences.add(sentence);
            sentences.add(sentence);
        }
    }


}