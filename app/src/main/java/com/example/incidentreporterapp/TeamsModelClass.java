package com.example.incidentreporterapp;

public class TeamsModelClass {
    private String teamName, emailAddress, phoneNumber;

    TeamsModelClass(String teamName,  String emailAddress, String phoneNumber){
        this.teamName = teamName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public TeamsModelClass() {
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