package com.example.incidentreporterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Department extends AppCompatActivity {
    ImageButton moreIncidents,moreTeams;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<IncidentsModelClass> incidentsList;
    IncidentsAdapter incidentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        moreIncidents =(ImageButton)  findViewById(R.id.notificationMsgBtn);
        moreTeams =(ImageButton) findViewById(R.id.addTeamBtn);

        initData();
        initRecyclerView();

        moreIncidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Department.this, Incidents.class);
                startActivity(intent);
            }
        });
        moreTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Department.this,Teams.class);
                startActivity(intent);
            }
        });


    }
    private void initData() {
        incidentsList = new ArrayList<>();
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Fire","-112343","243564"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Domestic Violence","334223","44534"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Robbery","-112344","243564"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Ambus","-123322","664532"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Fight","-123322","243564"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Rape","-123322","445362"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Road Accident","-123322","334876"));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.incidentsRecycler1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        incidentsAdapter = new IncidentsAdapter(incidentsList);
        recyclerView.setAdapter(incidentsAdapter);
        incidentsAdapter.notifyDataSetChanged();
    }
}