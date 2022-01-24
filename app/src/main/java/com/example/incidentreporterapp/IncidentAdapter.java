package com.example.incidentreporterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class IncidentAdapter extends FirestoreRecyclerAdapter<IncidentModel, IncidentAdapter.IncidentHolder> {
    public IncidentAdapter(@NonNull FirestoreRecyclerOptions<IncidentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IncidentHolder holder, int position, @NonNull IncidentModel model) {
        holder.description.setText(model.getIncidentDescription());
        holder.latitude.setText(model.getIncidentLatitude());
        holder.longitude.setText(model.getIncidentLongitude());
    }

    @NonNull
    @Override
    public IncidentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_layout, parent, false);
        return new IncidentHolder(view);
    }

    class IncidentHolder extends RecyclerView.ViewHolder{
        TextView description, latitude, longitude;
        public IncidentHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.incidentDescription);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }
    }
}
