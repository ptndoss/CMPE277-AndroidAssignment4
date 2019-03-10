package com.example.doss.sensordriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class THSensorMainActivity extends AppCompatActivity  implements  UIUpdate{

    private EditText temp = null;
    private EditText humi = null;
    private EditText act = null;
    private EditText sensorCount = null;
    private EditText output = null;
    public  TextView generate = null;
    private TextView cancle = null;
    private int count = 0;
    private THSensorTask asyncTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thsensor);

        temp = (EditText) findViewById(R.id.editText_temp);
        humi = (EditText) findViewById(R.id.editText_humid);
        act = (EditText) findViewById(R.id.editText_activity);
        sensorCount = (EditText) findViewById(R.id.editText_sensoreading);
        output = (EditText) findViewById(R.id.output);
        generate = (TextView) findViewById(R.id.button_generate);
        cancle = (TextView) findViewById(R.id.button_cncl);

        temp.setFocusable(false);
        humi.setFocusable(false);
        act.setFocusable(false);
        output.setFocusable(false);

    }

    public void Generate(View view){

        this.count =Integer.parseInt(sensorCount.getText().toString());
        Log.i("<DOSS>","Count is " + this.count);
        temp.setText("");
        humi.setText("");
        act.setText("");
        output.setText("");
        Log.i("<DOSS>","Set all the text with blank1");
        asyncTask = new THSensorTask(THSensorMainActivity.this);
        Log.i("<DOSS>","Set all the text with blank2");
        asyncTask.execute(count);
        Log.i("<DOSS>","Set all the text with blank3");
    }

    @Override
    public void updateUI(int temperature, int humidity, int activity, int readingCounter) {

        temp.setText(temperature+"");
        humi.setText(humidity+"");
        act.setText(activity+"");
        sensorCount.setText((count - readingCounter)+"" );
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Output : ").append(readingCounter+ "\n");
        stringBuilder.append("Temperature: ").append(temperature + "\n");
        stringBuilder.append("Humidity: ").append(humidity + "\n");
        stringBuilder.append("Activity: ").append(activity + "\n");
        output.append(stringBuilder.toString());
        output.setMovementMethod(new ScrollingMovementMethod());

    }

    public void Cancel(View view) {
        asyncTask.cancel(true);
        generate.setClickable(true);
        Toast.makeText(this, "The Async Task Stopped!", Toast.LENGTH_SHORT).show();
    }
}
