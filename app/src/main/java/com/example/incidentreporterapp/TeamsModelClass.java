package com.example.incidentreporterapp;

public class TeamsModelClass {
    private int teamIm;
    private String teamName, emailAddress, phoneNumber;

    TeamsModelClass(int teamIm, String teamName,  String emailAddress, String phoneNumber){
        this.teamIm = teamIm;
        this.teamName = teamName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public int getTeamIm() {
        return teamIm;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}