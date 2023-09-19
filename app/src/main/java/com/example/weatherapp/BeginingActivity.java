package com.example.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class BeginingActivity extends AppCompatActivity {

    public TextInputEditText citySearchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begining);

        citySearchET = findViewById(R.id.citySearchET);



    }
}