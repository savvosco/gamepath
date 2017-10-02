package com.savvo.tosco.gamepath.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;
import com.eftimoff.patternview.cells.Cell;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.savvo.tosco.gamepath.core.ActivityUtilis;
import com.savvo.tosco.gamepath.core.GameManger;
import com.savvo.tosco.gamepath.core.LevelManager;
import com.savvo.tosco.gamepath.core.SolutionUtils;
import com.savvo.tosco.gamepath.utils.*;

import com.savvo.tosco.gamepath.beans.Level;
import com.savvo.tosco.gamepath.beans.Solution;
import com.savvo.tosco.gamepath.R;



public class MainActivity extends AppCompatActivity implements PatternView.OnPatternCellAddedListener, PatternView.OnPatternClearedListener, PatternView.OnPatternDetectedListener,PatternView.OnPatternStartListener {
    
    private PatternView patternView;
    private GameManger manager;
    private LevelManager levelManager;
    private String patternString;

    private int score,selectedMode,countValidPath,lifeCount;
    private TextView timerTextView,scoreText,countLifesLeft,countResolvedSolution;
    private long millisInFut;

    private CountDownTimerWrapper timer;
    private long timeStart;

    private long timerStartPattern;

    private int time,countLevelBonue,bonusNode;
    private boolean isTimerOver,isPatterneStarted,isBonusActve;

    private ImageView imageViewBonus,imageViewBomb;

    public Handler timerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time=msg.what;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = GameManger.getInstance(this);
        levelManager = LevelManager.getInstance(this);

        //clean context before init
        manager.cleanManagerData();

        initManager(manager,getIntent().getExtras(),levelManager);

        millisInFut=levelManager.getCurrentLevel().getSecondsTimer()*1000;
        lifeCount=levelManager.getCurrentLevel().getLifeCount();

        countValidPath=0;
        score=0;
        isTimerOver=false;

        // INIT GUI ELEMENTS
        scoreText = (TextView) findViewById(R.id.scoreText);
        //countResolvedSolution = (TextView) findViewById(R.id.countSolutionText);
        countLifesLeft = (TextView) findViewById(R.id.lifeCount);
        countLifesLeft.setText(""+lifeCount);

        countResolvedSolution = (TextView) findViewById(R.id.countSolutionText);

        //initPatternView(this.selectedMode,levelManager.getCurrentLevelId());
        initPatternView();
        Toast.makeText(getApplicationContext(), levelManager.getCurrentLevel().getName(), Toast.LENGTH_SHORT).show();


        // TIMER START
        if(millisInFut > 0) {
            timerTextView = (TextView) findViewById(R.id.timerText);
            timer = new CountDownTimerWrapper(millisInFut,1000,patternView,this,timerHandler);
            timeStart = System.currentTimeMillis();
            timer.start();
        }
        // TIMER END

        //BOMB
        this.imageViewBomb = (ImageView)findViewById(R.id.iconBomb);
        this.imageViewBomb.setImageResource(R.drawable.bomb_red);

        //BONUS
        this.imageViewBonus = (ImageView)findViewById(R.id.iconBonus);
        this.imageViewBonus.setImageResource(R.drawable.bonus);

