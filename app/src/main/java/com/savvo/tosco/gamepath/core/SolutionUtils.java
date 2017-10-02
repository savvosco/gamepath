package com.savvo.tosco.gamepath.core;

import com.eftimoff.patternview.cells.Cell;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.savvo.tosco.gamepath.utils.dijkstra.*;

import com.savvo.tosco.gamepath.beans.Solution;

/**
 * Created by salvotosco on 12/07/17.
 */


public class SolutionUtils {

    private static final int SIZE = 4;

    public static int getPointByCoordinate(int i, int j) {
        return ((SIZE * i) + (j + 1));
    }

    public static Pair<Integer,Integer> getCoordinateByPoint(int point){
        Integer j=point%SIZE;
        Integer i=point/SIZE;
        Pair<Integer,Integer> res = Pair.of(i,j);
        return res;
    }

    /**
     *
     *
     * Returns true if all solution edges are in edges list
     *
     * @param edges
     * @param s
     * @return
     */
    public static boolean checkSolutionEdges(ArrayList<Pair<Integer,Integer>> edges,Solution s, int mode){

        Pair<Integer,Integer> tmpPair;

        for(int i=0; i<s.getNodes().size()-1;i++){
            tmpPair = Pair.of(s.getNodeByIndex(i),s.getNodeByIndex(i+1));
            if(!edges.contains(tmpPair))
                return false;



        }

        return true;
    }

    /**
     *
     * Returns true if there are duplicates in array input, false otherwise.
     *
     * @param nodes
     * @return
     */
    public static boolean duplicates(ArrayList<Integer> nodes){

        Set<Integer> myset = new HashSet<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (!myset.add(nodes.get(i))) {
                return true;
            }
        }
        return false;

    }

    /**
     *
     *
     * Parse patternView output and returns solution string.
     *
     * @param sol
     * @return
     */
    public static String parseSolution(String sol)
    {
        String result="";
        String[] solutions = sol.split("&");
        int i,j;

        for(int k =0; k < solutions.length; k++) {
            i= Integer.parseInt(solutions[k].split("-")[0]);
            j= Integer.parseInt(solutions[k].split("-")[1]);
            int point = SolutionUtils.getPointByCoordinate(i,j);
            if(k==solutions.length-1)
                result+=(point-1)+"";
            else
                result+=(point-1)+",";
        }

        return result;

    }


    /**
     *
     * Returns minimum path size, from startNode to endNode, using Dijkstra algorithm.
     *
     * @param edges
     * @param startNode
     * @param endNode
     * @return
     */
    public static int getDijkstraMinimumPath(List<Edge> edges, int startNode, int endNode){

        int result=0;

        Vertex<Integer> vertexFrom = new Vertex<>(startNode),
                        vertexTo = new Vertex<>(endNode);

        try {
            result = new DijkstraAlgorithm(new Graph(edges)).execute(vertexFrom).getShortestPathLength(vertexFrom, vertexTo);

        } catch (PathNotFoundException e) {
            return -1;
        }

        return result;
    }


    /**
     *
     *
     * Converts edgesList to a List<Edge>, if cells is valorized removes edges of current solution.
     *
     * @param cells
     * @param edgesList
     * @return
     */
    public static List<Edge> convertEdgeSetToListEdges(ArrayList<Pair<Integer,Integer>> edgesList, List<Cell> cells){

        List<Edge> edges = new ArrayList<Edge>();
        Vertex<Integer> fromTmp,toTmp;

        for (int i=0;i<edgesList.size();i++){
            fromTmp = new Vertex<>(edgesList.get(i).getLeft());
            toTmp = new Vertex<>(edgesList.get(i).getRight());
            edges.add(new Edge(fromTmp,toTmp,1));
        }


        ArrayList<Edge> edgeToRemove = new ArrayList<>();

        //removes edges contained in current pattern
        if(cells != null && cells.size() > 1){
            for (int j=0;j<cells.size()-1;j++){

                Vertex vertex1 = new Vertex(SolutionUtils.getPointByCoordinate(cells.get(j).getRow(),cells.get(j).getColumn())-1);
                Vertex vertex2 = new Vertex(SolutionUtils.getPointByCoordinate(cells.get(j+1).getRow(),cells.get(j+1).getColumn())-1);

                Edge tempEdge = new Edge(vertex1,vertex2,1);

                for(int k = 0; k < edges.size();k++){
                    if(tempEdge.equals(edges.get(k))) {

                        //edges.remove(k);
                        edgeToRemove.add(edges.get(k));

                         for(int h = 0; h < edges.size();h++){

                             if(edges.get(h).getDestination().equals(vertex1)  || edges.get(h).getDestination().equals(vertex2))
                                 //edges.remove(h);
                             edgeToRemove.add(edges.get(h));
                         }

                    }

                }

            }
        }

            int s = edgeToRemove.size();

        for(int i = 0;i < edgeToRemove.size();i++){
            edges.remove(edgeToRemove.get(i));
        }

        return edges;
    }


    public static int getVerticalCount(Solution s){
        int result = 0;

        for(int i=0;i<s.getNodes().size()-1; i++){

            Pair<Integer,Integer> start = getCoordinateByPoint(s.getNodeByIndex(i));
            Pair<Integer,Integer> end = getCoordinateByPoint(s.getNodeByIndex(i+1));

            if(start.getLeft()+1 == end.getLeft() && start.getRight()==end.getRight()
                    || start.getLeft()-1 == end.getLeft() && start.getRight()==end.getRight())
                result++;
        }

        return result;
    }

    public static int getOrizontalCount(Solution s){
        int result = 0;

        for(int i=0;i<s.getNodes().size()-1; i++){

            Pair<Integer,Integer> start = getCoordinateByPoint(s.getNodeByIndex(i));
            Pair<Integer,Integer> end = getCoordinateByPoint(s.getNodeByIndex(i+1));

            if(start.getLeft() == end.getLeft() && start.getRight()+1==end.getRight()
                    || start.getLeft() == end.getLeft() && start.getRight()-1==end.getRight())
                result++;
        }

        return result;
    }

    public static int getDiagonalCount(Solution s){
        int result = 0;

        for(int i=0;i<s.getNodes().size()-1; i++){

            Pair<Integer,Integer> start = getCoordinateByPoint(s.getNodeByIndex(i));
            Pair<Integer,Integer> end = getCoordinateByPoint(s.getNodeByIndex(i+1));

            if((start.getLeft()+1 == end.getLeft() && start.getRight()+1==end.getRight() || start.getLeft()-1 == end.getLeft() && start.getRight()-1==end.getRight())
                    || (start.getLeft()-1 == end.getLeft() && start.getRight()+1==end.getRight() || start.getLeft()+1 == end.getLeft() && start.getRight()-1==end.getRight())
                    )
                result++;
        }

        return result;
    }

}
