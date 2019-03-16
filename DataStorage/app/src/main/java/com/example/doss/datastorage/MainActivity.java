package com.example.doss.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            InputStream in=openFileInput(PreferenceView.STORE_PREFERENCES);
            if(in!=null)
            {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null)
                {
                    buf.append(str +"\n");
                }
                in.close();
                TextView savedPref=(TextView)findViewById(R.id.textView_saveddata);
                savedPref.setText(buf.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void launchPreferences(View view){
        Intent intent = new Intent(this, PreferenceView.class);
        startActivity(intent);
    }

    public void launchSQLite(View view){
        Intent intent = new Intent(this, SQLiteView.class);
        startActivity(intent);
    }

    public void exitApp(View view)
    {
        finish();
        System.exit(0);
    }

}
