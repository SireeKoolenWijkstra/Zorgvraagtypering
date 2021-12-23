/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Siree
 */
public class OutputFile {

    public void writeOutputFile(Iterable<CSVRecord> inputRecords, HashMap fileCodeLijst, HashMap fileConstanteLijst, ArrayList fileRodeLijst) throws IOException {

        CalculateZorgvraagtype zorgvraagtype = new CalculateZorgvraagtype();
        String outputFilePath = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\zorgvraagtyperingpercentages.csv";
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(outputFilePath));
        

        try (CSVPrinter csvPrinter = CSVFormat.EXCEL
                .withHeader("KLNT_NUMMER", "KLNT_VOORLETTERS",
                "KLNT_VOORVOEGSELS", "KLNT_NAAM", "BIAG_AFDELING_NUMMER","Zorgtype 1", "Zorgtype 2",
                "Zorgtype 3", "Zorgtype 4", "Zorgtype 5", "Zorgtype 6", "Zorgtype 7", "Zorgtype 8", "Zorgtype 10", "Zorgtype 11", "Zorgtype 12", "Zorgtype 13", "Zorgtype 14",
                "Zorgtype 15", "Zorgtype 16", "Zorgtype 17", "Zorgtype 18", "Zorgtype 19", "Zorgtype 20", "Zorgtype 21")
                .withDelimiter(';')
                .print(bufferedWriter)) {
            for (CSVRecord record : inputRecords) {
                var hmap = zorgvraagtype.calculatePercentagePerRecord(zorgvraagtype.scorePerTypePerRecord(
                        record,
                        fileCodeLijst,
                        fileConstanteLijst,
                        fileRodeLijst));
                csvPrinter.printRecord(prepareRecord(record, hmap));
            }
        }
    }

    public ArrayList<String> prepareRecord(CSVRecord record, HashMap<String, Double> percentageHmap) {

        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        
        ArrayList<String> finalRowInFinaLResult = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            finalRowInFinaLResult.add(record.get(i));
        }
        var zorgtypen = percentageHmap.keySet().toArray();
        Arrays.sort(zorgtypen);
        for (var zorgtype : zorgtypen) {
            finalRowInFinaLResult.add(format.format(percentageHmap.get(zorgtype)));
        }
        return finalRowInFinaLResult;
    }
}
