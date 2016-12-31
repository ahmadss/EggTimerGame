package com.androidlokomedia.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void updateTimer(int scondLeft){
        int minute = (int) scondLeft / 60;
        int second = scondLeft - minute * 60;
        String secondString = Integer.toString(second);
        if (second <= 9){
           secondString = "0"+secondString;
        }
        timerTextView.setText(Integer.toString(minute)+":"+secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerSeekbar = (SeekBar)findViewById(R.id.seekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.cotrollerButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go");
        timerSeekbar.setEnabled(true);
        counterIsActive = false;
    }

    public void onClickController(View view){
        if (counterIsActive==false){
            counterIsActive =  true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("STOP");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress()* 1000,1000){
                @Override
                public void onTick(long l) {
                    updateTimer((int)l/1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
