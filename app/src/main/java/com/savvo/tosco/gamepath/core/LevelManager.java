package com.savvo.tosco.gamepath.core;

import android.app.Activity;
import java.util.ArrayList;

import com.savvo.tosco.gamepath.beans.Level;


/**
 * Created by salvotosco on 25/07/17.
 */

public class LevelManager {

    public static final int NO_LEVEL_GAME=0;

    private static LevelManager _instance;
    private ArrayList<Level> gameLevels;
    private int currentLevel;

    private LevelManager(Activity activity) {
        this.gameLevels = new ArrayList<>();
        this.currentLevel=1;
        this.gameLevels= ActivityUtilis.loadLevels(activity);
    }

    public static LevelManager getInstance(Activity activity)
    {
        if (_instance == null)
        {
            _instance = new LevelManager(activity);
        }
        return _instance;
    }

    public Level getCurrentLevel(){
        return gameLevels.get(currentLevel);
    }

    public int getFirstLevelId(){
        //0 is no level game
        return gameLevels.get(1).getId();
    }

    public ArrayList<Level> getGameLevels() {
        return gameLevels;
    }

    /**
     *
     * Update current level number + 1
     *
     */
    public void nextLevel(){
        currentLevel++;
    }

    public int getCurrentLevelId() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     *
     * Returns true if level is done, false otherwise.
     *
     * @param idLevel
     * @param score
     * @return
     */
    public boolean checkResultsForLevel(int idLevel,int score,int solutionFoundCount){

        if(score >= gameLevels.get(idLevel).getScoreTarget() || solutionFoundCount >= gameLevels.get(idLevel).getFoundSolTarget())
           return true;

        return false;
    }

    /**
     *
     * Returns true if current level is done, false otherwise.
     *
     * @param score
     * @return
     */
    public boolean checkResultsForCurrentLevel(int score,int solutionFoundCount){

        if(getCurrentLevelId() != NO_LEVEL_GAME)
            if(score >= gameLevels.get(currentLevel).getScoreTarget() || solutionFoundCount >= gameLevels.get(currentLevel).getFoundSolTarget())
                return true;

        return false;
    }


    public void clearLevelManager(){
        this.currentLevel=1;
    }

}
