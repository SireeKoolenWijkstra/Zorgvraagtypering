/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Siree
 */
public class CalculateZorgvraagtype {

    /* Calculate the score per type (20 total, nr 9 is not counted so it counts up to 21):
     * For type 1 sum up all the scores for that type (eg score question 1 + question 2 + questions 3 etc)
     * Do so again for type 2, etc etc
     * Put the answers in a dictionary 
     */
    public HashMap<String, Double> scorePerTypePerRecord(
            CSVRecord patiënt,
            HashMap<String, HashMap<Integer, HashMap<String, Double>>> CodeLijst,
            HashMap<String, Double> constanteLijst,
            ArrayList<RodeRegel> RodeRegels) {

        HashMap<String, Double> scorePerType = (HashMap<String, Double>) constanteLijst.clone();

        for (int i = 1; i <= 12; i++) {
            String vraag = "HV" + (i < 10 ? "0" + i : i);

            int ernst = Integer.parseInt(patiënt.get(i + 4));
            //wanneer uitkomst op een vraag onbekend is, wordt waarde -1 gegeven. Waarde -1 betekent dat er niks aan de score van de zorgtype moet worden toegevoegd.             
            if (ernst == -1) {
                continue;
            }
            for (var zorgType : CodeLijst.get(vraag).get(ernst).entrySet()) {
                //voor elk type een score in scorePerTypePerRecord
                double previousValue = scorePerType.get(zorgType.getKey());
                double newValue = previousValue + zorgType.getValue();
                scorePerType.put(zorgType.getKey(), newValue);
            }
        }
        /*
        Loop over de ArrayList RodeRegels.
        Situatie a: Check bij de patiënt bij vraag x wat de ernst is en kijk of die voorkomt in de RodeRegels. 
        Komt de ernst voor in de RodeRegels , kijk dan of er een vraag_02 is bij die rodeRegel.
        Situatie b: Is er een vraag_2, check dan ook ernst_2 voorkomt in patiënt
        Bij zowel situatie a als bij zet de coëfficiënt in de scorePerType op 0 (exp(-oneindig) = 0, double.infinity oid)
         */
        for (RodeRegel rodeRegel : RodeRegels) {

            boolean ernstEqualsHONOSanswerToQuestionanswer = Integer.parseInt(patiënt.get(rodeRegel.vraag + 4)) == rodeRegel.ernst;

            if (ernstEqualsHONOSanswerToQuestionanswer) {
                if (rodeRegel.vraag_2 == null) {
                    scorePerType.replace(rodeRegel.zorgtype, Double.NEGATIVE_INFINITY);
                } else {
                    boolean ernst_2EqualsHONOSanswerToQuestionanswer = Integer.parseInt(patiënt.get(rodeRegel.vraag_2 + 4)) == rodeRegel.ernst_2;
                    if (ernst_2EqualsHONOSanswerToQuestionanswer) {
                        scorePerType.replace(rodeRegel.zorgtype, Double.NEGATIVE_INFINITY);
                    }
                }
            }
        }
        return scorePerType;
    }

    public HashMap<String, Double> calculatePercentagePerRecord(HashMap<String, Double> scorePerTypePerRecord) {
        double dividedby = 0;
        HashMap<String, Double> percentageHmap = new HashMap<String, Double>();
        for (var percentageZorgtype : scorePerTypePerRecord.entrySet()) {
            dividedby += Math.exp(percentageZorgtype.getValue());
        }
        for (var percentageZorgtype : scorePerTypePerRecord.entrySet()) {
            double percentage = (Math.exp(percentageZorgtype.getValue()) / dividedby) * 100;
            percentageHmap.put(percentageZorgtype.getKey(), percentage);
        }
        return percentageHmap;
    }
}
