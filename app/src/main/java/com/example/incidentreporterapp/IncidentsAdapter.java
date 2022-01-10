package com.example.incidentreporterapp;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.ViewHolder> {

    private List<IncidentsModelClass> incidentsList;

    public IncidentsAdapter(List<IncidentsModelClass> incidentsList){
        this.incidentsList = incidentsList;
    }

    @NonNull
    @Override
    public IncidentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentsAdapter.ViewHolder holder, int position) {
        int imgResource = incidentsList.get(position).getIncidentIm();
        String description = incidentsList.get(position).getIncidentDescription();
        String lat = incidentsList.get(position).getLatitude();
        String lon = incidentsList.get(position).getLongitude();


        holder.setData(imgResource, description, lat, lon);
    }

    @Override
    public int getItemCount() {
        return incidentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView, textView1,textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.IncidentImage);
            textView = itemView.findViewById(R.id.title);
            textView1 = itemView.findViewById(R.id.latitude);
            textView2 = itemView.findViewById(R.id.longitude);

        }

        public void setData(int imgResource, String description, String lat, String lon) {
            imageView.setImageResource(imgResource);
            textView.setText(description);
            textView1.setText(lat);
            textView2.setText(lon);
        }
    }
}
