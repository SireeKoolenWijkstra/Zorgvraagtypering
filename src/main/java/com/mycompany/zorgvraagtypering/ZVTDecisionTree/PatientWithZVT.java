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
public class PatientWithZVT {
   
    Patient patient;
    // Zorgvraagtype follows NZA-typesetting (meaning 1 to 21 and nr 9 doesn't exist)
    int zorgvraagtype;

    PatientWithZVT(Patient patient, int zorgvraagtype) throws Exception {
        this.patient = patient;
        if (zorgvraagtype == 9 || zorgvraagtype > 21 || zorgvraagtype < 1){
            throw new Exception ("zorgvraagtype bestaat niet");
        }
        this.zorgvraagtype = zorgvraagtype;
    }
}
