package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    CameraManager cameraManager;
    SensorManager sensorManager;
    Sensor sensor;
    private static final int CAMERA_REQUEST = 123;
    boolean hasCameraFlash = false;
    Button flashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        flashButton = (Button) findViewById(R.id.OnOff_btn);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onClick(View view) {
        if (hasCameraFlash) {
            Toast.makeText(MainActivity.this, "Flash Light Turned On",
                    Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(MainActivity.this, "No Flash Light Available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("LightValue",""+event.values[0]);
        if(event.values[0]==0) {
            flashLightOn(event.values[0]);
        }
        else {
            flashLightOff(event.values[0]);
        }
    }
    public void flashLightOn(float value) {
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            Log.d("LightOncameraId",cameraId);

                cameraManager.setTorchMode(cameraId, true);
                Toast.makeText(getBaseContext(), "Turning On FlashLight"+ "Environment luminous value is " +value,
                        Toast.LENGTH_LONG).show();

        } catch (Exception e) {
        }
    }

    public void flashLightOff(float value) {
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            Log.d("LightOffcameraId",cameraId);

            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(getBaseContext(), "Turning Off FlashLight"+ "Environment luminous value is " +value,
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

