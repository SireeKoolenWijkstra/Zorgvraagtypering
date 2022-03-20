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
public class NodeLeaf implements DecisionTree {

    int zorgvraagtypering;

    NodeLeaf(int zorgvraagtypering) {
        assert zorgvraagtypering > 0;
        assert zorgvraagtypering != 9;
        assert zorgvraagtypering < 22;
        this.zorgvraagtypering = zorgvraagtypering;
    }

    @Override
    public int typeer(Patient patient) {
        return zorgvraagtypering;
    }

    public void print(String indent) {
        System.out.println(indent + zorgvraagtypering);
    }

}
