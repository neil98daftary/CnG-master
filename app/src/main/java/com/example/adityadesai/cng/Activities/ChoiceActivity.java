package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.adityadesai.cng.R;

public class ChoiceActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        editor=getSharedPreferences("signInMode",MODE_APPEND).edit();
    }

    public void Buyer(View view)
    {

        editor.putBoolean("isCustomer",true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Vendor(View view)
    {
        editor.putBoolean("isCustomer",false);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