        countLevelBonue = levelManager.getCurrentLevel().getBonusCount();
    }



    @Override
    public void onPause() {
        super.onPause();

        if(manager.isTimerMode())
            timer.cancel();
    }


    /**
     *
     * If 'level' parameter is specified, init game settings from LevelManager, otherwise init settings from others extras parameters.
     *
     *
     * @param manager
     * @param extras
     * @param levelManager
     */
    private void initManager(GameManger manager, Bundle extras, LevelManager levelManager) {

        if (extras != null) {

            manager.cleanManagerData();

            //INIT GameManager getting level configuration from LevelManager
            if(!"".equals(extras.getString("level")) && null != extras.getString("level")){

                Integer levelId = Integer.parseInt(extras.getString("level"));
                Level level = levelManager.getGameLevels().get(levelId);
                levelManager.setCurrentLevel(levelId);

                this.selectedMode = levelManager.getCurrentLevel().getSet();

                //INIT MODES
                for(int i=0;i < level.getModes().size();i++){
                    if(level.getModes().get(i).equals(Level.CONCATENATE_MODE))
                    {
                        manager.setConcatenateMode(true);
                    }
                    else if(level.getModes().get(i).equals(Level.INTERSECTION_MODE))
                    {
                        manager.setIntersectionMode(true);
                    }
                    else if(level.getModes().get(i).equals(Level.BOMB_MODE))
                    {
                        manager.setBombMode(true);
                    }

                }

                if(level.getStartNode()!= -1){
                    manager.setFixedStart(level.getStartNode());
                }

                if(level.getEndNode()!= -1){
                    manager.setFixedEnd(level.getEndNode());
                }

                    //if notMode is active ignore not node
                if(!manager.isBombMode() && level.getNotNodes().size() > 0 && level.getNotNodes().get(0) != -1){
                        //manager.setFixedNot(level.getNotNodes());
                        manager.addFixedNot(level.getNotNodes().get(0));

                }



                if(level.getSecondsTimer() > 0){
                    manager.setTimerMode(true);
                }

                if(level.getBonusCount() > 0){
                    //manager
                }

            }
            //INIT MANAGER FROM EXTRAS
            else
                {
                    this.selectedMode = extras.getInt("mode");

                    if(extras.getBoolean("concatenate"))
                        manager.setConcatenateMode(true);

                    if(extras.getBoolean("intersection"))
                        manager.setIntersectionMode(true);

                    if(extras.getBoolean("notMode"))
                        manager.setBombMode(true);

                    String fromNode = extras.getString("start");
                    String toNode = extras.getString("end");
                    String notNode = extras.getString("not");

                    if(null != fromNode &&!"".equals(fromNode)&& !"0".equals(fromNode)){
                        manager.setFixedStart(Integer.parseInt(fromNode)-1);
                    }

                    if(null != toNode && !"".equals(toNode) && !"0".equals(toNode)){
                        manager.setFixedEnd(Integer.parseInt(toNode)-1);
                    }

                    if(null != notNode && !"".equals(notNode) && !"0".equals(notNode)){

                        Integer node = Integer.parseInt(notNode)-1;

                        manager.addFixedNot(node);

                        imageViewBomb = (ImageView)findViewById(R.id.iconBomb);
                        imageViewBomb.setImageResource(R.drawable.bomb_red);
                        imageViewBomb.setVisibility(View.VISIBLE);

                        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(200, 200);
                        //layoutParams.setMargins(int left, int top, int right, int bottom);

                        Pair<Integer,Integer> pair = SolutionUtils.getCoordinateByPoint(node);

                        int marginLeft=20+(290*pair.getRight());
                        int marginTop=300+(260*pair.getLeft());
                        //layoutParams.setMargins(320, 1120, 1, 1);
                        layoutParams.setMargins(marginLeft, marginTop, 1, 1);
                        imageViewBomb.setLayoutParams(layoutParams);
                    }

                }
            }
    }

    /**
     *
     * Initializes pattern view.
     *
     */
    private void initPatternView()
    {
        //TODO get color from configuration
        initColorPatter();

        patternView.setOnPatternStartListener(this);
        patternView.setOnPatternCellAddedListener(this);
        patternView.setOnPatternDetectedListener(this);
        patternView.setOnPatternClearedListener(this);

        patternView.setOnClickListener(new PatternView.OnClickListener(){

            @Override
            public void onClick(View v) {
            }
        });

    }


    /**
     *
     * Sets patter view colors.
     *
     */
    private void initColorPatter() {

        //TODO manage colors for level or mode
        int randomInt = RandomUtils.nextInt(0,7);


        patternView = (PatternView) findViewById(R.id.patternView);
        patternView.setTactileFeedbackEnabled(false);

        patternView.setBackgroundColor(Color.BLACK);
        patternView.setPathColor(Color.WHITE);
        patternView.setDotColor(Color.WHITE);
        patternView.setCircleColor(Color.WHITE);

        /*
        switch (randomInt){
            case 0:

                patternView.setBackgroundColor(Color.BLACK);
                patternView.setPathColor(Color.LTGRAY);
                patternView.setDotColor(Color.RED);
                patternView.setCircleColor(Color.WHITE);

                break;
            case 1:

                patternView.setBackgroundColor(Color.BLACK);
                patternView.setPathColor(Color.RED);
                patternView.setDotColor(Color.WHITE);
                patternView.setCircleColor(Color.YELLOW);

                break;
            case 2:

                patternView.setBackgroundColor(Color.BLACK);
                patternView.setPathColor(Color.GREEN);
                patternView.setDotColor(Color.RED);
                patternView.setCircleColor(Color.BLUE);

                break;
            case 3:

                patternView.setBackgroundColor(Color.BLUE);
                patternView.setPathColor(Color.YELLOW);
                patternView.setDotColor(Color.RED);
                patternView.setCircleColor(Color.YELLOW);

                break;

            case 4:

                patternView.setBackgroundColor(Color.BLACK);
                patternView.setPathColor(Color.LTGRAY);
                patternView.setDotColor(Color.RED);
                patternView.setCircleColor(Color.RED);

                break;

            case 5:

                patternView.setBackgroundColor(Color.BLUE);
                patternView.setPathColor(Color.RED);
                patternView.setDotColor(Color.YELLOW);
                patternView.setCircleColor(Color.YELLOW);

                break;

            case 6:

                patternView.setBackgroundColor(Color.BLACK);
                patternView.setPathColor(Color.BLUE);
                patternView.setDotColor(Color.GREEN);
                patternView.setCircleColor(Color.GREEN);

                break;
            default:
                break;
        }
        */


    }

    /**
     *
     * Manages life count
     *
     * @param @{@link Solution}
     * @param m
     */
    private void lifeCountManagment(Solution s, GameManger m) {

        //if(m.checkSolutionSize(s)){
        if(s.getSolutionSize() > 3){
            lifeCount--;
            if(lifeCount==0) {
                ActivityUtilis.vibrate(500,getMainActivity());
                startResultsAcitivty();
            }
            else{
                countLifesLeft.setText(""+lifeCount);
            }
        }
    }

    public void startResultsAcitivty(){
            Intent intent=new Intent(MainActivity.this,ResultsActivity.class);

            if(levelManager.getCurrentLevelId() != LevelManager.NO_LEVEL_GAME)
                intent.putExtra("level", levelManager.getCurrentLevelId()+"");

            //TODO
            intent.putExtra("score", score);
            intent.putExtra("sol", countValidPath);
            intent.putExtra("mode", selectedMode);
            intent.putExtra("concatenate", manager.isConcatenateMode());
            intent.putExtra("intersect", manager.isIntersectionMode());
            intent.putExtra("timer", manager.isTimerMode());
            intent.putExtra("result-level",levelManager.checkResultsForCurrentLevel(score,countValidPath));

            manager.cleanManagerData();
            timer.cancel();

        startActivity(intent);
    }

    public TextView getTimerTextView() {
        return timerTextView;
    }

    public int getTime() {
        return time;
    }

    public boolean isTimerOver() {
        return isTimerOver;
    }

    public void setTimerOver(boolean timerOver) {
        isTimerOver = timerOver;
    }

    public boolean isPatterneStarted() {
        return isPatterneStarted;
    }

    public void setPatterneStarted(boolean patterneStarted) {
        isPatterneStarted = patterneStarted;
    }

    public MainActivity getMainActivity(){return this;}

    @Override
    public void onPatternCellAdded() {

        List<Cell> cellsList = patternView.getPattern();

        if(cellsList.size() == GameManger.MAX_SOL_SIZE){
            long time = (System.currentTimeMillis()+timeStart)/1000;
            //System.out.println(" ******* TIME - "+time+" - **********");
            //Toast.makeText(getApplicationContext(), "Time: "+time, Toast.LENGTH_SHORT).show();
        }

        if(isPatterneStarted) {

            int node = SolutionUtils.getPointByCoordinate(cellsList.get(cellsList.size() - 1).getRow(), cellsList.get(cellsList.size() - 1).getColumn());
            long time = (System.currentTimeMillis() - timerStartPattern)/1000;

            System.out.println(" ******* TIME - " + time + "#" + node + " - **********");


        }

        // START BOMB MANAGMENT
        if(manager.isBombMode() && isPatterneStarted()){

            Cell lastCell = cellsList.get(cellsList.size()-1);
            int nodeInserted = SolutionUtils.getPointByCoordinate(lastCell.getRow(),lastCell.getColumn());
            //int set = levelManager.getCurrentLevel().getSet();
            int pathLen;

            if(cellsList.size()>1)
                pathLen = SolutionUtils.getDijkstraMinimumPath(SolutionUtils.convertEdgeSetToListEdges(manager.getEdgesSetList(selectedMode),cellsList),(nodeInserted-1),manager.getFixedNot().get(0));
            else
                pathLen = SolutionUtils.getDijkstraMinimumPath(SolutionUtils.convertEdgeSetToListEdges(manager.getEdgesSetList(selectedMode), null),(nodeInserted-1),manager.getFixedNot().get(0));

            if((pathLen-1) == 0){

                patternView.clearPattern(500);
                patternView.clearFocus();
                patternView.clearAnimation();


                imageViewBomb = (ImageView)findViewById(R.id.iconBomb);
                imageViewBomb.setImageResource(R.drawable.bomb_red);
                imageViewBomb.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(200, 200);
                //layoutParams.setMargins(int left, int top, int right, int bottom);

                Pair<Integer,Integer> pair = SolutionUtils.getCoordinateByPoint(manager.getFixedNot().get(0));

                int marginLeft=20+(290*pair.getRight());
                int marginTop=300+(260*pair.getLeft());
                layoutParams.setMargins(marginLeft, marginTop, 1, 1);
                imageViewBomb.setLayoutParams(layoutParams);

                new CountDownTimer(1500, 500) { // 1.5 sec
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {imageViewBomb.setVisibility(View.INVISIBLE);}
                }.start();

                lifeCount--;
                countLifesLeft.setText(lifeCount+"");

                if(lifeCount==0) {
                    startResultsAcitivty();
                }

                ActivityUtilis.playBombSound(this);
                ActivityUtilis.vibrate(100,getMainActivity());

            }
            else if(pathLen > 0)
                //countResolvedSolution.setText("B="+(manager.getFixedNot().get(0)+1)+" D= "+(pathLen-1));
                countResolvedSolution.setText( "D= "+(pathLen-1));
            else {
                //countResolvedSolution.setText("B="+(manager.getFixedNot().get(0)+1)+" D=NO PAT");
                countResolvedSolution.setText( "D=WRONG PAT");
            }

        }

        // END BOMB MANAGMENT

        // START BONUS MANAGMENT
        if (cellsList.size() > 1 && cellsList.size()<10 && isPatterneStarted() && !isBonusActve && countLevelBonue > 0) {

                Random r = new Random();
                if(r.nextInt()%15==0){
                    startBonus(cellsList);
                }

        }

        if(isBonusActve){

            Cell lastCell = cellsList.get(cellsList.size()-1);
            int nodeInserted = SolutionUtils.getPointByCoordinate(lastCell.getRow(),lastCell.getColumn())-1;

            if(bonusNode == nodeInserted){
                ActivityUtilis.playBonusOkSound(this);
                imageViewBonus.setVisibility(View.INVISIBLE);
            }

        }
        // START BONUS MANAGMENT

    }

    @Override
    public void onPatternStart() {

        isPatterneStarted=true;
        isBonusActve = false;

        if(manager.isBombMode()){

            List<Cell> cellsList = patternView.getPattern();
            Cell lastCell = cellsList.get(cellsList.size()-1);
            int nodeInserted = SolutionUtils.getPointByCoordinate(lastCell.getRow(),lastCell.getColumn());

            int bomb = manager.getBombNode(nodeInserted);
            manager.getFixedNot().clear();
            manager.getFixedNot().add(bomb);

            int pathLen = SolutionUtils.getDijkstraMinimumPath(SolutionUtils.convertEdgeSetToListEdges(manager.getEdgesSetList(selectedMode),cellsList),(nodeInserted-1),manager.getFixedNot().get(0));

            //countResolvedSolution.setText("B="+(manager.getFixedNot().get(0)+1)+" D= "+(pathLen-1));
            countResolvedSolution.setText("D= "+(pathLen-1));

        }
    }

    @Override
    public void onPatternCleared() {
        isPatterneStarted=false;
    }

    @Override
    public void onPatternDetected() {

        patternString = patternView.getPatternString();

        if (patternString == null || "".equals(patternString)) {
            patternString = patternView.getPatternString();
            patternView.clearPattern();
            return;
        }

        Solution solution = new Solution(SolutionUtils.parseSolution(patternString));
        String resultSolutionValidation = manager.isValidSolution(solution,selectedMode);

        if(GameManger.OK.equals(resultSolutionValidation) )
        {
            countValidPath++;

            if(countValidPath < levelManager.getCurrentLevel().getFoundSolTarget())
                Toast.makeText(getApplicationContext(), "SOLUTION FOUND N° "+countValidPath, Toast.LENGTH_SHORT).show();

            ActivityUtilis.playSolutionFoundSound(this);

            //SCORE MANAGMENT
            Integer actualScore = Integer.parseInt(scoreText.getText()+"");
            actualScore+=manager.getSolutionScore(solution,selectedMode);
            scoreText.setText(actualScore+"");
            score= actualScore;

        }
        //not valid solution
        else {

            String message = manager.manageMessageSolutionValidation(resultSolutionValidation);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            ActivityUtilis.playErrorSound(this);
            ActivityUtilis.vibrate(100,getMainActivity());
            //manages game life
            lifeCountManagment(solution,manager);

        }
        patternView.clearPattern();
        patternView.enableInput();

        //manage end game

        if(isTimerOver || countValidPath == levelManager.getCurrentLevel().getFoundSolTarget()){
            startResultsAcitivty();
        }

    }

    private void startBonus(List<Cell> currentCellsList){

        isBonusActve = true;
        countLevelBonue--;

        ArrayList<Integer> listNode = new ArrayList<>();

        for (int i=0;i < currentCellsList.size();i++)
            listNode.add(SolutionUtils.getPointByCoordinate(currentCellsList.get(i).getRow(),currentCellsList.get(i).getColumn())-1);

        //choose bonus node
        bonusNode = RandomUtils.nextInt(0,16);

        while (listNode.contains(bonusNode)){
            bonusNode = RandomUtils.nextInt(0,16);
        }

        //imageViewBonus.setImageResource(R.drawable.bonus);
        imageViewBonus.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(200, 200);
        Pair<Integer,Integer> pair = SolutionUtils.getCoordinateByPoint(bonusNode);

        int marginLeft=20+(290*pair.getRight());
        int marginTop=300+(260*pair.getLeft());
        layoutParams.setMargins(marginLeft, marginTop, 1, 1);
        //layoutParams.setMargins(int left, int top, int right, int bottom);
        imageViewBonus.setLayoutParams(layoutParams);

        new CountDownTimerWrapperBonus(1000, 500,patternView,this,imageViewBonus).start();

    }

    public boolean isBonusActve() {
        return isBonusActve;
    }

    public void setBonusActve(boolean bonusActve) {
        isBonusActve = bonusActve;
    }
}