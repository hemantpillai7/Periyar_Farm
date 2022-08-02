package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Services extends AppCompatActivity
{
    CardView amc,soil,bee,scheme,farm,garden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Our Services");

        amc=findViewById(R.id.serLocateAMC);
        soil=findViewById(R.id.soil);
        bee=findViewById(R.id.bee);
        scheme=findViewById(R.id.govscheme);
        farm=findViewById(R.id.setupfarm);
        garden=findViewById(R.id.garden);

        amc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), ServiceLocateNearestApmc.class);
                startActivity(i);
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ServiceSoilTestingActivity.class);
                startActivity(i);
            }
        });

        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ServiceBeeFarming.class);
                startActivity(i);
            }
        });
        scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ServiceGovSchemes.class);
                startActivity(i);
            }
        });
        farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ServiceSetupFarm.class);
                startActivity(i);
            }
        });
        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ServiceHomeGarden.class);
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}