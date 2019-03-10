package com.example.doss.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyExchange extends AppCompatActivity {

    private float dollar_amt;
    private String to_convert_to_currency,dollar_amt_string,result_str=null;
    private double converted_dollar_amnt;
    RequestQueue requestQueue;
    String jsonValues = "https://api.exchangeratesapi.io/latest?base=USD";
    String data = "";
    double pound=0.7682;
    double en=0.8897;
    double rupee=69.99;
//    TextView txPound = (TextView) findViewById(R.id.disabledpound);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencu_converter_page2);
    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView dollarAmnt = (TextView)findViewById(R.id.dymanicTextView);
        TextView currency = (TextView) findViewById(R.id.currency);

        dollarAmnt.setText(getIntent().getStringExtra("DYNAMIC_DOLLAR"));
        currency.setText(getIntent().getStringExtra("CURRENCY"));
        String curr = getIntent().getStringExtra("CURRENCY");
        Log.i("<DOSS>.....",curr);
    }


    public void confirm(View view)
    {
        CurrencyBroadCastReciever broadcast = new CurrencyBroadCastReciever();
        Intent intent = new Intent(this, CurrencyBroadCastReciever.class);



        requestQueue = Volley.newRequestQueue(this);
        Log.i("<DOSS>API ", "Data " + data);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, jsonValues,

                new Response.Listener<JSONObject>() {
                    String gbp;
                    String euro;
                    String rup;
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("<DOSS>API ", "Data " + data);
                        try {
                            JSONObject obj = response.getJSONObject("rates");
                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            gbp = obj.getString("GBP");
                            euro = obj.getString("EUR");
                            rup = obj.getString("INR");

                            pound = Double.parseDouble(gbp);
                            en = Double.parseDouble(euro);
                            rupee = Double.parseDouble(rup);

//                            txPound.setText(gbp);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            Log.i("<DOSS>API ", "Exception " + data);
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        requestQueue.add(obreq);

        dollar_amt =  Float.valueOf(getIntent().getStringExtra("DYNAMIC_DOLLAR"));

        to_convert_to_currency = getIntent().getStringExtra("CURRENCY");
        Log.i("<DOSS>API ",""+dollar_amt );
        switch(to_convert_to_currency){
            case "British Pound":
                converted_dollar_amnt = dollar_amt * pound;
                break;
            case "Indian Rupees":
                converted_dollar_amnt = dollar_amt * rupee;
                break;

            case "Euro":
                converted_dollar_amnt = dollar_amt * en;
                break;
        }


        intent.setAction("cmpe277.assignment2.CUSTOM_INTENT");
        intent.putExtra("DOLLAR_AMOUNT", dollar_amt);
        intent.putExtra("CURRENCY", to_convert_to_currency);
        intent.putExtra("CONVERTED_DOLLAR_AMNT", converted_dollar_amnt);
        sendBroadcast(intent);

    }

    public void close(View view){
            finish();
    }
}
