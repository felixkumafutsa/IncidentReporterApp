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

public class Teams extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<TeamsModelClass> teamsList;
    TeamsAdapter teamsAdapter;
    private  TeamsAdapter.RecyclerViewClickListener teamListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

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
        setOnClickListener();
        recyclerView = findViewById(R.id.teamsRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        teamsAdapter = new TeamsAdapter(teamsList, teamListener);
        recyclerView.setAdapter(teamsAdapter);
        teamsAdapter.notifyDataSetChanged();
    }

    private void setOnClickListener() {
        teamListener = new TeamsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Department.class);
                startActivity(intent);
            }
        };
    }
}