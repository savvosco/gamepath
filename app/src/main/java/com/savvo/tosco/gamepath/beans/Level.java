package com.savvo.tosco.gamepath.beans;

import java.util.ArrayList;

/**
 * Created by salvotosco on 25/07/17.
 */

public class Level extends  Object {

    public static String CONCATENATE_MODE="concatenate";
    public static String INTERSECTION_MODE="intersection";
    public static String BOMB_MODE ="bomb";

    private int id,scoreTarget, secondsTimer,lifeCount,foundSolTarget,startNode,endNode,set;
    private ArrayList<Integer> notNodes;
    private ArrayList<String> modes;
    private String description,name;


    private int bonusCount;




    public Level() {

        this.id=-1;
        this.set=-1;
        this.description="";
        this.scoreTarget = 0;
        this.foundSolTarget=0;
        this.secondsTimer = 0;
        this.lifeCount = 0;
        this.modes = new ArrayList<>();
        this.name="";
        this.startNode=-1;
        this.endNode=-1;
        this.notNodes = new ArrayList<>();
        this.bonusCount=0;

    }

    public Level(int id, String name, int set, int scoreTarget, int secondsTimer, int lifeCount, ArrayList<String> modes, int foundSolTarget, String description, int startNode, int endNode, int notNode) {
        this.id=id;
        this.set=set;
        this.description=description;
        this.scoreTarget = scoreTarget;
        this.foundSolTarget=foundSolTarget;
        this.secondsTimer = secondsTimer;
        this.lifeCount = lifeCount;
        this.modes = modes;
        this.name=name;

        if(startNode > -1)
            this.startNode=startNode;
        else
            this.startNode=-1;

        if(endNode > -1)
            this.endNode=endNode;
        else
            this.endNode=-1;

        this.notNodes = new ArrayList<>();

        if(notNode != -1)
            this.notNodes.add(notNode);

    }


    public int getScoreTarget() {
        return scoreTarget;
    }

    public Level setScoreTarget(int scoreTarget) {
        this.scoreTarget = scoreTarget;
        return this;
    }

    public int getSecondsTimer() {
        return secondsTimer;
    }

    public Level setSecondsTimer(int secondsTimer) {
        this.secondsTimer = secondsTimer;
        return this;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public Level setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
        return this;
    }

    public ArrayList<String> getModes() {
        return modes;
    }

    public Level setModes(ArrayList<String> modes) {
        this.modes = modes;
        return this;
    }

    public int getId() {
        return id;
    }

    public Level setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Level setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSet() {
        return set;
    }

    public Level setSet(int set) {
        this.set = set;
        return this;
    }

    public int getFoundSolTarget() {
        return foundSolTarget;
    }

    public Level setFoundSolTarget(int foundSolTarget) {
        this.foundSolTarget = foundSolTarget;
        return this;
    }

    public int getStartNode() {
        return startNode;
    }

    public Level setStartNode(int startNode) {
        this.startNode = startNode;
        return this;
    }

    public int getEndNode() {
        return endNode;
    }

    public Level setEndNode(int endNode) {
        this.endNode = endNode;
        return this;
    }

    public ArrayList<Integer> getNotNodes() {
        return notNodes;
    }

    public Level addNotNodes(int node) {
        this.notNodes.add(node);
        return this;
    }

    public String getName() {
        return name;
    }

    public Level setName(String name) {
        this.name = name;
        return this;
    }

    public int getBonusCount() {
        return bonusCount;
    }

    public Level setBonusCount(int bonusCount) {
        this.bonusCount = bonusCount;
        return this;
    }
}
