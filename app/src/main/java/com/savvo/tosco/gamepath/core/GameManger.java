package com.savvo.tosco.gamepath.core;

import android.app.Activity;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.savvo.tosco.gamepath.beans.*;

/**
 * Created by salvotosco on 22/06/17.
 */


/**
 *
 * Manages and validates solutions for a specific mode
 *
 */
public class GameManger implements GameManagerInterface {


    private static GameManger _instance;

    private GameManger(Activity activity) {

        this.modeCount=0;
        this.isConcatenateMode=false;
        this.isIntersectionMode=false;
        this.isTimerMode=false;
        this.isBombMode=false;
        this.solutionCount=0;
        edgesSet = new HashMap<>();
        solutionsFound = new HashMap<Integer,Solution>();
        stackSolutions = new LinkedList<Solution>();
        fixedStart=null;
        fixedEnd=null;
        fixedNot= new ArrayList<>();
        stackBombs = new LinkedList<Integer>();
        lastBomb=-1;
        ActivityUtilis.initGameProperties(activity,this);
    }


    public static GameManger getInstance(Activity activity)
    {
        if (_instance == null)
        {
            _instance = new GameManger(activity);
        }
        return _instance;
    }

    private static int modeCount;
    private int solutionCount,lastBomb;
    private HashMap<Integer,Solution> solutionsFound;
    private HashMap<Integer,ModeSolutionContainer> edgesSet;
    private LinkedList<Solution> stackSolutions;
    private LinkedList<Integer> stackBombs;
    private boolean isIntersectionMode,isConcatenateMode,isTimerMode,isBombMode;
    private Integer fixedStart, fixedEnd;
    private ArrayList<Integer> fixedNot;



    /**
     *
     *
     * Checks if solution has right edges for selected mode.
     *
     * @param solution
     * @param mode
     * @return
     */
    private boolean isSolutionValidForEdgeSet(Solution solution, int mode){
        Pair<Integer,Integer> edge;
        for (int i = 0; i<solution.getSolutionSize()-1;i++){
            edge = Pair.of(solution.getNodeByIndex(i),solution.getNodeByIndex(i+1));
            if(!edgesSet.get(mode).getEdgesSet().contains(edge)){
                return false;
            }
        }
        return true;
    }


    /**
     *
     * Return 'OK' constant if solution is valid, error message code if not valid.
     *
     *
     * @param solution
     * @param mode
     * @return
     */
    public String isValidSolution(Solution solution,int mode){

        if(!checkSolutionSize(solution)){
            return ERROR_SIZE;
        }

        if(SolutionUtils.duplicates(solution.getNodes())) {
            return ERROR_DUPLICATE;
        }

        //check start point if checked
        if (fixedStart != null && solution.getNodeByIndex(0) != fixedStart)
            return ERROR_START;

        //check end point if checked
        if (fixedEnd != null && solution.getNodeByIndex(solution.getNodes().size() - 1) != fixedEnd)
            return ERROR_END;

        //check not points if checked
        if (fixedNot != null && fixedNot.size() > 0) {
            for (int i = 0; i < solution.getNodes().size(); i++) {
                for (int j = 0; j < fixedNot.size(); j++) {
                    if (solution.getNodeByIndex(i) == fixedNot.get(j))
                        return ERROR_NOT;
                }
            }
        }
        //check if solution is already found
        if(!solutionsFound.containsValue(solution)) {
            // checks if solutions has right edges for selected mode
            if (!isSolutionValidForEdgeSet(solution,mode))
                return ERROR_GENERIC;
        }
        else
            return ERROR_FOUND;

        //check intersection mode enabled
        if (this.isIntersectionMode) {
            if (isIntersection(solution))
                return ERROR_INTERSECT;
        }

        //check concatenate mode enabled
        if (this.isConcatenateMode && solutionCount > 0) {
            if (!isConcatenateSolutions(solution))
                return ERROR_CONCATENATE;
        }

        //check time mode enabled
        if(this.isTimerMode){
        }

        //save solution
        solutionsFound.put(solutionCount, solution);
        solutionCount++;
        stackSolutions.push(solution);

        return GameManger.OK;

        }



