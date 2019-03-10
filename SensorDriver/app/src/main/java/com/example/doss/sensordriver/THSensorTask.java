package com.example.doss.sensordriver;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Random;

public class THSensorTask extends AsyncTask<Integer, Integer, Integer>{

    UIUpdate uiUpdate = null;

    public THSensorTask(UIUpdate uiUpdate){
        this.uiUpdate = uiUpdate;
    }

    @Override
    protected void onPreExecute() {
        THSensorMainActivity main = (THSensorMainActivity)uiUpdate;
        main.generate.setClickable(false);
        Toast.makeText(main, "Async Task Started...!", Toast.LENGTH_LONG).show();
    }


    @Override
    protected Integer doInBackground(Integer... readings) {
        int count = readings[0];
        Random rand = new Random();
        for (int i = 1; i <= count; i++) {
            int temp = rand.nextInt((100-25) + 25) ;
            int humi = rand.nextInt((100 - 40) + 40);
            int activity = rand.nextInt((500 -1) + 1);
            publishProgress(temp, humi, activity, i);
            THSensorTask.Threadsleep(5);

            if (isCancelled()) break;
        }
        return 1;
    }

    @Override
    protected void onProgressUpdate(Integer... readings) {

        uiUpdate.updateUI(readings[0], readings[1], readings[2], readings[3]);

        Toast.makeText((Context) uiUpdate, "Output -  " + readings[3], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Integer params) {

        THSensorMainActivity mainActivity = (THSensorMainActivity)uiUpdate;
        mainActivity.generate.setClickable(true);
        Toast.makeText(mainActivity, "Task Completed!", Toast.LENGTH_SHORT).show();
    }

    public static void Threadsleep(int secs)
    {
        try
        {
            Thread.sleep(secs * 1000);
        }
        catch(InterruptedException ex)
        {
            throw new RuntimeException("interrupted",ex);
        }
    }

}
