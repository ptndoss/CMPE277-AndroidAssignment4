package com.example.doss.datastorage;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteView extends AppCompatActivity {

    private EditText blogMessage = null;
    public final static String STORE_PREFERENCES="storePrefFinal1.txt";
    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_view);

           blogMessage = findViewById(R.id.editText_blgMsg);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sqlite, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveMessage(View view)
    {
        EditText editText=(EditText)findViewById(R.id.editText_blgMsg);
        String message=editText.getText().toString();
        Controller dataController=new Controller(getBaseContext());
        dataController.open();
        long retValue= dataController.insert(message);

        ContentValues values = new ContentValues();
        values.put("BLOGMSG", blogMessage.getText().toString());

//        retValue= dataController.insert(values);


        dataController.close();




        if(retValue!=-1)
        {
            Context context = getApplicationContext();
            CharSequence text=getString(R.string.save_success_msg);
            int duration=Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();

            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.commit();



                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(SQLiteView.STORE_PREFERENCES,MODE_APPEND));
                out.write("\nSQLite "+counter+","+blogMessage.getText().toString() +s.format(new Date()));
                out.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }


    public void cancel(View view) {

        blogMessage.setText("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