    /**
     *
     * Returns false if size is wrong, true otherwise
     *
      * @param solution
     * @return
     */

    public boolean checkSolutionSize(Solution solution) {

        if(solution.getSolutionSize() != MAX_SOL_SIZE-fixedNot.size())
            return false;

        return true;
    }

        /**
         *
         * Reads edges file each line is like: 1-2-3234 (node1-node2-edgeScore)
         *
         * @param lines
         * @throws IOException
         */
    public void addEdgesSet(ArrayList<String> lines,int total) throws IOException {

        ModeSolutionContainer tmoMode = new ModeSolutionContainer();
        ArrayList<Pair<Integer,Integer>> tmpSol2 = new ArrayList<>();
        ArrayList<String> tmpSolExtras = new ArrayList<>();

        Integer a,b;
        String extraInfo;

        for (int i=0;i < lines.size(); i++) {
            a = Integer.parseInt(lines.get(i).split("-")[0]);
            b = Integer.parseInt(lines.get(i).split("-")[1]);
            extraInfo = lines.get(i);
            tmpSol2.add(Pair.of(a,b));
            tmpSolExtras.add(extraInfo);
        }

        tmoMode.setEdgesSet(tmpSol2);
        tmoMode.setEdgesSetExtra(tmpSolExtras);
        tmoMode.setTotlSolutions(total );

        edgesSet.put(modeCount,tmoMode);
        modeCount++;

    }

    /**
     *
     *
     * Return solution validation message
     *
     *
     * @param code
     * @return
     */
    public String manageMessageSolutionValidation(String code){

        String outputMessage="";

        switch (code){
            case GameManger.ERROR_FOUND:
                outputMessage="PATTERN ALREADY FOUND!";
                break;

            case GameManger.ERROR_GENERIC:
                outputMessage = "WRONG PATTERN!";
                break;

            case GameManger.ERROR_CONCATENATE:
                outputMessage="NOT VALID FOR CONCATENATE MODE";
                break;

            case GameManger.ERROR_INTERSECT:
                outputMessage="NOT VALID FOR INTERSECTION MODE";
                break;

            case GameManger.ERROR_START:
                outputMessage="START NODE MUST BE: "+(getFixedStart()+1);
                break;

            case GameManger.ERROR_END:
                outputMessage="END NODE MUST BE: "+(getFixedEnd()+1);
                break;

            case GameManger.ERROR_NOT:
                //TODO consider all point
                outputMessage="SOLUTION CAN'T CONTAINS "+(getFixedNot().get(0)+1);
                break;

            case GameManger.ERROR_SIZE:
                outputMessage="SOLUTION SIZE WRONG";
                break;

            case GameManger.ERROR_DUPLICATE:
                outputMessage="SOLUTION CONTAINS DUPLICATES";
                break;

        }

        return outputMessage;

    }

    public int getModeCount() {
        return modeCount;
    }

    public boolean isFoundSolution(Solution solution){
        return solutionsFound.containsValue(solution);
    }

    public int getSolutionScore(Solution s,int mode){

        int result=0;
        for (int i=0;i<s.getNodes().size()-1;i++){
            result+=getEdgeScore(s.getNodeByIndex(i),s.getNodeByIndex(i+1),s,mode);
        }

        return result;
    }

