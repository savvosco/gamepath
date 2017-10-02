package com.savvo.tosco.gamepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.savvo.tosco.gamepath.R;

import com.savvo.tosco.gamepath.core.LevelManager;

public class ResultsActivity extends AppCompatActivity {

    private int score,solutionFound,recordScore,recordSolutionFound,mode;
    private boolean cocatenateMode, intersectMode,timerMode,nextLevelVisibility;

    private int levelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.score = extras.getInt("score");
            this.solutionFound = extras.getInt("sol");

            this.levelId = Integer.parseInt(extras.getString("level"));

            this.mode= extras.getInt("mode");
            this.cocatenateMode = extras.getBoolean("concatenate");
            this.intersectMode = extras.getBoolean("intersect");
            this.timerMode = extras.getBoolean("timer");

            this.nextLevelVisibility = extras.getBoolean("result-level");
        }

        TextView scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText(""+score);
        TextView solFoundText = (TextView) findViewById(R.id.solcount);
        solFoundText.setText(""+solutionFound);
        TextView solRecord = (TextView) findViewById(R.id.record);
        solRecord.setText(""+score);

        Button restartButton = (Button) findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ResultsActivity.this, BeforePlayActivity.class);
                intent.putExtra("level", levelId+"");
                startActivity(intent);
            }
        });

        Button menuButton = (Button) findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultsActivity.this,StartMenuActivity.class);
                startActivity(intent);
            }
        });

        Button nextLevelButton = (Button) findViewById(R.id.nextLevelButton);
        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultsActivity.this, BeforePlayActivity.class);
                intent.putExtra("level", (levelId+1)+"");
                startActivity(intent);
            }
        });

        if(!nextLevelVisibility)
            nextLevelButton.setVisibility(View.INVISIBLE);

        if(levelId== LevelManager.NO_LEVEL_GAME)
            nextLevelButton.setVisibility(View.INVISIBLE);
    }
}
