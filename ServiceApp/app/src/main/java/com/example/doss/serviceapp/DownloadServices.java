package com.example.doss.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadServices extends Service {

    private boolean success = false;
    public static final String NOTIFICATION = "service receiver";

    public class MyBinder extends Binder {
        DownloadServices getService() {
            Log.i("<DOSS>", "MyBinder Services");
            return DownloadServices.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        String urlPath = intent.getStringExtra("url");
        Log.i("<DOSS>", "OnStart Command"+urlPath);
        String fileName = intent.getStringExtra("filename");
        Log.i("<DOSS>", "OnStart Command"+fileName);
        new DoBackgroundTask().execute(urlPath,fileName);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Has been stopped", Toast.LENGTH_LONG).show();
    }

    private class DoBackgroundTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... urls) {
            int count = 0;
            long totalBytesDownloaded = 0;

            File output = new File(getExternalFilesDir(null),urls[1]);
            try {
                System.out.println(urls[1]);
                System.out.println("Downloading " + urls[1]);
                URL url = new URL(urls[0]);

                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                OutputStream outputStream = new FileOutputStream(output.getPath());
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
                success = true;


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            Log.i("<DOSS>","DownloadStatus " + success);

            onProgressUpdate(urls[1]);
            return null;

        }


        protected void onProgressUpdate(Integer... progress) {
          /*  Intent intent = new Intent(NOTIFICATION);
            intent.putExtra("file", filename);
            sendBroadcast(intent);*/

            Log.d("Downloading files",
                    String.valueOf(progress[0]) + "% downloaded");
            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% downloaded",
                    Toast.LENGTH_LONG).show();

        }

        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(),
                    "Downloaded " + result + " bytes",
                    Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }
}
