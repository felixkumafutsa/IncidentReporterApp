package com.example.incidentreporterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class QuickMessageAdapter extends FirestoreRecyclerAdapter<QuickMessageModel, QuickMessageAdapter.QuickMessageHolder> {
    public QuickMessageAdapter(@NonNull FirestoreRecyclerOptions<QuickMessageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuickMessageHolder holder, int position, @NonNull QuickMessageModel model) {
        holder.contentItem.setText(model.getContent());
    }

    @NonNull
    @Override
    public QuickMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_layout, parent, false);
        return new QuickMessageHolder(view);
    }

    class QuickMessageHolder extends RecyclerView.ViewHolder{
        TextView contentItem;
        public QuickMessageHolder(@NonNull View itemView) {
            super(itemView);
            contentItem = itemView.findViewById(R.id.incidentDescription);
        }
    }
}
