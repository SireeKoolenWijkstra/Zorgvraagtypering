/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering.ZVTDecisionTree;

import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public interface DecisionTree {

    public static double calculateWeightedEntropy(ArrayList<PatientWithZVT> patientsLeft, ArrayList<PatientWithZVT> patientsRight) {
        double entropyLeft = entropy(patientsLeft);
        double entropyRight = entropy(patientsRight);
        int population = patientsLeft.size() + patientsRight.size();

        return (entropyLeft * patientsLeft.size() + entropyRight * patientsRight.size()) / population;
    }

    public static double entropy(ArrayList<PatientWithZVT> patients) {
        assert !patients.isEmpty();

        int[] perZVTaantalPt = new int[20];
        for (PatientWithZVT patient : patients) {
            //If statementchecks because ZVT 9 doesn't exist and ZVTtypes go up to ZVT21 (problem due to indexing)

            if (patient.zorgvraagtype < 9) {
                perZVTaantalPt[patient.zorgvraagtype - 1]++;
            } else {
                perZVTaantalPt[patient.zorgvraagtype - 2]++;
            }

        }

        double totaal = 0;
        for (int nrOfPt : perZVTaantalPt) {
            double proportion = nrOfPt / (double) patients.size();
            if (proportion != 0) {
                totaal -= proportion * Math.log(proportion);
            }
        }
        return totaal;
    }

    public int typeer(Patient patient);

    /*
    (geen pruning ivm grootte dataset)
    1. loop over alle vragen en thresholds
        1.a groepeer alle patiënten aan hun kant van de threshold 
        1.b meet hoe tevreden je bent met de split (meet entropy 
            (https://towardsdatascience.com/entropy-and-information-gain-in-decision-trees-c7db67a3a293) en sla de split op als die beter is
    2. maak the node met de gegevens van stap 1      
     */
    public static DecisionTree createNode(ArrayList<PatientWithZVT> trainingGroup) {
        //Kijk of alle patiënten dezelfde zorgvraagtypering hebben, zo ja dan return je een nodeleaf
        int zorgvraagtypering = trainingGroup.get(0).zorgvraagtype;
        boolean oneZorgvraagtypering = true;
        for (var pt : trainingGroup) {
            if (zorgvraagtypering != pt.zorgvraagtype) {
                oneZorgvraagtypering = false;
            }
        }
        if (oneZorgvraagtypering) {
            return new NodeLeaf(zorgvraagtypering);
        }
        double bestValueOfSplit = Double.POSITIVE_INFINITY; //Hoe schever de de verdeling over de zorgvraagtyperingen, hoe lager de entropy(wat beter is)
        ArrayList<PatientWithZVT> goodSplitofPatientsLeft = null; // is null omdat bestValueOfSplit altijd het allerslechte is en dus goodSplitofPatiensLeft 
        //altijd wordt gevuld
        ArrayList<PatientWithZVT> goodSplitofPatientsRight = null;// is null omdat bestValueOfSplit altijd het allerslechte is en dus goodSplitofPatiensRight 
        //altijd wordt gevuld
        int nodeForkHonosVraag = 0;
        int nodeForkThreshold = 0;

        //zie stap 1 en 1.a
        for (int honosVraag = 0; honosVraag < 12; honosVraag++) {
            for (int threshold = 0; threshold < 5; threshold++) {
                ArrayList<PatientWithZVT> patientsLeft = new ArrayList<PatientWithZVT>();
                ArrayList<PatientWithZVT> patientsRight = new ArrayList<PatientWithZVT>();
                for (var patient : trainingGroup) {
                    if (patient.patient.honosScore[honosVraag] < threshold) {
                        patientsLeft.add(patient);
                    } else {
                        patientsRight.add(patient);
                    }

                }
                if (patientsLeft.isEmpty()) {
                    continue;
                }
                if (patientsRight.isEmpty()) {
                    continue;
                }
                //zie stap 1.b
                double valueOfSplit = calculateWeightedEntropy(patientsLeft, patientsRight);
                if (valueOfSplit < bestValueOfSplit) {
                    bestValueOfSplit = valueOfSplit;
                    goodSplitofPatientsLeft = patientsLeft;
                    goodSplitofPatientsRight = patientsRight;
                    nodeForkHonosVraag = honosVraag;
                    nodeForkThreshold = threshold;
                }
            }
        }
        //zie stap 2
        return new NodeFork(nodeForkHonosVraag, nodeForkThreshold, createNode(goodSplitofPatientsLeft), createNode(goodSplitofPatientsRight));
    }
}
