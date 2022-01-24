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

public class Incidents extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference incidentRef = db.collection("incidents");
    private IncidentAdapter incidentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = incidentRef.orderBy("timestamp").limit(50);
        FirestoreRecyclerOptions<IncidentModel> options = new FirestoreRecyclerOptions.Builder<IncidentModel>().setQuery(query, IncidentModel.class).build();
        incidentAdapter = new IncidentAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.incidentsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(incidentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        incidentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        incidentAdapter.stopListening();
    }
}