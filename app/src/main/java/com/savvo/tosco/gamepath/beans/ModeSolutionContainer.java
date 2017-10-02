package com.savvo.tosco.gamepath.beans;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;


/**
 * Created by salvotosco on 19/07/17.
 */

public class ModeSolutionContainer {

    private ArrayList<Pair<Integer,Integer>> edgesSet;
    private ArrayList<String> edgesSetExtra;
    private int totlSolutions;


    public ModeSolutionContainer() {
        this.edgesSet = new ArrayList<>();
        this.edgesSetExtra = new ArrayList<>();
        this.totlSolutions=0;
    }

    public ModeSolutionContainer(ArrayList<Pair<Integer, Integer>> edgesSet, ArrayList<String> edgesSetExtra, int totlSolutions) {
        this.edgesSet = edgesSet;
        this.edgesSetExtra = edgesSetExtra;
        this.totlSolutions = totlSolutions;
    }

    public ArrayList<Pair<Integer, Integer>> getEdgesSet() {
        return edgesSet;
    }

    public void setEdgesSet(ArrayList<Pair<Integer, Integer>> edgesSet) {
        this.edgesSet = edgesSet;
    }

    public ArrayList<String> getEdgesSetExtra() {
        return edgesSetExtra;
    }

    public void setEdgesSetExtra(ArrayList<String> edgesSetExtra) {
        this.edgesSetExtra = edgesSetExtra;
    }

    public int getTotlSolutions() {
        return totlSolutions;
    }

    public void setTotlSolutions(int totlSolutions) {
        this.totlSolutions = totlSolutions;
    }
}
