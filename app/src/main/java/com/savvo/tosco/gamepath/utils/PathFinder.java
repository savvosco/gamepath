package com.savvo.tosco.gamepath.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.savvo.tosco.gamepath.beans.Solution;


import com.savvo.tosco.gamepath.core.SolutionUtils;
import com.savvo.tosco.gamepath.utils.dijkstra.*;


/**
 * Created by salvotosco on 20/06/17.
 */

public class PathFinder {

    public static int countSolution=0;
    public static final int SOLUTION_NUMBER=16;
    public static final int SOLUTION_NUMBER_NOT_1=15;
    public static final int SOLUTION_NUMBER_NOT_2=14;
    public static final int SOLUTION_NUMBER_NOT_3=13;

    public static ArrayList<String> lines = new ArrayList<String>();



    public static void main(String[] args) throws IOException {

        //generateSolutionsFile();
        //intersectionTest();
        //solutionScoreTest();
        //printEdgeSet();
        //getStartEndPointStatistics2();
        //getStartEndPointStatisticsExcluding1Node();
        //testSub_1_Solution();
        //dijstraTest();

        //getStartEndPointStatisticsExcluding1NodeNoIntersection();

        //getStartEndPointStatistics2();


        //TODO run in background?
        //createFileForStartEndCombination();

        //getStartEndPointStatisticsExcluding2NodeNoIntersection();


        testStartEndNotIntersections();

    }


    // recursive dfs
    private static void dfs_rec(ArrayList<ArrayList<Integer>> adjLists, boolean[] visited, int v, int d,
                                List<Integer> path) {
        visited[v] = true;
        path.add(v);

        if (v == d) {

            ArrayList<String> allSolutions = new ArrayList<String>();
            ArrayList<String> hamiltonianSolutions = new ArrayList<String>();

            for (int i = 0; i < path.size(); i++) {
                allSolutions.add(i,path.get(i)+"");
            }

            if(path.size() == SOLUTION_NUMBER_NOT_2) {
                hamiltonianSolutions.add(allSolutions.toString());
                countSolution++;
                lines.add(hamiltonianSolutions.toString());
            }

        }
        else {
            for (int w : adjLists.get(v)) {
                if (!visited[w] /*&& w!=0*/) {
                    dfs_rec(adjLists, visited, w, d, path);
                }

            }
        }
        path.remove(path.size() - 1);
        visited[v] = false;
    }

    // Usually dfs_rec() would be sufficient. However, if we don't want to pass
// a boolean array to our function, we can use another function dfs().
// We only have to pass the adjacency list and the source node to dfs(),
// as opposed to dfs_rec(), where we have to pass the boolean array
// additionally.
    public static void dfs(ArrayList<ArrayList<Integer>> adjLists, int s, int d) {
        int n = adjLists.size();
        boolean[] visited = new boolean[n];

        List<Integer> path = new ArrayList<Integer>();
        int path_index = 0; // Initialize path[] as empty
        dfs_rec(adjLists, visited, s, d, path);
    }



    private static void generateSolutionsFile() {


        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }



        adjLists.get(0).clear();

