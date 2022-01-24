package com.example.incidentreporterapp;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class IncidentsAdapter extends FirestoreRecyclerAdapter<IncidentModelClass, IncidentsAdapter.IncidentHolder> {
    public IncidentsAdapter(@NonNull FirestoreRecyclerOptions<IncidentModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IncidentHolder holder, int position, @NonNull IncidentModelClass model) {
        holder.incidentImage.setImageResource(model.getIncidentImage());
        holder.incidentDescription.setText(model.incidentDescription);
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
        ImageView incidentImage;
        TextView incidentDescription;
        TextView latitude;
        TextView longitude;

      public IncidentHolder(@NonNull View itemView) {
          super(itemView);
          incidentImage = itemView.findViewById(R.id.incidentImage);
          incidentDescription = itemView.findViewById(R.id.incidentDescription);
          latitude= itemView.findViewById(R.id.latitude);
          longitude= itemView.findViewById(R.id.longitude);
      }
  }
}