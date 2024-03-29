/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering.ZVTDecisionTree;

import com.mycompany.zorgvraagtypering.ReadCSVFile;
import com.mycompany.zorgvraagtypering.ZVTDecisionTree.DecisionTree.TreeAndE;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        String inleespatientsdata = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\inleespatientNCzondernamenmetcorrectie (1).csv";

        //https://sorry.vse.cz/~berka/docs/4iz451/dm07-decision-tree-c45.pdf, z is 0,69 according to sorry, according to my calculations 2.09 is the most optimal value.
        double z = 2.09;

        /*
        test om calculate error te testen
       PatientWithZVT tom = new PatientWithZVT(new Patient(new int[]{0, 2, 4, 3, 1, 2, 4, 0, 0, 4, 1, 1}), 7);
       PatientWithZVT julia = new PatientWithZVT(new Patient(new int[]{0, 2, 2, 2, 1, 0, 4, 0, 0, 4, 1, 1}), 1);
       
       ArrayList <PatientWithZVT> workdata = new ArrayList<PatientWithZVT>();
       
       workdata.add(tom);
       workdata.add(julia);
         */
        ArrayList<PatientWithZVT> trainingdata = readtrainingdata(inleespatientsdata);

        crossvalidate(trainingdata, z, true);
        TreeAndE treeAndEDecisiontree = DecisionTree.createNode(trainingdata, z);
        treeAndEDecisiontree.decisiontree.print("");
        System.out.println("trainingserror:");
        printErrors(trainingdata, treeAndEDecisiontree.decisiontree);
        giveOverviewZ(trainingdata);

    }

    public static ArrayList<PatientWithZVT> testlearningdata() throws Exception {

        PatientWithZVT p1 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, "Centrum"), 1);
        PatientWithZVT p2 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, "Zuidoost"), 2);
        /*
        PatientWithZVT p3 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0}), 3);
        PatientWithZVT p4 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0}), 4);
        PatientWithZVT p5 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0}), 5);
        PatientWithZVT p6 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}), 6);
        PatientWithZVT p7 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0}), 7);
        PatientWithZVT p8 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0}), 8);
        PatientWithZVT p21 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0}), 21);
        PatientWithZVT p10 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}), 10);
        PatientWithZVT p11 = new PatientWithZVT(new Patient(new int[]{0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0}), 11);
        PatientWithZVT p12 = new PatientWithZVT(new Patient(new int[]{0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}), 12);
        PatientWithZVT p13 = new PatientWithZVT(new Patient(new int[]{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}), 13);
        PatientWithZVT p14 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}), 14);
        PatientWithZVT p15 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0}), 15);
        PatientWithZVT p16 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0}), 16);
        PatientWithZVT p17 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0}), 17);
        PatientWithZVT p18 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}), 18);
        PatientWithZVT p19 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0}), 19);
        PatientWithZVT p20 = new PatientWithZVT(new Patient(new int[]{0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0}), 20);
         */

        ArrayList<PatientWithZVT> patients = new ArrayList<PatientWithZVT>();
        patients.add(p1);
        /*patients.add(p2);
        patients.add(p3);
        patients.add(p4);
        patients.add(p5);
        patients.add(p6);
        patients.add(p7);
        patients.add(p8);
        patients.add(p21);
        patients.add(p10);
        patients.add(p11);
        patients.add(p12);
         patients.add(p13);
        patients.add(p14);
        patients.add(p15);
        patients.add(p16);
        patients.add(p17);
        patients.add(p18);
        patients.add(p19);
        patients.add(p20);
         */

        return patients;
    }

    public static ArrayList<PatientWithZVT> readtrainingdata(String inleespatientsdatafile) throws IOException, Exception {

        ArrayList<PatientWithZVT> patients = new ArrayList<PatientWithZVT>();
        ReadCSVFile readCSVFile = new ReadCSVFile();

        for (var line : readCSVFile.parseHONOSBasic(inleespatientsdatafile)) {
            /*header van inleesbestand is: 
            0: KLNT_NUMMER	
            1: KLNT_VOORLETTERS
            2: KLNT_VOORVOEGSELS
            3: KLNT_NAAM	
            4: BIAG_AFDELING_NUMMER	
            5-16: honosAnswers 12x
            17: ZVT dataset
             */

            int[] honosAnswers = new int[12];
            String locatie = line.get(4);
            int zorgtype = Integer.parseInt(line.get(17));

            for (int i = 0; i < 12; i++) {
                honosAnswers[i] = Integer.parseInt(line.get(i + 5));
            }
            Patient patient = new Patient(honosAnswers, locatie);
            PatientWithZVT WithZVT = new PatientWithZVT(patient, zorgtype);
            patients.add(WithZVT);
        }

        return patients;
    }

    /*public static ArrayList<Patient> readInputfileToBeTypedPatients() throws IOException, Exception {
        String inleespatientsdata = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\inleespatientNCzondernamenmetcorrectie.csv";
        ArrayList<Patient> patients = new ArrayList<Patient>();
        ReadCSVFile readCSVFile = new ReadCSVFile();

        for (var line : readCSVFile.parseHONOSBasic(inleespatientsdata)) {

            int[] honosAnswers = new int[12];
            for (int i = 1; i < 13; i++) {
                honosAnswers[i - 1] = Integer.parseInt(line.get(i));
            }
            Patient patient = new Patient(honosAnswers);
            patients.add(patient);
        }

        return patients;
    }
     */
    public static int crossvalidate(ArrayList<PatientWithZVT> trainingdata, double z, boolean verbose) throws IOException, Exception {

        int goed = 0;
        int fout = 0;

        /*
        cross validation, ik train op 1 minder dan wat er aan input is
         */
        for (int i = 0; i < trainingdata.size(); i++) {
            ArrayList<PatientWithZVT> workdata = (ArrayList<PatientWithZVT>) trainingdata.clone();
            workdata.remove(i);
            TreeAndE treeAndEDecisiontree = DecisionTree.createNode(workdata, z);
            int ZVT = treeAndEDecisiontree.decisiontree.typeer(trainingdata.get(i).patient);

            if (ZVT == trainingdata.get(i).zorgvraagtype) {
                goed++;
            } else {
                if (verbose) {
                    System.out.println(trainingdata.get(i).patient.locatie + ": " + ZVT + " moet zijn " + trainingdata.get(i).zorgvraagtype);
                }
                fout++;
            }
        }
        if (verbose) {
            System.out.println(goed + ";" + fout + ";");
        }
        return fout;

    }

    public static void giveOverviewZ(ArrayList<PatientWithZVT> trainingsdata) throws Exception {

        for (double z = 0; z < 8.0; z = z + 0.01) {

            System.out.println(z + " " + crossvalidate(trainingsdata, z, false));
        }

    }

    public static void printErrors(ArrayList<PatientWithZVT> trainingsdata, DecisionTree decisiontree) {
        for (PatientWithZVT patient : trainingsdata) {
            int dtZVT = decisiontree.typeer(patient.patient);
            if (patient.zorgvraagtype != dtZVT) {
                System.out.println(patient.patient.locatie + ": " + dtZVT + " moet zijn " + patient.zorgvraagtype);
            }
        }
    }

}
