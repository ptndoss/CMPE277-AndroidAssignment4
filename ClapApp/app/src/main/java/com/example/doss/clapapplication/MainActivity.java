package com.example.doss.clapapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView display = null;
    SensorManager sensorMgr;
    Sensor sensor;
    AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    boolean raiseUP=true;
    float old_val=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        display = (TextView)findViewById(R.id.txtView_display);

         audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.tone);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        float value = event.values[0];

        mediaPlayer.start();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if(event.values[0] > old_val){
            old_val=value;
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            display.setText("Volume Increased");
            Log.i("<DOSS>HIgh", ""+old_val);

        }
        else if(event.values[0] < old_val){
            old_val=value;
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            display.setText("Volume Decreased");
            Log.i("<DOSS>Low", ""+old_val);

        }


       /* if(event.sensor.getType() == sensor.TYPE_PROXIMITY){

            if(value <= old_val){
                Log.i("<Doss>", "OldValue.... " + old_val);
                raiseUP = false;
            }
            else{
                raiseUP= true;
            }

            if(raiseUP && value <= old_val ) {
                Log.i("<Doss>", "eventValue " + event.values[0]);
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                display.setText("IncreasingVolume..");
                raiseUP = false;
                old_val = value;
                Log.i("<Doss>", "old " + old_val);
            }
            else{
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                display.setText("DecreasingVolume..");
                raiseUP = true;
                old_val = value;
            }
        }*/

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
