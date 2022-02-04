package com.example.incidentreporterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TeamsAdapter extends FirestoreRecyclerAdapter<TeamsModelClass, TeamsAdapter.TeamsHolder> {
    public TeamsAdapter(@NonNull FirestoreRecyclerOptions<TeamsModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeamsHolder holder, int position, @NonNull TeamsModelClass model) {
        holder.name.setText(model.getTeamName());
        holder.phone.setText(model.getPhoneNumber());
        holder.email.setText(model.getEmailAddress());
    }

    @NonNull
    @Override
    public TeamsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_layout, parent, false);
        return new TeamsHolder(view);
    }

    class TeamsHolder extends RecyclerView.ViewHolder{
        TextView name, phone, email;
        Button call, view;
        public TeamsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teamName);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            call =  itemView.findViewById(R.id.call);
            view = itemView.findViewById(R.id.viewprofile);
        }
    }
}
