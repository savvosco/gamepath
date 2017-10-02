package com.savvo.tosco.gamepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.savvo.tosco.gamepath.R;

public class StartActivity extends AppCompatActivity {


    //TODO move in file properties
    private static final String[] MODES={"ALL","NO ORIZONTAL","NO VERTICAL","NO DIAGONAL"};
    private static final String[] NODE={"","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};

    private Spinner spinnerMode,spinnerFrom,spinnerTo,spinnerNot,spinnerLevel;
    private Button startButton;
    private CheckBox intersection,concatenate,timer,notMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                EditText levelNumber = (EditText) findViewById(R.id.levelNumber);

                String level = levelNumber.getText().toString();

                //if level is valorized, load extras from level selected
                if (!"".equals(level) && level != null) {
                    intent = new Intent(v.getContext(), BeforePlayActivity.class);
                    intent.putExtra("level",level);
                    v.getContext().startActivity(intent);

                }
                else {

                    intent = new Intent(v.getContext(), MainActivity.class);

                int modeSelected = (int) spinnerMode.getSelectedItemId();
                intent.putExtra("mode", modeSelected);

                if (concatenate.isChecked())
                    intent.putExtra("concatenate", true);

                if (intersection.isChecked())
                    intent.putExtra("intersection", true);

                if (timer.isChecked())
                    intent.putExtra("timer", true);

                if (notMode.isChecked()) {
                    intent.putExtra("notMode", true);
                }

                String to = spinnerTo.getSelectedItem().toString();
                String from = spinnerFrom.getSelectedItem().toString();
                String not = spinnerNot.getSelectedItem().toString();

                if (null != from && !"".equals(from)) {
                    intent.putExtra("start", from);
                }

                if (null != to && !"".equals(to)) {
                    intent.putExtra("end", to);
                }

                if (null != not && !"".equals(not)) {
                    intent.putExtra("not", not);
                }

            }

            v.getContext().startActivity(intent);

            }
        });

        spinnerMode = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapterMode = new ArrayAdapter<String>(StartActivity.this,
                android.R.layout.simple_spinner_item,MODES);
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adapterMode);

        ArrayAdapter<String> adapterNode = new ArrayAdapter<String>(StartActivity.this,
                android.R.layout.simple_spinner_item,NODE);

        spinnerTo = (Spinner)findViewById(R.id.spinnerTo);
        adapterNode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(adapterNode);

        spinnerFrom = (Spinner)findViewById(R.id.spinnerFrom);
        adapterNode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapterNode);

        spinnerNot = (Spinner)findViewById(R.id.spinnerNot);
        adapterNode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNot.setAdapter(adapterNode);

        //spinnerLevel = (Spinner)findViewById(R.id.spinnerLevel);
        //adapterNode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerLevel.setAdapter(adapterNode);

        concatenate = (CheckBox) findViewById(R.id.concatenate);
        intersection = (CheckBox) findViewById(R.id.intersection);
        timer = (CheckBox) findViewById(R.id.timeModeBox);
        notMode = (CheckBox) findViewById(R.id.bombMode);

    }

}
