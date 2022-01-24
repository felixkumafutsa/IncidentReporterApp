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
    private IncidentsAdapter incidentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = incidentRef.limit(100);
        FirestoreRecyclerOptions<IncidentModelClass> options = new FirestoreRecyclerOptions.Builder<IncidentModelClass>().setQuery(query, IncidentModelClass.class).build();
        incidentsAdapter = new IncidentsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.incidentsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(incidentsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        incidentsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        incidentsAdapter.stopListening();
    }
}