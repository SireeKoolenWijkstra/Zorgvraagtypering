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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Patient tom    = new Patient(new int[]{0,2,4,3,1,2,4,0,0,4,1,1}, 7);
        Patient julia  = new Patient(new int[]{0,2,2,2,1,0,4,0,0,4,1,1}, 1);
        Patient henk   = new Patient(new int[]{0,1,2,4,1,0,2,0,0,4,1,1}, 6);
        Patient ingrid = new Patient(new int[]{0,1,3,4,1,0,2,0,0,4,1,1}, 7);
        
        ArrayList <Patient> patients = new ArrayList<Patient>();
        patients.add(henk);
        patients.add(ingrid);
        patients.add(julia);
        patients.add(tom);
        
        var varia = DecisionTree.createNode(patients);
        
    }
    
}
