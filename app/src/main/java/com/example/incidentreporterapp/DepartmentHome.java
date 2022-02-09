package com.example.incidentreporterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DepartmentHome extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference incidentRef = db.collection("incidents");
    private IncidentAdapter incidentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_home);

        Button buttonL = findViewById(R.id.buttonForOut);
        setRecyclerView();

        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }

    private void setRecyclerView() {
        Query query = incidentRef.orderBy("timestamp").limit(50);
        FirestoreRecyclerOptions<IncidentModel> options = new FirestoreRecyclerOptions.Builder<IncidentModel>().setQuery(query, IncidentModel.class).build();
        incidentAdapter = new IncidentAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclers);
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