        /*
          *
          * VERTICAL EDGES: 60
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(1);
        adjLists.get(0).add(5);

        //adjacency list for vertex 1
        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(4);
        adjLists.get(1).add(6);

        //adjacency list for vertex 2
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(2).add(5);
        adjLists.get(2).add(7);

        //adjacency list for vertex 3
        adjLists.get(3).add(2);
        adjLists.get(3).add(6);

        //adjacency list for vertex 4
        adjLists.get(4).add(1);
        adjLists.get(4).add(5);
        adjLists.get(4).add(9);

        //adjacency list for vertex 5
        //adjLists.get(5).add(0);
        adjLists.get(5).add(2);
        adjLists.get(5).add(4);
        adjLists.get(5).add(6);
        adjLists.get(5).add(8);
        adjLists.get(5).add(10);

        //adjacency list for vertex 6
        adjLists.get(6).add(1);
        adjLists.get(6).add(3);
        adjLists.get(6).add(5);
        adjLists.get(6).add(7);
        adjLists.get(6).add(9);
        adjLists.get(6).add(11);

        //adjacency list for vertex 7
        adjLists.get(7).add(2);
        adjLists.get(7).add(6);
        adjLists.get(7).add(10);

        //adjacency list for vertex 8
        adjLists.get(8).add(5);
        adjLists.get(8).add(9);
        adjLists.get(8).add(13);

        //adjacency list for vertex 9
        adjLists.get(9).add(4);
        adjLists.get(9).add(6);
        adjLists.get(9).add(8);
        adjLists.get(9).add(10);
        adjLists.get(9).add(12);
        adjLists.get(9).add(14);

        //adjacency list for vertex 10
        adjLists.get(10).add(5);
        adjLists.get(10).add(7);
        adjLists.get(10).add(9);
        adjLists.get(10).add(11);
        adjLists.get(10).add(13);
        adjLists.get(10).add(15);

        //adjacency list for vertex 11
        adjLists.get(11).add(6);
        adjLists.get(11).add(10);
        adjLists.get(11).add(14);

        //adjacency list for vertex 12
        adjLists.get(12).add(9);
        adjLists.get(12).add(13);

        //adjacency list for vertex 13
        adjLists.get(13).add(8);
        adjLists.get(13).add(10);
        adjLists.get(13).add(12);
        adjLists.get(13).add(14);

        //adjacency list for vertex 14
        adjLists.get(14).add(9);
        adjLists.get(14).add(11);
        adjLists.get(14).add(13);
        adjLists.get(14).add(15);

        //adjacency list for vertex 15
        adjLists.get(15).add(10);
        adjLists.get(15).add(14);


        /*
        *
        * Matrix 4x4 - END
        *
        * */



        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {
            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if(i!=j)  {
                    dfs(adjLists, i, j);
                }
            }
        }


       //File f = new File("/Users/salvotosco/Desktop/all-solutionss");

        List<String> filelinesTest=null;
        try {
            filelinesTest = FileUtils.readLines(new File("/Users/salvotosco/Desktop/SOL/mode-solutions/solNoOrizontal"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int sizeIntersect = 0;

        for(int j=0;j<filelinesTest.size();j++){

            String tmpLine = filelinesTest.get(j);

            Solution s = new Solution(tmpLine);

            if(isIntersection(s)) {
                sizeIntersect++;
            }
            else
                System.out.println(s.getNodes().toString());

        }

        System.out.println("SIZE: "+filelinesTest.size() + " INTERSECT: " +sizeIntersect);

        /*
        File f = new File("/Users/salvotosco/Desktop/SOL/onlyVertical");
        try {
            FileUtils.writeLines(f,lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    //TODO GENERATE SCORE FOR EDGE
    private static void printEdgeSet() {

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }

        /*
          *
          * ALL EDGES: 84
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(1);
        adjLists.get(0).add(4);
        adjLists.get(0).add(5);

        //adjacency list for vertex 1
        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(4);
        adjLists.get(1).add(5);
        adjLists.get(1).add(6);

        //adjacency list for vertex 2
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(2).add(5);
        adjLists.get(2).add(6);
        adjLists.get(2).add(7);

        //adjacency list for vertex 3
        adjLists.get(3).add(2);
        adjLists.get(3).add(6);
        adjLists.get(3).add(7);

        //adjacency list for vertex 4
        adjLists.get(4).add(0);
        adjLists.get(4).add(1);
        adjLists.get(4).add(5);
        adjLists.get(4).add(8);
        adjLists.get(4).add(9);

        //adjacency list for vertex 5
        adjLists.get(5).add(0);
        adjLists.get(5).add(1);
        adjLists.get(5).add(2);
        adjLists.get(5).add(4);
        adjLists.get(5).add(6);
        adjLists.get(5).add(8);
        adjLists.get(5).add(9);
        adjLists.get(5).add(10);

        //adjacency list for vertex 6
        adjLists.get(6).add(1);
        adjLists.get(6).add(2);
        adjLists.get(6).add(3);
        adjLists.get(6).add(5);
        adjLists.get(6).add(7);
        adjLists.get(6).add(9);
        adjLists.get(6).add(10);
        adjLists.get(6).add(11);

        //adjacency list for vertex 7
        adjLists.get(7).add(2);
        adjLists.get(7).add(3);
        adjLists.get(7).add(6);
        adjLists.get(7).add(10);
        adjLists.get(7).add(11);

        //adjacency list for vertex 8
        adjLists.get(8).add(4);
        adjLists.get(8).add(5);
        adjLists.get(8).add(9);
        adjLists.get(8).add(12);
        adjLists.get(8).add(13);

        //adjacency list for vertex 9
        adjLists.get(9).add(4);
        adjLists.get(9).add(5);
        adjLists.get(9).add(6);
        adjLists.get(9).add(8);
        adjLists.get(9).add(10);
        adjLists.get(9).add(12);
        adjLists.get(9).add(13);
        adjLists.get(9).add(14);

        //adjacency list for vertex 10
        adjLists.get(10).add(5);
        adjLists.get(10).add(6);
        adjLists.get(10).add(7);
        adjLists.get(10).add(9);
        adjLists.get(10).add(11);
        adjLists.get(10).add(13);
        adjLists.get(10).add(14);
        adjLists.get(10).add(15);

        //adjacency list for vertex 11
        adjLists.get(11).add(6);
        adjLists.get(11).add(7);
        adjLists.get(11).add(10);
        adjLists.get(11).add(14);
        adjLists.get(11).add(15);

        //adjacency list for vertex 12
        adjLists.get(12).add(8);
        adjLists.get(12).add(9);
        adjLists.get(12).add(13);

        //adjacency list for vertex 13
        adjLists.get(13).add(8);
        adjLists.get(13).add(9);
        adjLists.get(13).add(10);
        adjLists.get(13).add(12);
        adjLists.get(13).add(14);

        //adjacency list for vertex 14
        adjLists.get(14).add(9);
        adjLists.get(14).add(10);
        adjLists.get(14).add(11);
        adjLists.get(14).add(13);
        adjLists.get(14).add(15);

        //adjacency list for vertex 15
        adjLists.get(15).add(10);
        adjLists.get(15).add(11);
        adjLists.get(15).add(14);

        /*
        *
        * Matrix 4x4 - END
        *
        * */


        for(int i=0;i<adjLists.size();i++){
            for(int j=0;j<adjLists.get(i).size();j++){
                    System.out.println(i+"-"+adjLists.get(i).get(j));
            }
        }

    }

    public static int getSolutionScore(Solution s,ArrayList<Pair<String,Integer>> edgeStatistics){

        int result=0;
        for (int i=0;i<s.getNodes().size()-1;i++){
            result+=getEdgeScore(s.getNodeByIndex(i),s.getNodeByIndex(i+1),edgeStatistics,343184,s);
        }
        return result;
    }

    private static void solutionScoreTest() {


        List<String> filelinesTest=null;
        try {
            filelinesTest = FileUtils.readLines(new File("/Users/salvotosco/Desktop/SOL/EDGE-STAT/edgeAllCount"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Pair<String,Integer>> edgeStatistics = new ArrayList<>();

        for (int i=0;i<filelinesTest.size();i++){
            String [] tmp = filelinesTest.get(i).split("-");
            edgeStatistics.add(Pair.of(tmp[0]+"-"+tmp[1],Integer.parseInt(tmp[2])));

        }

        List<String> filelinesAllSol=null;
        try {
            filelinesAllSol = FileUtils.readLines(new File("/Users/salvotosco/Desktop/SOL/mode-solutions/all-solutions"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> tmpRres = new ArrayList<>();
        Solution solTmp;
        double score;


        double max =0;
        double min=1000;
        double media = 0;
        int sum =0;

        for (int i=0;i<filelinesAllSol.size()-1;i++){
            solTmp = new Solution(filelinesAllSol.get(i));
            score = getSolutionScore(solTmp,edgeStatistics);

            tmpRres.add(solTmp.getNodes()+" --- SCORE: "+score);

            if(score >= max)
                max=score;

            if(score<= min)
                min=score;

            sum+=score;

        }

        media= sum/filelinesAllSol.size()-1;

        System.out.println("MIN: "+min);
        System.out.println("MAX: "+max);
        System.out.println("MEDIA: "+media);


        File f = new File("/Users/salvotosco/Desktop/SOL/edgeScore");
        try {
            FileUtils.writeLines(f,tmpRres);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void intersectionTest() {

        List<String> filelinesTest=null;
        try {
            filelinesTest = FileUtils.readLines(new File("/Users/salvotosco/Desktop/SOL/mode-solutions/solNoOrizontal"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int countintersect =0,noIntersect=0;

        for (int i =0;i<filelinesTest.size();i++){

            Solution s = new Solution(filelinesTest.get(i));

            boolean isinter = isIntersection(s);

            if(isinter)
                countintersect++;
            else {
                noIntersect++;
                System.out.println("NO INTERSECT --> "+s.getNodes().toString());
            }

            //System.out.println("SOL:"+filelinesTest.get(i)+" INTERSECT: "+ isinter);
        }

        System.out.println("TOTAL SOL: "+filelinesTest.size()+" INTESECT: "+countintersect + " NO INTRSECT: "+noIntersect);

    }

    public static int getEdgeScore(int a, int b,ArrayList<Pair<String,Integer>> edgeStatistics,int total,Solution s){

        double tmpRes=0;
        int result=0;
        String edge = a+"-"+b;

        for (int i =0;i < edgeStatistics.size();i++){
            if(edgeStatistics.get(i).getLeft().equals(edge)){
                tmpRes+=edgeStatistics.get(i).getRight();
                break;
            }
        }

        int numVer=getVerticalCount(s);
        int numOri=getOrizontalCount(s);
        int numDia=getDiagonalCount(s);

        //no orizonatal
        if(numOri==0){

        }
        //no vertical
        else if(numVer==0){

        }
        //no diagonal
        else if(numDia == 0){

        }

        tmpRes/=total;

        if(tmpRes > 0 &&  tmpRes <= 0.1)
            result = 10;
        else if(tmpRes > 0.1 &&  tmpRes <= 0.2)
            result=8;
        else if(tmpRes > 0.2 &&  tmpRes <= 0.3)
            result=3;
        else if(tmpRes > 0.3 &&  tmpRes <= 0.4)
            result=2;
        else
            result=1;

        return result;
    }

    public static int getVerticalCount(Solution s){
        int result = 0;

        for(int i=0;i<15; i++){

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

        for(int i=0;i<15; i++){

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

        for(int i=0;i<15; i++){

            Pair<Integer,Integer> start = getCoordinateByPoint(s.getNodeByIndex(i));
            Pair<Integer,Integer> end = getCoordinateByPoint(s.getNodeByIndex(i+1));

            if((start.getLeft()+1 == end.getLeft() && start.getRight()+1==end.getRight() || start.getLeft()-1 == end.getLeft() && start.getRight()-1==end.getRight())
                    || (start.getLeft()-1 == end.getLeft() && start.getRight()+1==end.getRight() || start.getLeft()+1 == end.getLeft() && start.getRight()-1==end.getRight())
                    )
                result++;
        }

        return result;
    }

    public static Pair<Integer,Integer> getCoordinateByPoint(int point){
        Integer j=point%4;
        Integer i=point/4;
        Pair<Integer,Integer> res = Pair.of(i,j);
        return res;
    }

    public static boolean isIntersection(Solution sol) {

        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> diagonalEdges = new ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();

        for (int i = 0; i < sol.getNodes().size(); i++) {
            pairs.add(SolutionUtils.getCoordinateByPoint(sol.getNodeByIndex(i)));
        }

        Pair<Integer, Integer> pairA,pairB;

        //scorro gli archi e individuo quelli diagonali
        for (int j=0; j< pairs.size()-1;j++){
            pairA = pairs.get(j);
            pairB = pairs.get(j+1);
            if((pairA.getLeft()+1==pairB.getLeft() && pairA.getRight()+1==pairB.getRight()) || (pairA.getLeft()-1==pairB.getLeft() && pairA.getRight()-1==pairB.getRight())
                    || ((pairA.getLeft()+1==pairB.getLeft() && pairA.getRight()-1==pairB.getRight()) || (pairA.getLeft()-1==pairB.getLeft() && pairA.getRight()+1==pairB.getRight())))
            {
                diagonalEdges.add(Pair.of(pairA,pairB));
            }

        }

        if(diagonalEdges.size()<=1)
            return false;
        else{

            //per ogni arco diagonale cerco se c'è quello che lo incrocia
            Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> tmpEdgeA,tmpEdgeB;

            for (int i=0; i< diagonalEdges.size();i++){

                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> currentEdge = diagonalEdges.get(i);

                if(currentEdge.getLeft().getLeft()+1 == currentEdge.getRight().getLeft() && currentEdge.getLeft().getRight()-1==currentEdge.getRight().getRight())
                {

                    tmpEdgeA = Pair.of(Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()-1),Pair.of(currentEdge.getLeft().getLeft()+1,currentEdge.getLeft().getRight()));
                    tmpEdgeB = Pair.of(Pair.of(currentEdge.getLeft().getLeft()+1,currentEdge.getLeft().getRight()),Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()-1));

                    if(diagonalEdges.contains(tmpEdgeA) || diagonalEdges.contains(tmpEdgeB))
                        return true;
                }

                if(currentEdge.getLeft().getLeft()-1 == currentEdge.getRight().getLeft() && currentEdge.getLeft().getRight()+1==currentEdge.getRight().getRight())
                {
                    tmpEdgeA = Pair.of(Pair.of(currentEdge.getLeft().getLeft()-1,currentEdge.getLeft().getRight()),Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()+1));
                    tmpEdgeB = Pair.of(Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()+1),Pair.of(currentEdge.getLeft().getLeft()-1,currentEdge.getLeft().getRight()));

                    if(diagonalEdges.contains(tmpEdgeA) || diagonalEdges.contains(tmpEdgeB))
                        return true;
                }

                if((currentEdge.getLeft().getLeft()+1 == currentEdge.getRight().getLeft() && currentEdge.getLeft().getRight()+1==currentEdge.getRight().getRight())){

                    tmpEdgeA = Pair.of(Pair.of(currentEdge.getLeft().getLeft()+1,currentEdge.getLeft().getRight()),Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()+1));
                    tmpEdgeB = Pair.of(Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()+1),Pair.of(currentEdge.getLeft().getLeft()+1,currentEdge.getLeft().getRight()));

                    if(diagonalEdges.contains(tmpEdgeA) || diagonalEdges.contains(tmpEdgeB))
                        return true;
                }

                if(currentEdge.getLeft().getLeft()-1 == currentEdge.getRight().getLeft() && currentEdge.getLeft().getRight()-1==currentEdge.getRight().getRight())
                {
                    tmpEdgeA = Pair.of(Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getRight().getRight()+1),Pair.of(currentEdge.getLeft().getLeft()+1,currentEdge.getLeft().getRight()));
                    tmpEdgeB = Pair.of(Pair.of(currentEdge.getLeft().getLeft()-1,currentEdge.getRight().getRight()),Pair.of(currentEdge.getLeft().getLeft(),currentEdge.getLeft().getRight()-1));

                    if(diagonalEdges.contains(tmpEdgeA) || diagonalEdges.contains(tmpEdgeB))
                        return true;
                }

            }
        }

        return false;

    }

    public static void getStartEndPointStatistics(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = getMatrix4x4All();
        ArrayList<String> results = new ArrayList<>();

        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {
            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if(i!=j)  {

                    dfs(adjLists, i, j);
/*
                    int countIntersectionSol = 0;

                    for (int z = 0; z < lines.size();z++){
                        Solution tmpSol = new Solution(lines.get(z));
                        if(!isIntersection(tmpSol)){
                            countIntersectionSol++;
                        }
                    }

                    results.add("FROM: "+i+" TO: "+j+" SIZE: "+lines.size()+" NO-INTERS: "+countIntersectionSol);
                    */
                }

                lines.clear();
            }
        }

        int s = results.size();
        String str = results.toString();

    }

    private static ArrayList<ArrayList<Integer>> getMatrix4x4All(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }

        /*
          *
          * ALL EDGES: 84
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(1);
        adjLists.get(0).add(4);
        adjLists.get(0).add(5);

        //adjacency list for vertex 1

        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(4);
        adjLists.get(1).add(5);
        adjLists.get(1).add(6);


        //adjacency list for vertex 2
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(2).add(5);
        adjLists.get(2).add(6);
        adjLists.get(2).add(7);

        //adjacency list for vertex 3
        adjLists.get(3).add(2);
        adjLists.get(3).add(6);
        adjLists.get(3).add(7);

        //adjacency list for vertex 4
        adjLists.get(4).add(0);
        adjLists.get(4).add(1);
        adjLists.get(4).add(5);
        adjLists.get(4).add(8);
        adjLists.get(4).add(9);

        //adjacency list for vertex 5
        adjLists.get(5).add(0);
        adjLists.get(5).add(1);
        adjLists.get(5).add(2);
        adjLists.get(5).add(4);
        adjLists.get(5).add(6);
        adjLists.get(5).add(8);
        adjLists.get(5).add(9);
        adjLists.get(5).add(10);

        //adjacency list for vertex 6
        adjLists.get(6).add(1);
        adjLists.get(6).add(2);
        adjLists.get(6).add(3);
        adjLists.get(6).add(5);
        adjLists.get(6).add(7);
        adjLists.get(6).add(9);
        adjLists.get(6).add(10);
        adjLists.get(6).add(11);

        //adjacency list for vertex 7
        adjLists.get(7).add(2);
        adjLists.get(7).add(3);
        adjLists.get(7).add(6);
        adjLists.get(7).add(10);
        adjLists.get(7).add(11);

        //adjacency list for vertex 8
        adjLists.get(8).add(4);
        adjLists.get(8).add(5);
        adjLists.get(8).add(9);
        adjLists.get(8).add(12);
        adjLists.get(8).add(13);

        //adjacency list for vertex 9
        adjLists.get(9).add(4);
        adjLists.get(9).add(5);
        adjLists.get(9).add(6);
        adjLists.get(9).add(8);
        adjLists.get(9).add(10);
        adjLists.get(9).add(12);
        adjLists.get(9).add(13);
        adjLists.get(9).add(14);

        //adjacency list for vertex 10
        adjLists.get(10).add(5);
        adjLists.get(10).add(6);
        adjLists.get(10).add(7);
        adjLists.get(10).add(9);
        adjLists.get(10).add(11);
        adjLists.get(10).add(13);
        adjLists.get(10).add(14);
        adjLists.get(10).add(15);

        //adjacency list for vertex 11
        adjLists.get(11).add(6);
        adjLists.get(11).add(7);
        adjLists.get(11).add(10);
        adjLists.get(11).add(14);
        adjLists.get(11).add(15);

        //adjacency list for vertex 12
        adjLists.get(12).add(8);
        adjLists.get(12).add(9);
        adjLists.get(12).add(13);

        //adjacency list for vertex 13
        adjLists.get(13).add(8);
        adjLists.get(13).add(9);
        adjLists.get(13).add(10);
        adjLists.get(13).add(12);
        adjLists.get(13).add(14);

        //adjacency list for vertex 14
        adjLists.get(14).add(9);
        adjLists.get(14).add(10);
        adjLists.get(14).add(11);
        adjLists.get(14).add(13);
        adjLists.get(14).add(15);

        //adjacency list for vertex 15
        adjLists.get(15).add(10);
        adjLists.get(15).add(11);
        adjLists.get(15).add(14);

        return adjLists;
    }

    private static ArrayList<ArrayList<Integer>> getMatric4x4NoDiagonal(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }

        /*
          *
          * NO DIAGONAL EDGES: 48
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(1);
        adjLists.get(0).add(4);

        //adjacency list for vertex 1
        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(5);

        //adjacency list for vertex 2
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(2).add(6);

        //adjacency list for vertex 3
        adjLists.get(3).add(2);
        adjLists.get(3).add(7);

        //adjacency list for vertex 4
        adjLists.get(4).add(0);
        adjLists.get(4).add(5);
        adjLists.get(4).add(8);

        //adjacency list for vertex 5
        adjLists.get(5).add(1);
        adjLists.get(5).add(4);
        adjLists.get(5).add(6);
        adjLists.get(5).add(9);


        //adjacency list for vertex 6
        adjLists.get(6).add(2);
        adjLists.get(6).add(5);
        adjLists.get(6).add(7);
        adjLists.get(6).add(10);

        //adjacency list for vertex 7
        adjLists.get(7).add(3);
        adjLists.get(7).add(6);
        adjLists.get(7).add(11);

        //adjacency list for vertex 8
        adjLists.get(8).add(4);
        adjLists.get(8).add(9);
        adjLists.get(8).add(12);

        //adjacency list for vertex 9
        adjLists.get(9).add(5);
        adjLists.get(9).add(8);
        adjLists.get(9).add(10);
        adjLists.get(9).add(13);


        //adjacency list for vertex 10
        adjLists.get(10).add(6);
        adjLists.get(10).add(9);
        adjLists.get(10).add(11);
        adjLists.get(10).add(14);


        //adjacency list for vertex 11
        adjLists.get(11).add(7);
        adjLists.get(11).add(10);
        adjLists.get(11).add(15);

        //adjacency list for vertex 12
        adjLists.get(12).add(8);
        adjLists.get(12).add(13);

        //adjacency list for vertex 13
        adjLists.get(13).add(9);
        adjLists.get(13).add(12);
        adjLists.get(13).add(14);

        //adjacency list for vertex 14
        adjLists.get(14).add(10);
        adjLists.get(14).add(13);
        adjLists.get(14).add(15);

        //adjacency list for vertex 15
        adjLists.get(15).add(11);
        adjLists.get(15).add(14);


        /*
        *
        * Matrix 4x4 - END
        *
        * */


        return adjLists;


    }

    private static ArrayList<ArrayList<Integer>> getMatrix4x4NoOrizontal(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }

        /*
          *
          * NO ORIZONAL EDGES 60
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(4);
        adjLists.get(0).add(5);

        //adjacency list for vertex 1
        adjLists.get(1).add(4);
        adjLists.get(1).add(5);
        adjLists.get(1).add(6);

        //adjacency list for vertex 2
        adjLists.get(2).add(5);
        adjLists.get(2).add(6);
        adjLists.get(2).add(7);

        //adjacency list for vertex 3
        adjLists.get(3).add(6);
        adjLists.get(3).add(7);

        //adjacency list for vertex 4
        adjLists.get(4).add(0);
        adjLists.get(4).add(1);
        adjLists.get(4).add(8);
        adjLists.get(4).add(9);

        //adjacency list for vertex 5
        adjLists.get(5).add(0);
        adjLists.get(5).add(1);
        adjLists.get(5).add(2);
        adjLists.get(5).add(8);
        adjLists.get(5).add(9);
        adjLists.get(5).add(10);

        //adjacency list for vertex 6
        adjLists.get(6).add(1);
        adjLists.get(6).add(2);
        adjLists.get(6).add(3);
        adjLists.get(6).add(9);
        adjLists.get(6).add(10);
        adjLists.get(6).add(11);

        //adjacency list for vertex 7
        adjLists.get(7).add(2);
        adjLists.get(7).add(3);
        adjLists.get(7).add(10);
        adjLists.get(7).add(11);

        //adjacency list for vertex 8
        adjLists.get(8).add(4);
        adjLists.get(8).add(5);
        adjLists.get(8).add(12);
        adjLists.get(8).add(13);

        //adjacency list for vertex 9
        adjLists.get(9).add(4);
        adjLists.get(9).add(5);
        adjLists.get(9).add(6);
        adjLists.get(9).add(12);
        adjLists.get(9).add(13);
        adjLists.get(9).add(14);

        //adjacency list for vertex 10
        adjLists.get(10).add(5);
        adjLists.get(10).add(6);
        adjLists.get(10).add(7);
        adjLists.get(10).add(13);
        adjLists.get(10).add(14);
        adjLists.get(10).add(15);

        //adjacency list for vertex 11
        adjLists.get(11).add(6);
        adjLists.get(11).add(7);
        adjLists.get(11).add(14);
        adjLists.get(11).add(15);

        //adjacency list for vertex 12
        adjLists.get(12).add(8);
        adjLists.get(12).add(9);


        //adjacency list for vertex 13
        adjLists.get(13).add(8);
        adjLists.get(13).add(9);
        adjLists.get(13).add(10);

        //adjacency list for vertex 14
        adjLists.get(14).add(9);
        adjLists.get(14).add(10);
        adjLists.get(14).add(11);

        //adjacency list for vertex 15
        adjLists.get(15).add(10);
        adjLists.get(15).add(11);



        /*
        *
        * Matrix 4x4 - END
        *
        * */

        return adjLists;
    }

    private static ArrayList<ArrayList<Integer>> getMatrix4x4NoVertical(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
        final int n = SOLUTION_NUMBER;

        // Add an empty adjacency list for each vertex
        for (int v = 0; v < n; v++) {
            adjLists.add(new ArrayList<Integer>());
        }



        /*
          *
          * VERTICAL EDGES: 60
          *
          * Matrix 4x4 - START
          *
          *
          * */

        //adjacency list for vertex 0
        adjLists.get(0).add(1);
        adjLists.get(0).add(5);

        //adjacency list for vertex 1
        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(4);
        adjLists.get(1).add(6);

        //adjacency list for vertex 2
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(2).add(5);
        adjLists.get(2).add(7);

        //adjacency list for vertex 3
        adjLists.get(3).add(2);
        adjLists.get(3).add(6);

        //adjacency list for vertex 4
        adjLists.get(4).add(1);
        adjLists.get(4).add(5);
        adjLists.get(4).add(9);

        //adjacency list for vertex 5
        adjLists.get(5).add(0);
        adjLists.get(5).add(2);
        adjLists.get(5).add(4);
        adjLists.get(5).add(6);
        adjLists.get(5).add(8);
        adjLists.get(5).add(10);

        //adjacency list for vertex 6
        adjLists.get(6).add(1);
        adjLists.get(6).add(3);
        adjLists.get(6).add(5);
        adjLists.get(6).add(7);
        adjLists.get(6).add(9);
        adjLists.get(6).add(11);

        //adjacency list for vertex 7
        adjLists.get(7).add(2);
        adjLists.get(7).add(6);
        adjLists.get(7).add(10);

        //adjacency list for vertex 8
        adjLists.get(8).add(5);
        adjLists.get(8).add(9);
        adjLists.get(8).add(13);

        //adjacency list for vertex 9
        adjLists.get(9).add(4);
        adjLists.get(9).add(6);
        adjLists.get(9).add(8);
        adjLists.get(9).add(10);
        adjLists.get(9).add(12);
        adjLists.get(9).add(14);

        //adjacency list for vertex 10
        adjLists.get(10).add(5);
        adjLists.get(10).add(7);
        adjLists.get(10).add(9);
        adjLists.get(10).add(11);
        adjLists.get(10).add(13);
        adjLists.get(10).add(15);

        //adjacency list for vertex 11
        adjLists.get(11).add(6);
        adjLists.get(11).add(10);
        adjLists.get(11).add(14);

        //adjacency list for vertex 12
        adjLists.get(12).add(9);
        adjLists.get(12).add(13);

        //adjacency list for vertex 13
        adjLists.get(13).add(8);
        adjLists.get(13).add(10);
        adjLists.get(13).add(12);
        adjLists.get(13).add(14);

        //adjacency list for vertex 14
        adjLists.get(14).add(9);
        adjLists.get(14).add(11);
        adjLists.get(14).add(13);
        adjLists.get(14).add(15);

        //adjacency list for vertex 15
        adjLists.get(15).add(10);
        adjLists.get(15).add(14);


        /*
        *
        * Matrix 4x4 - END
        *
        * */

        return adjLists;
    }

    public static void getStartEndPointStatistics2(){

        ArrayList<ArrayList<Integer>> list = getMatrix4x4All();

        ArrayList<String> results = new ArrayList<>();
        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {
            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if(i!=j)  {
                    dfs(list, i, j);
                }
            }
        }

        ArrayList<String> start = new ArrayList<>();
        ArrayList<String> end = new ArrayList<>();

        for (int k = 0; k < SOLUTION_NUMBER; k++) {
            for (int b=0; b<lines.size();b++){
                if(lines.get(b).startsWith("[["+k)){
                    start.add(lines.get(b));
                }
                else if(lines.get(b).endsWith(k+"]]")){
                    end.add(lines.get(b));
                }
            }

            results.add("NODE: "+k+" START: "+start.size()+" END: "+end.size());
            start.clear();
            end.clear();

        }

        int sizeS = start.size();
        int sizeE = end.size();

    }

    public static void getStartEndPointStatisticsExcluding1Node(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists;
        ArrayList<String> resultsTmp = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();

        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {

            Integer tmpIndexI = i;
            Integer tmpIndexJ=null;

            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if(i!=j)  {
                    tmpIndexJ=j;

                    for (int m = 0; m<SOLUTION_NUMBER;m++) {

                      if(m!= tmpIndexI && m != tmpIndexJ){
                          adjLists = getMatrix4x4All();
                          //adjLists = getMatric4x4NoDiagonal();
                          //adjLists = getMatrix4x4NoOrizontal();
                          //adjLists = getMatrix4x4NoVertical();

                          clearNode(adjLists,m);

                          dfs(adjLists, tmpIndexI, tmpIndexJ);

                                results.add("FROM "+tmpIndexI+" TO "+tmpIndexJ+" NOT "+m+" SOL "+lines.size());

                                File file = new File("/Users/salvotosco/Desktop/SOL/TMP-start-end-not-1/S-"+tmpIndexI+"_E-"+tmpIndexJ+"_NOT-"+m);

                                results.add("FROM "+tmpIndexI+" TO "+tmpIndexJ+" NOT "+m+" SOL "+lines.size());

                                try {

                                    if(lines.size() > 0) {FileUtils.writeLines(file, lines);}


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                lines.clear();

                      }

                    }
                }
            }
        }

        File file = new File("/Users/salvotosco/Desktop/SOL/TMP-start-end-not-1/summary");

        try {
            FileUtils.writeLines(file, results);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getStartEndPointStatisticsExcluding1NodeNoIntersection(){

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists;
        ArrayList<String> resultsNoIntersection = new ArrayList<>();

        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {

            Integer tmpIndexI = i;
            Integer tmpIndexJ=null;

            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if(i!=j)  {
                    tmpIndexJ=j;

                    for (int m = 0; m<SOLUTION_NUMBER;m++) {

                        if(m!= tmpIndexI && m != tmpIndexJ){
                            //adjLists = getMatrix4x4All();
                            //adjLists = getMatric4x4NoDiagonal();
                            //adjLists = getMatrix4x4NoOrizontal();
                            adjLists = getMatrix4x4NoVertical();

                            clearNode(adjLists,m);
                            dfs(adjLists, tmpIndexI, tmpIndexJ);

                            File file = new File("/Users/salvotosco/Desktop/SOL/NO-VER-NO-INTER-start-end-not-1/S-"+tmpIndexI+"_E-"+tmpIndexJ+"_NOT-"+m);

                            ArrayList<String> linesNoIntersection = new ArrayList<>();
                            int count=0;

                            for(int p = 0; p < lines.size();p++){

                                String tmpStr = lines.get(p);

                                Solution tmpSol = new Solution(tmpStr);

                                if(!isIntersection(tmpSol)){
                                    linesNoIntersection.add(tmpStr);
                                    count++;
                                }
                            }

                            resultsNoIntersection.add("FROM "+tmpIndexI+" TO "+tmpIndexJ+" NOT "+m+" SOL NO-INTER "+count);

                            try {

                                if(linesNoIntersection.size() > 0) {
                                    FileUtils.writeLines(file, linesNoIntersection);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            lines.clear();
                            linesNoIntersection.clear();

                        }

                    }
                }
            }
        }

        File file = new File("/Users/salvotosco/Desktop/SOL/NO-VER-NO-INTER-start-end-not-1/summary");

        try {
            FileUtils.writeLines(file, resultsNoIntersection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static  void  testSub_1_Solution() {

        ArrayList<ArrayList<Integer>> matrixTmp=null;

        for (int k = 0; k< SOLUTION_NUMBER;k++) {

            ArrayList<ArrayList<Integer>> matrixDefault = getMatrix4x4All();
            matrixTmp = clearNode(matrixDefault, k);

            for (int i = 0; i < SOLUTION_NUMBER; i++) {
                for (int j = 0; j < SOLUTION_NUMBER; j++) {
                    if (i != j && i!=k) {
                        dfs(matrixTmp, i, j);
                    }
                }
            }

            int size = lines.size();
            lines.add("SIZE: " + size);
            System.out.println(size+" NODE "+k);

            File file = new File("/Users/salvotosco/Desktop/SOL/mode-not-1-solutions/solNo"+k);

            try {
                FileUtils.writeLines(file, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }

            lines.clear();
        }

    }

     public static ArrayList<ArrayList<Integer>> clearNode(ArrayList<ArrayList<Integer>> list, int node){
         list.get(node).clear();
         for (int i = 0 ; i<SOLUTION_NUMBER; i++){
             if(i!=node){
                 for (int j = 0 ; j<list.get(i).size(); j++){
                     if(list.get(i).get(j)==node){
                         list.get(i).remove(j);
                     }
                 }
             }
         }
         return list;

     }

     public  static void testDijkstra() throws PathNotFoundException {

         Integer from = 0,to=15;

         Vertex<Integer> vertexFrom = new Vertex<>(from);
         Vertex<Integer> vertexTo = new Vertex<>(to);

         List<Edge> edges = new ArrayList<Edge>();

         edges.add(new Edge(new Vertex(0),new Vertex(1),1));
         edges.add(new Edge(new Vertex(1),new Vertex(2),1));
         edges.add(new Edge(new Vertex(1),new Vertex(3),1));
         edges.add(new Edge(new Vertex(2),new Vertex(3),1));
         edges.add(new Edge(new Vertex(3),new Vertex(0),1));

         // Finding length of path (number of steps) between nodes:
         int pathLength = new DijkstraAlgorithm(new Graph(edges)).execute(vertexFrom).getShortestPathLength(new Vertex(0), new Vertex(3));

         // Finding weighted distance between nodes.
         int pathWeightedDistance = new DijkstraAlgorithm(new Graph(edges)).execute(vertexFrom).getDistance(vertexTo);
     }



     public static void createFileForStartEndCombination(){

         List<String> linesFile = null;

         String fileName = "/Users/salvotosco/Desktop/SOL/mode-solutions/all-solutions";
         //String fileName = "/Users/salvotosco/Desktop/SOL/mode-solutions/solNoDiagonal";
         //String fileName = "/Users/salvotosco/Desktop/SOL/mode-solutions/solNoVertical";
         //String fileName = "/Users/salvotosco/Desktop/SOL/mode-solutions/solNoOrizontal";

         try {
             linesFile = FileUtils.readLines(new File(fileName));
         } catch (IOException e) {
             e.printStackTrace();
         }

         ArrayList<String> resultSolIntersect = new ArrayList<>();

         for(int i =0;i < linesFile.size();i++){

             Solution tmpSol = new Solution(linesFile.get(i));

             if(!isIntersection(tmpSol)){
                 resultSolIntersect.add(tmpSol.getNodes().toString());
             }

         }

         ArrayList<String>  tmpListStart = new ArrayList<>();
         ArrayList<String>  tmpListEnd = new ArrayList<>();
         ArrayList<String>  tmpListSummary = new ArrayList<>();

         for (int y=0;y<SOLUTION_NUMBER;y++){

            for (int k = 0; k<resultSolIntersect.size();k++){

                String tmpSol = resultSolIntersect.get(k);

                 if(tmpSol.startsWith("["+y)){
                    tmpListStart.add(tmpSol);
                 }
                 else if(tmpSol.endsWith(y+"]")){
                     tmpListEnd.add(tmpSol);
                 }
             }

             File fileStart = new File("/Users/salvotosco/Desktop/SOL/NO-ALL-START-END/start"+y);
             File fileEnd = new File("/Users/salvotosco/Desktop/SOL/NO-ALL-START-END/end"+y);

             tmpListSummary.add(y+" - START - "+tmpListStart.size()+" END - "+tmpListEnd.size());

             try {

                 if(tmpListStart.size()>0)
                    FileUtils.writeLines(fileStart, tmpListStart);

                 if(tmpListEnd.size()>0)
                    FileUtils.writeLines(fileEnd, tmpListEnd);

             } catch (IOException e) {
                 e.printStackTrace();
             }

             tmpListEnd.clear();
             tmpListStart.clear();

         }


         File fileSummary = new File("/Users/salvotosco/Desktop/SOL/NO-ALL-START-END/summar");
         try {
             FileUtils.writeLines(fileSummary, tmpListSummary);
         } catch (IOException e) {
             e.printStackTrace();
         }

     }

    public static void getStartEndPointStatisticsExcluding2NodeNoIntersection() {

        // Create adjacency list representation
        ArrayList<ArrayList<Integer>> adjLists;
        ArrayList<String> resultsNoIntersection = new ArrayList<>();

        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < SOLUTION_NUMBER; i++) {
            nodes.add(i);
        }

        // Print vertices in the order in which they are visited by dfs()
        for (int i = 0; i < SOLUTION_NUMBER; i++) {

            Integer tmpIndexI = i;
            Integer tmpIndexJ = null;

            for (int j = 0; j < SOLUTION_NUMBER; j++) {
                if (i != j) {
                    tmpIndexJ = j;

                    for (int m = 0; m < SOLUTION_NUMBER; m++) {

                        if (m != tmpIndexI && m != tmpIndexJ) {

                            for (int x = 0; x < nodes.size(); x++) {
                                final Integer first = nodes.get(x);
                                for (int y = 0; y < nodes.size(); y++) {
                                    if (y == x) continue; // will properly increase y
                                    final Integer second = nodes.get(y);

                                    if (first != tmpIndexI && first != tmpIndexJ && second != tmpIndexI && second != tmpIndexJ) {

                                        //adjLists = getMatrix4x4All();
                                        //adjLists = getMatric4x4NoDiagonal();
                                        //adjLists = getMatrix4x4NoOrizontal();
                                        adjLists = getMatrix4x4NoVertical();

                                        clearNode(adjLists, first);
                                        clearNode(adjLists, second);

                                        dfs(adjLists, tmpIndexI, tmpIndexJ);

                                        File file = new File("/Users/salvotosco/Desktop/SOL/NO-VER-2NOT/S-" + tmpIndexI + "_E-" + tmpIndexJ + "_NOT-( " + first + " - " + second + " )");

                                        ArrayList<String> linesNoIntersection = new ArrayList<>();
                                        int count = 0;

                                        for (int p = 0; p < lines.size(); p++) {

                                            String tmpStr = lines.get(p);

                                            Solution tmpSol = new Solution(tmpStr);

                                            if (!isIntersection(tmpSol)) {
                                                linesNoIntersection.add(tmpStr);
                                                count++;
                                            }
                                        }

                                        resultsNoIntersection.add("FROM " + tmpIndexI + " TO " + tmpIndexJ + " NOT ( " + first + " - " + second + " ) SOL NO-INTER " + count);

                                        try {

                                            if (linesNoIntersection.size() > 0) {
                                                FileUtils.writeLines(file, linesNoIntersection);
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        lines.clear();
                                        linesNoIntersection.clear();


                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        File file = new File("/Users/salvotosco/Desktop/SOL/START-END/summary");

        try {
            FileUtils.writeLines(file, resultsNoIntersection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testStartEndNotIntersections(){

        ArrayList<ArrayList<Integer>> adjLists;
        ArrayList<String> results = new ArrayList<>();

        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < SOLUTION_NUMBER; i++) {
            nodes.add(i);
        }
            for(int x = 0; x < nodes.size(); x++) {
                final Integer first = nodes.get(x);
                for (int y = 0; y < nodes.size(); y++) {
                    if (y == x) continue; // will properly increase y
                    final Integer second = nodes.get(y);

                    //adjLists = getMatrix4x4All();
                    //adjLists = getMatric4x4NoDiagonal();
                    //adjLists = getMatrix4x4NoOrizontal();
                    adjLists = getMatrix4x4NoVertical();

                    dfs(adjLists, first, second);

                    File file = new File("/Users/salvotosco/Desktop/SOL/START-END-NO-VER/START- "+first+" - END -"+second);

                    ArrayList<String> linesNoIntersection = new ArrayList<>();

                    int count = 0;

                    for (int p = 0; p < lines.size(); p++) {
                        String tmpStr = lines.get(p);
                        Solution tmpSol = new Solution(tmpStr);

                        if (!isIntersection(tmpSol)) {

                            linesNoIntersection.add(tmpStr);
                            count++;
                        }
                    }

                    if(count > 0)
                        results.add("FROM " + first + " TO " + second + " SOL: " + count);

                    try {

                        if (linesNoIntersection.size() > 0) {
                            FileUtils.writeLines(file, linesNoIntersection);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    lines.clear();
                    linesNoIntersection.clear();

                }
            }

        File file = new File("/Users/salvotosco/Desktop/SOL/START-END-NO-VER/summary");

        try {
            FileUtils.writeLines(file, results);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