    public int getEdgeScore(int a, int b,Solution s, int mode){

        double tmpRes=0;
        Pair<Integer,Integer> p = Pair.of(a,b);

        for (int i =0;i < edgesSet.get(mode).getEdgesSet().size();i++){
            if(edgesSet.get(mode).getEdgesSet().get(i).equals(p)){

                //check if exists edge score
                if(edgesSet.get(mode).getEdgesSetExtra().get(i).split("-").length > 2)
                {
                    tmpRes += Integer.parseInt(edgesSet.get(mode).getEdgesSetExtra().get(i).split("-")[2]);
                    break;
                }
                else {
                    tmpRes+=1;
                }
            }
        }


        int numVer=SolutionUtils.getVerticalCount(s);
        int numOri=SolutionUtils.getOrizontalCount(s);
        int numDia=SolutionUtils.getDiagonalCount(s);


        //no orizonatal
        if(numOri==0){

        }
        //no vertical
        else if(numVer==0){

        }
        //no diagonal
        else if(numDia == 0){

        }

        int tot = edgesSet.get(mode).getTotlSolutions();
        tmpRes/=tot;


        int result=0;

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

    /**
     *
     * Returns true if solution has edges intersection, false otherwise.
     *
     *
     * @param sol
     * @return
     */
    public boolean isIntersection(Solution sol) {

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


    public ArrayList<Pair<Integer,Integer>> getEdgesSetList(int set){
        return edgesSet.get(set).getEdgesSet();
    }


    public boolean isConcatenateSolutions(Solution sol1){

        //TODO all - concatenate, intersection e time mode active
        Solution lastSol = stackSolutions.getFirst();
        if(lastSol.getNodeByIndex(sol1.getNodes().size()-1)== sol1.getNodeByIndex(0))
            return true;
        return false;
    }

    public void setIntersectionMode(boolean intersectionMode) {
        this.isIntersectionMode = intersectionMode;
    }

    public void setConcatenateMode(boolean concatenateMode) {
        this.isConcatenateMode = concatenateMode;
    }

    public boolean isIntersectionMode() {
        return isIntersectionMode;
    }

    public boolean isConcatenateMode() {
        return isConcatenateMode;
    }

    public boolean isTimerMode() {
        return isTimerMode;
    }

    public void setTimerMode(boolean timerMode) {
        isTimerMode = timerMode;
    }

    public void setFixedStart(Integer fixedStart) {
        this.fixedStart = fixedStart;
    }

    public void setFixedEnd(Integer fixedEnd) {
        this.fixedEnd = fixedEnd;
    }

    public Integer getFixedStart() {
        return fixedStart;
    }

    public Integer getFixedEnd() {
        return fixedEnd;
    }

    public ArrayList<Integer> getFixedNot() {
        return fixedNot;
    }

    public void addFixedNot(Integer fixedNot) {
        this.fixedNot.add(fixedNot);
    }

    public void setFixedNot(ArrayList<Integer> fixedNot) {
        this.fixedNot = fixedNot;
    }

    public void cleanManagerData(){
        this.solutionsFound.clear();
        this.stackSolutions.clear();
        this.fixedNot.clear();
        this.stackBombs.clear();
        this.fixedStart=null;
        this.fixedEnd=null;
        this.setBombMode(false);
        this.setConcatenateMode(false);
        this.setIntersectionMode(false);
        this.setTimerMode(false);

    }

    public boolean isBombMode() {
        return isBombMode;
    }

    public void setBombMode(boolean bombMode) {
        isBombMode = bombMode;
    }

    public LinkedList<Integer> getStackBombs() {
        return stackBombs;
    }

    public void setStackBombs(LinkedList<Integer> stackBombs) {
        this.stackBombs = stackBombs;
    }

    /**
     *
     * Returns random number between 1 and 16 excluding currentCell
     *
     * @param currentCell
     * @return
     */
    public int getBombNode(int currentCell){

        int ran = RandomUtils.nextInt(1,17);

        if(lastBomb > 0){

            while (ran == lastBomb || currentCell == ran){
                ran = RandomUtils.nextInt(1,17);
            }
        }
        else
            while(currentCell == ran)
                ran = RandomUtils.nextInt(1,17);


        lastBomb = ran;

        return lastBomb;
    }

}
