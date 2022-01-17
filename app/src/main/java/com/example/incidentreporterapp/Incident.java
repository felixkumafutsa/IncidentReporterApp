package com.example.incidentreporterapp;

public class Incident {
    String incidentId;
    String incidentContent;
    public Incident(){

    }

    public Incident(String incidentId, String incidentContent) {
        this.incidentId = incidentId;
        this.incidentContent = incidentContent;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public String getIncidentContent() {
        return incidentContent;
    }
}
