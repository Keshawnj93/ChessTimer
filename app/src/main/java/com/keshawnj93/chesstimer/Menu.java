package com.keshawnj93.chesstimer;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Locale;

public class Menu extends Activity {

    Spinner spMin1, spMin2, spSec1, spSec2;
    RadioButton rbPlayer1, rbPlayer2;
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        spMin1 = (Spinner) findViewById(R.id.spMin1);
        setSpinner(spMin1, 0);
        spMin2 = (Spinner) findViewById(R.id.spMin2);
        setSpinner(spMin2, 0);
        spSec1 = (Spinner) findViewById(R.id.spSec1);
        setSpinner(spSec1, 1);
        spSec2 = (Spinner) findViewById(R.id.spSec2);
        setSpinner(spSec2, 1);

        rbPlayer1 = (RadioButton) findViewById(R.id.rbPlayer1);
        rbPlayer2 = (RadioButton) findViewById(R.id.rbPlayer2);

        btStart = (Button) findViewById(R.id.btStart);

        btStart.setOnClickListener(e -> {
            // if time is 00:00, error
            if ((spMin1.getSelectedItem().toString().equals("00") && spSec1.getSelectedItem().toString().equals("00")) ||
                    (spMin2.getSelectedItem().toString().equals("00") && spSec2.getSelectedItem().toString().equals("00"))){

            }

            else {
                String turn = "";
                if (rbPlayer1.isChecked()){
                    turn = "1";
                }

                else if (rbPlayer2.isChecked()){
                    turn = "2";
                }

                Intent goToTimer = new Intent(getApplicationContext(), MainActivity.class);
                goToTimer.putExtra("STARTTIME1", getStartTime(spMin1.getSelectedItem().toString(), spSec1.getSelectedItem().toString()));
                goToTimer.putExtra("STARTTIME2", getStartTime(spMin2.getSelectedItem().toString(), spSec2.getSelectedItem().toString()));
                goToTimer.putExtra("TURN", turn);

                startActivity(goToTimer);
            }
        });


    }

    private void setSpinner(Spinner s, int mode){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerAdapter);

        //Mode 0 - Populate minutes spinners (0 to 99)
        if (mode == 0){
            for (int i = 0; i < 100; i++){
                spinnerAdapter.add(String.format(Locale.getDefault(),"%02d", i));
            }
        }

        //Mode 0 - Populate seconds spinners (0 to 59)
        else if (mode == 1){
            for (int i = 0; i < 60; i++){
                spinnerAdapter.add(String.format(Locale.getDefault(),"%02d", i));
            }
        }

        spinnerAdapter.notifyDataSetChanged();
        s.setSelection(0);
    }

    private int getStartTime(String m, String s){
        //Get starting time in Milliseconds
        return ((Integer.parseInt(m) * 60000) + (Integer.parseInt(s) * 1000));
    }

}
