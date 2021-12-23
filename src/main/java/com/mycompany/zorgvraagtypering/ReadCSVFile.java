/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Siree
 */
public class ReadCSVFile {

    public Iterable<CSVRecord> parseHONOSBasic(String fileName) throws IOException {

        BufferedReader dataSet = new BufferedReader(new FileReader(fileName));

        Iterable<CSVRecord> records = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader()
                .parse(dataSet);

        return records;
    }

    public HashMap parseCodeLijst(String fileName) throws IOException, ParseException {
        //to read ',' and not '.' as the   
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

        BufferedReader dataSet = new BufferedReader(new FileReader(fileName));

        Iterable<CSVRecord> records = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader()
                .parse(dataSet);
        HashMap<String, HashMap<Integer, HashMap<String, Double>>> hmap = new HashMap<String, HashMap<Integer, HashMap<String, Double>>>();

        for (CSVRecord record : records) {
            if (!hmap.containsKey(record.get(0))) {
                hmap.put(record.get(0), new HashMap<Integer, HashMap<String, Double>>());
            }
            if (!hmap.get(record.get(0)).containsKey(Integer.parseInt(record.get(1)))) {
                hmap.get(record.get(0)).put(Integer.parseInt(record.get(1)), new HashMap<String, Double>());
            }
            hmap.get(record.get(0)).get(Integer.parseInt(record.get(1))).put(record.get(3), format.parse(record.get(4)).doubleValue());
        }
        return hmap;

    }

    public HashMap parseConstanteLijst(String fileName) throws IOException, ParseException {
        //to read ',' and not '.' as the   
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

        BufferedReader dataSet = new BufferedReader(new FileReader(fileName));

        Iterable<CSVRecord> records = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader()
                .parse(dataSet);
        HashMap<String, Double> hmap = new HashMap<String, Double>();

        for (CSVRecord record : records) {
            hmap.put(record.get(0), format.parse(record.get(1)).doubleValue());
        }
        return hmap;

    }

    public ArrayList<RodeRegel> parseRodeLijst(String fileName) throws IOException, ParseException {

        BufferedReader dataSet = new BufferedReader(new FileReader(fileName));

        Iterable<CSVRecord> records = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader()
                .parse(dataSet);
        ArrayList<RodeRegel> rodeRegels = new ArrayList<RodeRegel>();

        for (CSVRecord record : records) {
            RodeRegel rodeRegel = new RodeRegel();
            rodeRegel.vraag = Integer.parseInt(record.get(0).substring(2));
            rodeRegel.ernst = Integer.parseInt(record.get(1));
            if (!record.get(2).isEmpty()) {
                rodeRegel.vraag_2 = Integer.parseInt(record.get(2).substring(2));
                rodeRegel.ernst_2 = Integer.parseInt(record.get(3));
            } else {
                rodeRegel.vraag_2 = null;
            }
            rodeRegel.zorgtype = record.get(4);
            rodeRegels.add(rodeRegel);
        }
        return rodeRegels;
    }
}
