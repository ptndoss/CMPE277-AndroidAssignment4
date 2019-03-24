package com.example.doss.serviceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText file1 = null;
    EditText file2 = null;
    EditText file3 = null;
    EditText file4 = null;
    EditText file5 = null;
    private DownloadServices serviceBinder;
    private URL[] urls;
    Button download;
    static String downloadPath;
    String url1 = "";
    String url2 = "";
    String url3 = "";
    String url4 = "";
    String url5 = "";


    String filename1,filename2,filename3,filename4,filename5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file1 = (EditText) findViewById(R.id.editText_file1);
        file2 = (EditText) findViewById(R.id.editText_file2);
        file3 = (EditText) findViewById(R.id.editText_file3);
        file4 = (EditText) findViewById(R.id.editText_file4);
        file5 = (EditText) findViewById(R.id.editText_file5);

        download = (Button) findViewById(R.id.btn_startdownload);

        downloadPath =   getExternalFilesDir(null).toString();
        Log.i("<DOSS>", " " + downloadPath);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");
        registerReceiver(receiver, new IntentFilter(
                DownloadServices.NOTIFICATION));


        download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 url1 = file1.getText().toString();
                 url2 = file2.getText().toString();
                 url3 = file3.getText().toString();
                 url4 = file4.getText().toString();
                 url5 = file5.getText().toString();

                filename1 = url1.substring(url1.lastIndexOf('/')+1);
                filename2 = url2.substring(url2.lastIndexOf('/')+1);
                filename3 = url3.substring(url3.lastIndexOf('/')+1);
                filename4 = url4.substring(url4.lastIndexOf('/')+1);
                filename5 = url5.substring(url5.lastIndexOf('/')+1);


                startDownload(v);

            }
        });
    }


    public void startDownload(View view) {
        Toast.makeText(this, "Starting all downloads !", Toast.LENGTH_SHORT).show();
        Log.i("<DOSS>","STart Download");
        startDownloadService(url1,filename1);

        startDownloadService(url2,filename2);
        waitForNextDownload();
        startDownloadService(url3,filename3);
        waitForNextDownload();
        startDownloadService(url4,filename4);
        waitForNextDownload();
        startDownloadService(url5,filename5);
    }

    private void waitForNextDownload(){
        try {
            //---simulate taking some time to download a file---
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void startDownloadService(String url, String filename) {
        Log.i("<DOSS>","Inside Service Start");
        Intent intent = new Intent(getBaseContext(), DownloadServices.class);
        intent.putExtra("url", url);
        intent.putExtra("filename",filename);
        startService(intent);
        waitForNextDownload();
    }




    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int x;
            if (bundle != null) {
                String filename = bundle.getString("file");
                System.out.println("BroadcastReceiver -> onReceive -> Filename -> " + filename);
                if (filename.equals(filename1)){
                    Toast.makeText(getBaseContext(), filename1+" File downloaded Successfully..",
                            Toast.LENGTH_SHORT).show();
                }
                else if (filename.equals(filename2)){
                    Toast.makeText(getBaseContext(), filename2+" File downloaded Successfully..",
                            Toast.LENGTH_SHORT).show();
                }
                else if (filename.equals(filename3)){
                    Toast.makeText(getBaseContext(), filename3+" File downloaded Successfully..",
                            Toast.LENGTH_SHORT).show();
                }
                else if (filename.equals(filename4)) {
                    Toast.makeText(getBaseContext(), filename4+" File downloaded Successfully..",
                            Toast.LENGTH_SHORT).show();
                }
                else if (filename.equals(filename5)) {
                    Toast.makeText(getBaseContext(), filename5+" File downloaded Successfully..",
                            Toast.LENGTH_SHORT).show();}

            }
        }
    };

}
