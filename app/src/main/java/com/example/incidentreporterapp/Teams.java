package com.example.incidentreporterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Teams extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference teamRef = db.collection("Users");
    private TeamsAdapter teamsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = teamRef.orderBy("FullName").limit(50);
        FirestoreRecyclerOptions<TeamsModelClass> options = new FirestoreRecyclerOptions.Builder<TeamsModelClass>().setQuery(query, TeamsModelClass.class).build();
        teamsAdapter = new TeamsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.teamsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(teamsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
       teamsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        teamsAdapter.stopListening();
    }
}