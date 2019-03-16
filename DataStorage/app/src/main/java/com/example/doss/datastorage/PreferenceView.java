package com.example.doss.datastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceView extends AppCompatActivity {

    private EditText bookName = null;
    private EditText authorName = null;
    private EditText description = null;
    public int counter=0;
    public final static String STORE_PREFERENCES="storePrefFinal1.txt";
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_view);

        bookName = findViewById(R.id.editText_bookName);
        description = findViewById(R.id.editText_description);
        authorName = findViewById(R.id.editText_AuthorName);

    }

    public void onSave(View view)
    {
        //save the preferences (if not null) in a file.
        EditText name_text=(EditText)findViewById(R.id.editText_bookName);
        String name=name_text.getText().toString();
        EditText author_text=(EditText)findViewById(R.id.editText_AuthorName);
        String author=author_text.getText().toString();
        EditText des_text=(EditText)findViewById(R.id.editText_description);
        String description=des_text.getText().toString();

        if(name!=null && author!=null && description!=null)
        {
            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("COUNTER", counter);
                editor.commit();

                SharedPreferences sharedpreferences= getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE);
                String channel = sharedpreferences.getString(STORE_PREFERENCES, "null");


                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(STORE_PREFERENCES,MODE_APPEND));
                String message="\nSaved Preference "+counter+", "+s.format(new Date());
                out.write(message);
                out.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void cancel(View view) {

        bookName.setText("");
        authorName.setText("");
        description.setText("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
