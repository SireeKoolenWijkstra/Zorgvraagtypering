/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering.ZVTDecisionTree;

import com.mycompany.zorgvraagtypering.ReadCSVFile;
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

        var input = reallearningdata();
        int goed = 0;
        int fout = 0;
        
        
        
        for (int i = 0; i <input.size();i++){
             ArrayList<PatientWithZVT> workdata = (ArrayList<PatientWithZVT>)input.clone();
             workdata.remove(i);
            var decisionTree = DecisionTree.createNode(workdata);
            int ZVT = decisionTree.typeer(input.get(i).patient);
            
            if (ZVT == input.get(i).zorgvraagtype){
                goed++;
            }else{
                fout++;
            }
        }
        System.out.println("goed = " + goed + ", fout = " + fout);

    }

    public static ArrayList<PatientWithZVT> testlearningdata() throws Exception {

        PatientWithZVT tom = new PatientWithZVT(new Patient(new int[]{0, 2, 4, 3, 1, 2, 4, 0, 0, 4, 1, 1}), 7);
        PatientWithZVT julia = new PatientWithZVT(new Patient(new int[]{0, 2, 2, 2, 1, 0, 4, 0, 0, 4, 1, 1}), 1);
        PatientWithZVT henk = new PatientWithZVT(new Patient(new int[]{0, 1, 2, 4, 1, 0, 2, 0, 0, 4, 1, 1}), 6);
        PatientWithZVT ingrid = new PatientWithZVT(new Patient(new int[]{0, 1, 3, 4, 1, 0, 2, 0, 0, 4, 1, 1}), 7);
        PatientWithZVT mergel = new PatientWithZVT(new Patient(new int[]{0, 1, 3, 4, 1, 0, 2, 0, 0, 4, 1, 1}), 23);
        PatientWithZVT ingridde = new PatientWithZVT(new Patient(new int[]{0, 1, 3, 4, 1, 0, 2, 0, 0, 4, 1, 1}), 9);

        ArrayList<PatientWithZVT> patients = new ArrayList<PatientWithZVT>();
        patients.add(henk);
        patients.add(ingrid);
        patients.add(ingridde);
        patients.add(julia);
        patients.add(tom);
        patients.add(mergel);

        return patients;
    }

    public static ArrayList<PatientWithZVT> reallearningdata() throws IOException, Exception {
        String inleespatientsdata = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\inleespatientNCzondernamen.csv";
        ArrayList<PatientWithZVT> patients = new ArrayList<PatientWithZVT>();
        ReadCSVFile readCSVFile = new ReadCSVFile();

        for (var line : readCSVFile.parseHONOSBasic(inleespatientsdata)) {

            int[] honosAnswers = new int[12];
            int zorgtype = Integer.parseInt(line.get(13));

            for (int i = 1; i < 13; i++) {
                honosAnswers[i-1] = Integer.parseInt(line.get(i));
            }
            Patient patient = new Patient(honosAnswers);
            PatientWithZVT WithZVT = new PatientWithZVT(patient, zorgtype);
            patients.add(WithZVT);
        }

        return patients;
    }
    public static ArrayList<Patient> readInputfileToBeTypedPatients() throws IOException, Exception {
        String inleespatientsdata = "C:\\Users\\Siree\\Documents\\School\\PPT-ICT\\Onderzoeksrapport\\bronnen\\inleespatientNCzondernamen.csv";
        ArrayList<Patient >patients = new ArrayList<Patient>();
        ReadCSVFile readCSVFile = new ReadCSVFile();

        for (var line : readCSVFile.parseHONOSBasic(inleespatientsdata)) {

            int[] honosAnswers = new int[12];
            for (int i = 1; i < 13; i++) {
                honosAnswers[i-1] = Integer.parseInt(line.get(i));
            }
            Patient patient = new Patient(honosAnswers);
            patients.add(patient);
        }

        return patients;
    }

}
