package com.example.doss.currencyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class CurrencyBroadCastReciever extends BroadcastReceiver {

    float dollar_amount;
    String currency;
    double converted_Dollar;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();

        dollar_amount = (Float) extras.get("DOLLAR_AMOUNT");
        currency = (String) extras.get("CURRENCY");
        converted_Dollar = (Double)extras.get("CONVERTED_DOLLAR_AMNT");
        Log.i("DOlar Amnt" , "Value = " + dollar_amount);
        Intent i = new Intent(context , CurrencyConverter.class);
        i.putExtra("Dollar_Amount", dollar_amount);
        i.putExtra("CONVERTED_CURRENCY", currency);
        i.putExtra("CONVERTED_DOLLAR_AMNT", converted_Dollar);
        i.putExtra("TOGGLE", "TRUE");
        Log.i("<DOSS>", "In broadcast reciver"+ dollar_amount);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
        context.startActivity(i);
//        Toast.makeText(context, "Intent Detected." + intent.getAction(), Toast.LENGTH_LONG).show();
    }
}
