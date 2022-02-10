/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering.ZVTDecisionTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

        double totaal = 0;
        for (int nrOfPt : tel_PtperZVT(patients)) {
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
            //Je vergelijkt ieders zorgvraagtype met de zorgvraagtype van de eerste patiënt. Als niet iedereen hetzelfde is, moet je een split maken 
            //en niet een node leaf. Anders eindigt hier de recursie. 
            if (zorgvraagtypering != pt.zorgvraagtype) {
                oneZorgvraagtypering = false;
            }
        }
        if (oneZorgvraagtypering) {
            return new NodeLeaf(zorgvraagtypering);
        }
        double bestValueOfSplit = Double.POSITIVE_INFINITY; //Double.Postitive_infinity is een extreem hoge waarde. Hoe schever de de verdeling over de 
        //zorgvraagtyperingen, hoe lager de entropy(wat beter is)
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
         NodeFork nodeFork = new NodeFork(nodeForkHonosVraag, nodeForkThreshold, createNode(goodSplitofPatientsLeft), createNode(goodSplitofPatientsRight));
         double e = calculateTrueErrorValue(trainingGroup);

         return nodeFork;
    }

    /*
    Pruning
    perpectief van de nodeFork die mogelijk een nodeLeaf wordt
    stap 1: bereken de f en de e. De f is E/N (aantal pt's in node met een ander zvt dan het meerendeel 
	van de pt's in die node zitten gedeeld door het aantal in de node (wat N is)). De e is 
	de ophoging van f van je decision tree. F moet worden opgehoogd omdat de decision tree 
	overgefit is op de data. De c is 0.25 standaard, z is dan 0.69. 
    stap 2a:bereken de f en e van de linker node
    stap 2b:bereken de f en de e van de rechter node
    stap 3: combineer de f en de e van de linker en rechter node
    stap 4: vergelijk de gecombineerde e met je eigen e
    stap 5a:als hun gecombineerde e groter is, tranformeer tot een leaf node
    stap 5b:als hun gecombineerde e kleiner of gelijk is, herhaal de stappen voor zowel de linker child 
            node als de rechter childe node
     */

    public static int[] tel_PtperZVT(ArrayList<PatientWithZVT> patients) {
        int[] perZVTaantalPt = new int[20];
        for (PatientWithZVT patient : patients) {
            //If statementchecks because ZVT 9 doesn't exist and ZVTtypes go up to ZVT21 (problem due to indexing)

            if (patient.zorgvraagtype < 9) {
                perZVTaantalPt[patient.zorgvraagtype - 1]++;
            } else {
                perZVTaantalPt[patient.zorgvraagtype - 2]++;
            }
        }
        return perZVTaantalPt;

    }

    public static double calculateTrueErrorValue(ArrayList<PatientWithZVT> trainingGroup) {
        //N is totaal aantal pt's in node
        int N = trainingGroup.size();
        int[] ptperZVT = tel_PtperZVT(trainingGroup);
        int max = 0;

        //bij equal gesplitste populatie maakt het voor de max niks uit, max behoudt dezelfde waarde
        for (var nrPT : ptperZVT) {
            if (nrPT > max) {
                max = nrPT;
            }
        }
        // E is de errorwaarde van je eigen decisiontree die dus iets te goed is omdat je decisiontree overfitted op de data is
        int E = N - max;
        int f = E / N;

        //e is de ophoging van f naar een pessimistich geschatte echte errorwaarde, berekening is terug te vinden op 
        //https://sorry.vse.cz/~berka/docs/4iz451/dm07-decision-tree-c45.pdf, z is 0,69. 
        double z = 0.69;
        double e = (f + ((z * z) / (2 * N)) + z * Math.sqrt((f / N) - ((f * f) / N) + ((z * z) / (4 * N * N)))) / (1 + (z * z) / N);
        return e;
    }

}
