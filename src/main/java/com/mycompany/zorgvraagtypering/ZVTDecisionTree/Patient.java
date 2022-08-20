/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zorgvraagtypering.ZVTDecisionTree;

/**
 *
 * @author Siree
 */
public class Patient {
    
    int [] honosScore;
    String locatie; 
    
    public Patient(int[] i, String locatie){
        this.honosScore = i;
        this.locatie = locatie;
    }

    
}
