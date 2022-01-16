package com.example.incidentreporterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class Incidents extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<IncidentsModelClass> incidentsList;
    IncidentsAdapter incidentsAdapter;
    private  IncidentsAdapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        initData();
        initRecyclerView();
    }

    private void initData() {
        incidentsList = new ArrayList<>();
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Fire","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Domestic Violence","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"Robbery","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"ddddddd","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"ddddddd","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"ddddddd","ddddddd","ddddddd"));
        incidentsList.add(new IncidentsModelClass(R.drawable.communicate_40px,"ddddddd","ddddddd","ddddddd"));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.incidentsRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        incidentsAdapter = new IncidentsAdapter(incidentsList, listener);
        recyclerView.setAdapter(incidentsAdapter);
        incidentsAdapter.notifyDataSetChanged();
    }

    private void setOnClickListener() {
        listener = new IncidentsAdapter.RecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Department.class);
                startActivity(intent);
            }
        };
    }
}