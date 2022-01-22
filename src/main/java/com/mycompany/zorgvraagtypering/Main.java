/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Siree
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        

        String fileHonosBasic = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\inleesbestandHonosBasis.csv";
        String fileCodelijst = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\CodelijstZorgvraagtypering.csv";
        String fileConstanteLijst = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\ConstanteZorgvraagtypering.csv";
        String fileRodeLijst = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\RodeLijst.csv";

        ReadCSVFile readCSVFile = new ReadCSVFile();
        OutputFile outputFile = new OutputFile();

        outputFile.writeOutputFile(
                readCSVFile.parseHONOSBasic(fileHonosBasic),
                readCSVFile.parseCodeLijst(fileCodelijst),
                readCSVFile.parseConstanteLijst(fileConstanteLijst),
                readCSVFile.parseRodeLijst(fileRodeLijst));
    }
}
