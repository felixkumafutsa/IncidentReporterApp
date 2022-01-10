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

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private List<TeamsModelClass> teamsList;

    public TeamsAdapter(List<TeamsModelClass> teamsList){
        this.teamsList = teamsList;
    }

    @NonNull
    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.ViewHolder holder, int position) {
        int imgResource = teamsList.get(position).getTeamIm();
        String teamName = teamsList.get(position).getTeamName();
        String emailAddress = teamsList.get(position).getEmailAddress();
        String phoneNumber = teamsList.get(position).getPhoneNumber();


        holder.setData(imgResource, teamName, emailAddress, phoneNumber);
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView, textView1,textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.teamImage);
            textView = itemView.findViewById(R.id.teamName);
            textView1 = itemView.findViewById(R.id.email);
            textView2 = itemView.findViewById(R.id.phone);

        }

        public void setData(int imgResource, String teamName, String emailAddress, String phoneNumber) {
            imageView.setImageResource(imgResource);
            textView.setText(teamName);
            textView1.setText(phoneNumber);
            textView2.setText(emailAddress);
        }
    }
}
