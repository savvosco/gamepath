package com.savvo.tosco.gamepath.beans;

import java.util.ArrayList;

/**
 * Created by salvotosco on 22/06/17.
 */


public class Solution extends  Object{

    private ArrayList<Integer> nodes;

    public Solution(String solution) {
        nodes = new ArrayList<>();
        fillSolution(solution);

    }

    private void fillSolution(String sol) {

        if(sol.startsWith("["))
        {
            sol=sol.replace("[","");
            sol=sol.replace("]","");
            sol=sol.replace(" ","");
        }

        String[] tmp = sol.split(",");

        for (int i = 0; i < tmp.length; i++) {
            nodes.add(i,Integer.parseInt(tmp[i]));;
        }
    }

    public int getNodeByIndex(int index){
        return nodes.get(index);
    }

    public ArrayList<Integer> getNodes(){
        return this.nodes;
    }

    public int getSolutionSize(){
        return nodes.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        return nodes.equals(solution.nodes);

    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
