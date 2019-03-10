package com.example.doss.currencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter extends AppCompatActivity  {
    ListAdapter listadapter;
//    ListView listview;
    Spinner currency = null;
    String chosenCurrency;
    private String toggle= "False";

    RequestQueue requestQueue;
    String jsonValues = "https://api.exchangeratesapi.io/latest?base=USD";
    String data = "";
    double pound;
    double en;
    double rupee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_currency_converter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        currency = (Spinner) findViewById(R.id.cnvrtTo_spinner);
//        currency.setOnItemSelectedListener(this);
        List<String> currList = new ArrayList<String>();
        currList.add("Euro");
        currList.add("British Pound");
        currList.add("Indian Rupees");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(dataAdapter);

        Bundle extras = getIntent().getExtras();
        Float amnt = getIntent().getFloatExtra("Dollar_Amount", 0);
        toggle = getIntent().getStringExtra("TOGGLE");
        Log.i("<DOSS>", "Toggle Value " + toggle);
        Log.i("<DOSS> in Main", "Amnt "+amnt);

        if(toggle != null && toggle.equalsIgnoreCase("True")){
            TextView textDesc = (TextView) findViewById(R.id.txtView_description);
            Float s = getIntent().getFloatExtra("Dollar_Amount", 0);
            String converted_currency = getIntent().getStringExtra("CONVERTED_CURRENCY");
            Double d = getIntent().getDoubleExtra("CONVERTED_DOLLAR_AMNT",0.0);
            textDesc.setText("Dollar Amount "+s + " converted to " +d + " in " + converted_currency);
            Log.i("<DOSS> in Main", "Amnt "+s);
            Log.i("<DOSS> in MainActivity", textDesc.getText().toString());
//        currency.setText(getIntent().getStringExtra("CURRENCY"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                chosenCurrency = parent.getItemAtPosition(position).toString();
                Log.i("<DOSS>Currency", chosenCurrency);
                Intent intent = new Intent(CurrencyConverter.this, CurrencyExchange.class).putExtra("CURRENCY",chosenCurrency);

            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void sendMessage(View view)
    {
        String dol_amnt = ((EditText)findViewById(R.id.dollarAmnt)).getText().toString();
        Intent intent = new Intent(CurrencyConverter.this, CurrencyExchange.class);
        intent.putExtra("DYNAMIC_DOLLAR",dol_amnt);
        intent.putExtra("CURRENCY",chosenCurrency );

        startActivity(intent);
    }



    public void close(View view){
        finish();
    }

  /*  @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
