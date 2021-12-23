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
public class NodeFork implements DecisionTree {

    int honosVraag;
    int threshold;
    DecisionTree left;
    DecisionTree right;

    public NodeFork(int honosVraag, int threshold, DecisionTree left, DecisionTree right) {
        this.honosVraag = honosVraag;
        this.threshold = threshold;
        this.left = left;
        this.right = right;
    }

    @Override
    public int typeer(Patient patient) {
        if (patient.honosScore[honosVraag] < threshold) {
            return left.typeer(patient);
        } else {
            return right.typeer(patient);
        }
    }
}
