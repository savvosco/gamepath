package com.savvo.tosco.gamepath.core;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Vibrator;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.savvo.tosco.gamepath.core.GameManger;
import com.savvo.tosco.gamepath.beans.Level;
import com.savvo.tosco.gamepath.R;

/**
 * Created by salvotosco on 16/07/17.
 */

public class ActivityUtilis {

    /**
     *
     * Read solution-settings file to load game configurations.
     *
     * @param activity
     * @return
     */
    public static void initGameProperties(Activity activity, GameManger manager) {

        BufferedReader br = null;
        InputStream is = null;
        ArrayList<String> modesAll = new ArrayList<>();
        ArrayList<String> edgesAll = new ArrayList<>();
        int totalSolutionsAll=0;

        //reads all modes
        try {

        is = activity.getBaseContext().getAssets().open("solutions-settings");
        br = new BufferedReader(new InputStreamReader(is));
        String lineConf,tmpLine;

        while ((lineConf = br.readLine()) != null) {

            if(!lineConf.startsWith("#")) {
                if (lineConf.startsWith("total")) {
                    tmpLine = lineConf.split("total=")[1];
                    totalSolutionsAll = Integer.parseInt(tmpLine);
                } else if (lineConf.startsWith("mode")) {
                    tmpLine = lineConf.split("mode=")[1];
                    modesAll.add(tmpLine);
                } else if (lineConf.startsWith("edge")) {
                    tmpLine = lineConf.split("edge=")[1];
                    edgesAll.add(tmpLine);
                }
            }
        }

        manager.addEdgesSet(edgesAll,totalSolutionsAll);

            ArrayList<String> edgesTmp = new ArrayList<>();
            int tmpCount=0;

            for (int i = 0; i < modesAll.size(); i++) {

                is = activity.getBaseContext().getAssets().open(modesAll.get(i));
                br = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = br.readLine()) != null) {

                    if(!line.startsWith("#")) {
                        if (line.startsWith("total")) {
                            tmpLine = line.split("total=")[1];
                            tmpCount = Integer.parseInt(tmpLine);
                        } else if (line.startsWith("edge")) {
                            tmpLine = line.split("edge=")[1];
                            edgesTmp.add(tmpLine);
                        }
                    }
                }

                manager.addEdgesSet(edgesTmp,tmpCount);
                edgesTmp.clear();
                tmpCount=0;

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }//finally

    }


    /**
     *
     * Loads levels configuration from file levels-conf.xml
     *
     * @param activity
     * @return
     */
    public static ArrayList<Level> loadLevels(Activity activity){

        ArrayList<Level> levels = new ArrayList<>();

        try {
            InputStream is = activity.getAssets().open("levels-conf.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            //LOAD DEFAULT VALUES
            //String timerDefault = getValue("timer-default",element);
            //String lifeDefault = getValue("life-default",element);

            //LOAD LEVELS
            NodeList nList = doc.getElementsByTagName("level");

            //Level tmpLevel;
            Level tmpLevel;
            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    String description = getValue("description", element2);
                    String name = getValue("name", element2);
                    String set = getValue("set", element2);
                    String scoreTarget = getValue("score-target", element2);
                    String solutionTarget = getValue("solution-target", element2);
                    String timerTmp = getValue("timer", element2);
                    String lifesTmp = getValue("lifes", element2);
                    String id = getValue("id",element2);
                    String startNode = getValue("start",element2);
                    String endNode = getValue("end",element2);
                    String notNode = getValue("not",element2);
                    String bonus = getValue("bonus",element2);

                    //TODO caleidoscopic constructor

                    tmpLevel = new Level();

                    if(scoreTarget == null || "".equals(scoreTarget))
                        scoreTarget="1000";

                    if(solutionTarget == null || "".equals(solutionTarget))
                        solutionTarget="100";

                    if(timerTmp == null || "".equals(timerTmp))
                        timerTmp="0";

                    if(lifesTmp == null || "".equals(lifesTmp))
                        lifesTmp="10";

                    if(startNode == null || "".equals(startNode))
                        startNode="-1";

                    if(endNode == null || "".equals(endNode))
                        endNode="-1";

                    if(notNode == null || "".equals(notNode))
                        notNode="-1";

                    if(bonus == null || "".equals((bonus)))
                        bonus = "0";

                    ArrayList<String>  modes = getValues("mode",element2);

                    tmpLevel.setId(Integer.parseInt(id))
                            .setName(name)
                            .setSet(Integer.parseInt(set))
                            .setScoreTarget(Integer.parseInt(scoreTarget))
                            .setSecondsTimer(Integer.parseInt(timerTmp))
                            .setLifeCount(Integer.parseInt(lifesTmp))
                            .setModes(modes)
                            .setFoundSolTarget(Integer.parseInt(solutionTarget))
                            .setDescription(description)
                            .setStartNode(Integer.parseInt(startNode))
                            .setEndNode(Integer.parseInt(endNode))
                            .addNotNodes(Integer.parseInt(notNode))
                            .setBonusCount(Integer.parseInt(bonus));

                    levels.add(tmpLevel);
                }
            }

        } catch (Exception e) {e.printStackTrace();}

        return levels;

    }

    /**
     *
     * Returns tag text value if exists, empty string otherwise.
     *
     *
     * @param tag
     * @param element
     * @return
     */
    private static String getValue(String tag, Element element) {

        //check if tag exists
        if(element.getElementsByTagName(tag).item(0) != null){
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }
        else
            return "";

    }


    /**
     *
     * Returns arrayList of tags values if exists, empty arrayList otherwise.
     *
     * @param tag
     * @param element
     * @return
     */
    private static ArrayList<String> getValues(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        ArrayList<String>  results = new ArrayList<String>();

        for (int j = 0; j< nodeList.getLength();j++){
            results.add(nodeList.item(j).getChildNodes().item(0).getNodeValue());
        }

        return results;
    }


    public static void vibrate(int duration,Activity activity)
    {
        Vibrator vibs = (Vibrator) activity.getSystemService(activity.VIBRATOR_SERVICE);
        vibs.vibrate(duration);
    }

    public static void playBombSound(Activity activity){
        final MediaPlayer playbomb = MediaPlayer.create(activity, R.raw.bomb);
        playbomb.start();
    }

    public static void playBonusOkSound(Activity activity){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.bonus);
        mp.start();
    }


    public static void playSolutionFoundSound(Activity activity){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.solfound);
        mp.start();
    }


    public static void playLevelUpSound(Activity activity){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.levelup);
        mp.start();
    }


    public static void playErrorSound(Activity activity){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.error);
        mp.start();
    }


}
