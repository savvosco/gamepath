package com.savvo.tosco.gamepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.savvo.tosco.gamepath.R;

import com.savvo.tosco.gamepath.core.LevelManager;


public class BeforePlayActivity extends AppCompatActivity {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_play);

        level = Integer.parseInt(getIntent().getExtras().getString("level"));

        LevelManager levelManager = LevelManager.getInstance(this);

        TextView levelDescription = (TextView) findViewById(R.id.descriptionLevelTextView);
        TextView levelName = (TextView) findViewById(R.id.levelNameTextView);

        levelDescription.setText(levelManager.getGameLevels().get(level).getDescription());
        levelName.setText(levelManager.getGameLevels().get(level).getName());

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeforePlayActivity.this,MainActivity.class);
                intent.putExtra("level",level+"");
                v.getContext().startActivity(intent);

            }
        });




    }
}
