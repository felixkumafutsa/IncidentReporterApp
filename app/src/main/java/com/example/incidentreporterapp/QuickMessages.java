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

public class QuickMessages extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference incidentRef = db.collection("quickMessages");
    private QuickMessageAdapter quickMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = incidentRef.orderBy("timestamp").limit(50);
        FirestoreRecyclerOptions<QuickMessageModel> options = new FirestoreRecyclerOptions.Builder<QuickMessageModel>().setQuery(query, QuickMessageModel.class).build();
        quickMessageAdapter = new QuickMessageAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.quickMsgRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quickMessageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        quickMessageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        quickMessageAdapter.stopListening();
    }
}