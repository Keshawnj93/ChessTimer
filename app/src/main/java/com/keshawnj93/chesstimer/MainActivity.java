package com.keshawnj93.chesstimer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static long STARTTIME1, STARTTIME2;
    private long mTimeLeft1, mTimeLeft2;
    boolean timer1Running, timer2Running;
    RelativeLayout player1, player2;
    TextView tvTimep1, tvTimep2;
    String mode1 = "Start", mode2 = "Start", turn;
    CountDownTimer mCountDownTimer1, mCountDownTimer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        STARTTIME1 = extras.getInt("STARTTIME1");
        mTimeLeft1 = STARTTIME1;
        STARTTIME2 = extras.getInt("STARTTIME2");
        turn = extras.getString("TURN");
        mTimeLeft2 = STARTTIME2;

        player1 = findViewById(R.id.player1);
        player1.setBackgroundColor(Color.parseColor("#ffffff"));

        player2 = findViewById(R.id.player2);
        player2.setBackgroundColor(Color.parseColor("#000000"));

        tvTimep1 = findViewById(R.id.tvTimep1);
        tvTimep1.setRotation(270);
        tvTimep1.setTextColor(Color.parseColor("#000000"));
        tvTimep1.setTextSize(32);
        if (turn.equals("1")){
            tvTimep1.setText("START");
        }
        else{
            tvTimep1.setText(milliToString("1"));
        }

        tvTimep2 = findViewById(R.id.tvTimep2);
        tvTimep2.setRotation(270);
        tvTimep2.setTextColor(Color.parseColor("#ffffff"));
        tvTimep2.setTextSize(32);
        if (turn.equals("2")){
            tvTimep2.setText("START");
        }
        else{
            tvTimep2.setText(milliToString("2"));
        }


        player1.setOnClickListener(v -> {
            //Reset if either timer has finished
            if (mode1.equals("Finished") || mode2.equals("Finished") ){
                resetTimer("1");
            }

            //Switch the timer if the countdown has already started
            else if (timer1Running){
                switchTimer("1");
            }

            //Begin the countdown timer
            else if (turn.equals("1")){
                startTimer("1");
            }
        });

        player2.setOnClickListener(v -> {
            //Reset if either timer has finished
            if (mode1.equals("Finished") || mode2.equals("Finished") ){
                resetTimer("2");
            }

            //Switch the timer if the countdown has already started
            else if (timer2Running){
                switchTimer("2");
            }

            //Begin the countdown timer
            else if (turn.equals("2")){
                startTimer("2");
            }

        });
    }

    private void switchTimer(String t){
        if (t.equals("1")){
            pauseTimer("1");
            turn = "2";
            startTimer(turn);
        }

        else if (t.equals("2")){
            pauseTimer("2");
            turn = "1";
            startTimer(turn);
        }
    }

    private void startTimer(String t){

        if (t.equals("1")){
            mCountDownTimer1 = new CountDownTimer(mTimeLeft1, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeft1 = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    win("2");
                    mode1 = "Finished";
                }
            }.start();

            timer1Running = true;
            mode1 = "Pause";
            //player1.setBackgroundColor(Color.parseColor("#ff8989"));
        }

        else if (t.equals("2")){
            mCountDownTimer2 = new CountDownTimer(mTimeLeft2, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeft2 = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    win("1");
                    mode1 = "Finished";
                }
            }.start();

            timer2Running = true;
            mode2 = "Pause";
            //player2.setBackgroundColor(Color.parseColor("#ff8989"));
        }
    }

    private void pauseTimer(String t){
        if (t.equals("1")){
            try{
                mCountDownTimer1.cancel();
            } catch (Exception e){
                //
            }
            timer1Running = false;
            //player1.setBackgroundColor(Color.parseColor("#a05555"));
        }

        else if (t.equals("2")){
            try{
                mCountDownTimer2.cancel();
            } catch (Exception e){
                //
            }
            timer2Running = false;
            //player2.setBackgroundColor(Color.parseColor("#a05555"));
        }
    }

    private void resetTimer(String t){
        /*mode1 = mode2 = "Start";
        mTimeLeft1 = mTimeLeft2 = STARTTIME;
        player1.setBackgroundColor(Color.parseColor("#ffffff"));
        player2.setBackgroundColor(Color.parseColor("#000000"));

        updateCountDownText();
        turn = t; */

        Intent goToMenu = new Intent(getApplicationContext(), Menu.class);
        startActivity(goToMenu);
    }

    private void win(String winner){
        pauseTimer(winner);
        timer1Running = timer2Running = false;

        if (winner.equals("1")){
            tvTimep2.setText("00:00:000");
            player1.setBackgroundColor(Color.parseColor("#34d62f"));
            player2.setBackgroundColor(Color.parseColor("#d6342f"));
        }

        else if (winner.equals("2")){
            tvTimep1.setText("00:00:000");
            player1.setBackgroundColor(Color.parseColor("#d6342f"));
            player2.setBackgroundColor(Color.parseColor("#34d62f"));
        }
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeft1 / 1000) / 60;
        int seconds = (int) (mTimeLeft1 / 1000) % 60;
        int milli = (int) (mTimeLeft1 % 1000);

        String formattedTime = String.format(Locale.getDefault(),"%02d:%02d:%03d", minutes, seconds, milli);
        tvTimep1.setText(formattedTime);

        minutes = (int) (mTimeLeft2 / 1000) / 60;
        seconds = (int) (mTimeLeft2 / 1000) % 60;
        milli = (int) (mTimeLeft2 % 1000);

        formattedTime = String.format(Locale.getDefault(),"%02d:%02d:%03d", minutes, seconds, milli);
        tvTimep2.setText(formattedTime);
    }

    private  String milliToString(String t){
        int minutes, seconds, milli;
        if (t.equals("1")){
            minutes = (int) (STARTTIME1 / 1000) / 60;
            seconds = (int) (STARTTIME1 / 1000) % 60;
            milli = (int) (STARTTIME1 % 1000);
            return String.format(Locale.getDefault(),"%02d:%02d:%03d", minutes, seconds, milli);
        }

        else if (t.equals("2")){
            minutes = (int) (STARTTIME2 / 1000) / 60;
            seconds = (int) (STARTTIME2 / 1000) % 60;
            milli = (int) (STARTTIME2 % 1000);
            return String.format(Locale.getDefault(),"%02d:%02d:%03d", minutes, seconds, milli);
        }

        return "ERR";
    }
}
