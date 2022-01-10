







package com.example.incidentreporterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class Teams extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<TeamsModelClass> teamsList;
    TeamsAdapter teamsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        initData();
        initRecyclerView();
    }

    private void initData() {
        teamsList = new ArrayList<>();
        teamsList.add(new TeamsModelClass(R.drawable.communicate_40px,"Fire Brigade","fire@fire.net","0997576478"));
        teamsList.add(new TeamsModelClass(R.drawable.communicate_40px,"MASM","masm@ealt.com","08857674688"));
        teamsList.add(new TeamsModelClass(R.drawable.communicate_40px,"POLICE","mwpolice@s.mw","0998764784"));
          }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.incidentsRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        teamsAdapter = new TeamsAdapter(teamsList);
        recyclerView.setAdapter(teamsAdapter);
        teamsAdapter.notifyDataSetChanged();
    }
}