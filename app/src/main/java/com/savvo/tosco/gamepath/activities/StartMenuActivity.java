package com.savvo.tosco.gamepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.savvo.tosco.gamepath.core.GameManger;
import com.savvo.tosco.gamepath.core.LevelManager;
import com.savvo.tosco.gamepath.R;

public class StartMenuActivity extends AppCompatActivity {

    private Button levelButton,freegameButton,optionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        //TODO put into load game function!
        GameManger.getInstance(this);
        LevelManager lManager = LevelManager.getInstance(this);

        //final int level = lManager.getCurrentLevel().getId();
        final int level = lManager.getFirstLevelId();

        levelButton = (Button) findViewById(R.id.level);
        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BeforePlayActivity.class);
                intent.putExtra("level",+level+"");
                //intent.putExtra("level","12");
                v.getContext().startActivity(intent);
            }
        });

        freegameButton = (Button) findViewById(R.id.freegame);
        freegameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("level",+LevelManager.NO_LEVEL_GAME+"");
                v.getContext().startActivity(intent);
            }
        });

        optionButton = (Button) findViewById(R.id.option);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StartActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}
